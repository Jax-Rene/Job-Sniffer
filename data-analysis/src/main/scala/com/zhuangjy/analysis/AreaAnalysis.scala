package com.zhuangjy.analysis

import java.io.StringWriter
import java.sql.DriverManager
import java.util.NoSuchElementException

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.zhuangjy.entity.Area
import com.zhuangjy.common.{JobEnum, JobType}
import com.zhuangjy.dao.AnalysisDao
import com.zhuangjy.util.ReadProperties
import org.apache.spark.SparkContext
import org.apache.spark.rdd.{JdbcRDD, RDD}

import scala.collection.JavaConverters._

/**
  * Created by zhuangjy on 2016/2/17.
  */
object AreaAnalysis {
  val mapper = new ObjectMapper
  mapper.registerModule(DefaultScalaModule)

  def main(args: Array[String]) {
    val sc = new SparkContext("local", "mysql")
    val section = AnalysisDao.loadSection
    var areaMap: Map[String, Area] = Map()
    val classPathProperties = ReadProperties.readFromClassPathMultiplePro("analysis.properties", Array("area", "company_type", "finance_stage"))
    val areas: Array[String] = classPathProperties("area").split(",")
    val industryField: Array[String] = classPathProperties("company_type").split(",")
    val financeStage: Array[String] = classPathProperties("finance_stage").split(",")
    //初始化地区分析Map
    for (s <- areas)
      areaMap += (s -> new Area(s))
    calcArea(section(0), section(1), sc, areas, industryField, financeStage, areaMap)
    for (s <- areaMap.values) {
      AnalysisDao.insertAreaAnalysis(s)
    }
    sc.stop()
  }


  def calcArea(min: Long, max: Long, sc: SparkContext, areas: Array[String],
               industrys: Array[String], financeStage: Array[String],
               map: Map[String, Area]): Map[String, Area] = {
    val rdd = new JdbcRDD(
      sc,
      () => {
        Class.forName("com.mysql.jdbc.Driver").newInstance()
        DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/jobs", "root", "")
      },
      "SELECT `job_name`,`job_type`,`company_city`,`salary`,`education`,`finance_stage`,`industry_field`,`company_size`" +
        " FROM job WHERE id >= ? AND id <= ?",
      min, max, 3,
      r => (r.getString("job_name"), r.getInt("job_type"), r.getString("company_city"), r.getFloat("salary"),
        r.getString("education"), r.getString("finance_stage"), r.getString("industry_field"), r.getString("company_size"))
    ).cache()

    for (s <- areas) {
      val specificAreaRdd = rdd.filter(line => line._3.toUpperCase.contains(s.toUpperCase)).cache()
      //所有地区对比
      //1.所有地区需求量
      val count = specificAreaRdd.count()
      map(s).setCount(count)
      //2.所有地区平均薪水
      //TODO 求平均值大数据会越界
      val avgSalar = specificAreaRdd.map(line => line._4).reduce((a, b) => (a + b))
      map(s).setAvgSalary(avgSalar / count)

      //具体地区内部对比
      //1.指定地区工作类型的数量分布
      val typeCountMap = specificAreaRdd.map(line => (line._2, 1)).reduceByKey((a, b) => a + b).collect().toMap
      var strWriter = new StringWriter
      mapper.writeValue(strWriter, typeCountMap)
      map(s).setJobTypeCount(strWriter.toString)
      //2.指定地区工作类型平均薪水
      val typeSalary = specificAreaRdd.map(line => (line._2, line._4)).reduceByKey((a, b) => a + b).collect()
      val typeSalaryMap = typeSalary.map(i => (i._1, i._2 / typeCountMap(i._1))).toMap
      strWriter = new StringWriter
      mapper.writeValue(strWriter, typeSalaryMap)
      map(s).setJobTypeSalary(strWriter.toString)
      //3.指定地区公司类型分布
      var industryMap: Map[String, Long] = Map()
      for (s <- industrys) {
        val count = specificAreaRdd.filter(_._7.toUpperCase.contains(s.toUpperCase)).count()
        industryMap += (s -> count)
      }
      strWriter = new StringWriter
      mapper.writeValue(strWriter, industryMap)
      map(s).setIndustryField(strWriter.toString)
      //4.指定地区公司规模（投资轮)分布
      var financeStageMap: Map[String, Long] = Map()
      for (s <- financeStage) {
        val count = specificAreaRdd.filter(_._6.toUpperCase.contains(s.toUpperCase)).count()
        financeStageMap += (s -> count)
      }
      strWriter = new StringWriter
      mapper.writeValue(strWriter, financeStageMap)
      map(s).setFinanceStage(strWriter.toString)

      //5.指定地区各个工作数量分布
      var jobDetailCountMap: Map[Integer, Map[String, Long]] = Map()
      var jobDetailSalaryMap: Map[Integer, Map[String, Float]] = Map()
      for (typeIndex: Integer <- JobEnum.listAllTypeIndex().asScala) {
        var jobDetailCount: Map[String, Long] = Map()
        val jobTypeRdd = specificAreaRdd.filter(_._2.equals(typeIndex))
        var sum = jobTypeRdd.count()
        var other = "";
        for (s: String <- JobEnum.listKeyWords(typeIndex).asScala) {
          if (JobEnum.getJobNameByKeyWords(s).indexOf("其他") == -1) {
            val count = jobTypeRdd.filter(_._1.toUpperCase.contains(s.toUpperCase)).count()
            jobDetailCount += (JobEnum.getJobNameByKeyWords(s) -> count)
            sum -= count;
          } else {
            other = s;
          }
        }
        jobDetailCount += (JobEnum.getJobNameByKeyWords(other) -> sum)
        jobDetailCountMap += (typeIndex -> jobDetailCount)
        strWriter = new StringWriter
        mapper.writeValue(strWriter, jobDetailCountMap)
        map(s).setJobDetailCount(strWriter.toString)
        val jobDetailSalary = jobTypeRdd.map(line => (JobEnum.getKeyWordsByName(line._1,typeIndex), line._4)).reduceByKey((a, b) => a + b).collect().map(i => (JobEnum.getJobNameByKeyWords(i._1), i._2)).filter((key: (String, Float)) => jobDetailCount.contains(key._1) && !key._1.contains("其他"))
        var jobDetailSalaryTemp = jobDetailSalary.map(i => (i._1, i._2 / jobDetailCount(i._1))).toMap
        var total:Float = 0.0f
        var c = 0
        jobDetailSalaryTemp.foreach(i => {
          total += i._2
          c += 1
        })
        if(typeSalaryMap.contains(typeIndex)) {
          jobDetailSalaryTemp += (JobEnum.getOther(typeIndex) -> (typeSalaryMap(typeIndex) * (c + 1) - total))
        }
        jobDetailSalaryMap += (typeIndex -> jobDetailSalaryTemp)
        strWriter = new StringWriter
        mapper.writeValue(strWriter, jobDetailSalaryMap)
        map(s).setJobDetailSalary(strWriter.toString)
      }
    }
    map
  }
}
