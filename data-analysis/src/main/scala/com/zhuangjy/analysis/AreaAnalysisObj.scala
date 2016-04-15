package com.zhuangjy.analysis

import java.io.StringWriter
import java.sql.DriverManager

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.zhuangjy.entity.{AreaAnalysis, PropertiesMap}
import com.zhuangjy.common.{JobEnum, JobType}
import org.apache.spark.SparkContext
import org.apache.spark.rdd.{JdbcRDD, RDD}

import scala.collection.JavaConverters._

/**
  * Created by zhuangjy on 2016/2/17.
  */
object AreaAnalysisObj {
  val mapper = new ObjectMapper() with ScalaObjectMapper
  mapper.registerModule(DefaultScalaModule)

  def calcArea(min: Long, max: Long, sc: SparkContext, properties: PropertiesMap, jdbcUrl: String, userName: String,
               passWord: String, map: Map[String, AreaAnalysis]): Map[String, AreaAnalysis] = {
    val rdd = new JdbcRDD(
      sc,
      () => {
        Class.forName("com.mysql.jdbc.Driver").newInstance()
        DriverManager.getConnection(jdbcUrl, userName, passWord)
      },
      "SELECT `job_name`,`job_type`,`company_city`,`salary`,`education`,`finance_stage`,`industry_field`" +
        " FROM job WHERE id >= ? AND id <= ?",
      min, max, 3,
      r => (r.getString("job_name"), r.getInt("job_type"), r.getString("company_city"), r.getFloat("salary"),
        r.getString("education"), r.getString("finance_stage"), r.getString("industry_field"))
    ).cache()
    properties.getArea.split(",").iterator.foreach(s => {
      val specificAreaRdd = rdd.filter(line => line._3.toUpperCase.contains(s.toUpperCase)).cache()
      val count = specificAreaRdd.count()
      //所有地区需求量
      map(s).setCount(count)
      //所有地区平均薪水
      //TODO 求平均值大数据会越界
      try {
        val avgSalar = specificAreaRdd.map(line => line._4).reduce((a, b) => (a + b))
        map(s).setAvgSalary(avgSalar / map(s).getCount)
      } catch {
        case ex: Exception => print(s)
      }
      //指定地区工作类型的数量分布
      val typeCountMap = specificAreaRdd.map(line => (line._2, 1)).reduceByKey((a, b) => a + b).collect().toMap
      map(s).setJobTypeCount(outPutJson(typeCountMap))
      //指定地区工作类型平均薪水
      val typeSalaryMap = specificAreaRdd.map(line => (line._2, line._4)).reduceByKey((a, b) => a + b).collect().map(i => (i._1, i._2 / typeCountMap(i._1))).toMap
      map(s).setJobTypeSalary(outPutJson(typeSalaryMap))
      //指定地区公司类型分布
      var industryMap: Map[String, Long] = Map()
      properties.getCompanyType.split(",").foreach(s => {
        val count = specificAreaRdd.filter(_._7.toUpperCase.contains(s.toUpperCase)).count()
        industryMap += (s -> count)
      })
      map(s).setIndustryField(outPutJson(industryMap))
      //指定地区公司规模（投资轮)分布
      var financeStageMap: Map[String, Long] = Map()
      properties.getFinanceStage.split(",").foreach(s => financeStageMap += (s -> specificAreaRdd.filter(_._6.toUpperCase.contains(s.toUpperCase)).count()))
      map(s).setFinanceStage(outPutJson(financeStageMap))
      //指定地区各个工作数量分布
      var jobDetailCountMap: Map[Integer, Map[String, Long]] = Map()
      var jobDetailSalaryMap: Map[Integer, Map[String, Float]] = Map()
      JobEnum.listAllTypeIndex().asScala.iterator.foreach(typeIndex => {
        var jobDetailCount: Map[String, Long] = Map()
        val jobTypeRdd = specificAreaRdd.filter(_._2.equals(typeIndex))
        var sum = jobTypeRdd.count()
        var other = ""
        JobEnum.listKeyWords(typeIndex).asScala.iterator.foreach(s => {
          if (JobEnum.getJobNameByKeyWords(s).indexOf("其他") == -1) {
            val count = jobTypeRdd.filter(_._1.toUpperCase.contains(s.toUpperCase)).count()
            if (count != 0) {
              jobDetailCount += (JobEnum.getJobNameByKeyWords(s) -> count)
              sum -= count
            }
          } else {
            other = s
          }
        })
        if (sum > 0)
          jobDetailCount += (JobEnum.getJobNameByKeyWords(other) -> sum)
        jobDetailCountMap += (typeIndex -> jobDetailCount)
        map(s).setJobDetailCount(outPutJson(jobDetailCountMap))
        val jobDetailSalary = jobTypeRdd.map(line => (JobEnum.getKeyWordsByName(line._1, typeIndex), line._4)).reduceByKey((a, b) => a + b).collect().map(i => (JobEnum.getJobNameByKeyWords(i._1), i._2)).filter((key: (String, Float)) => jobDetailCount.contains(key._1) && !key._1.contains("其他"))
        var jobDetailSalaryTemp = jobDetailSalary.map(i => (i._1, i._2 / jobDetailCount(i._1))).toMap
        var total: Float = 0.0f
        var c = 0
        jobDetailSalaryTemp.foreach(i => {
          total += i._2
          c += 1
        })
        if (typeSalaryMap.contains(typeIndex))
          jobDetailSalaryTemp += (JobEnum.getOther(typeIndex) -> (typeSalaryMap(typeIndex) * (c + 1) - total))
        jobDetailSalaryMap += (typeIndex -> jobDetailSalaryTemp)
        map(s).setJobDetailSalary(outPutJson(jobDetailSalaryMap))
      })
    })
    map
  }

  def outPutJson(attr: Any): String = {
    val stringWriter = new StringWriter()
    mapper.writeValue(stringWriter, attr)
    stringWriter.toString
  }
}
