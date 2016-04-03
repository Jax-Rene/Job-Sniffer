package com.zhuangjy.analysis

import java.io.StringWriter
import java.sql.DriverManager

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.zhuangjy.common.JobEnum
import com.zhuangjy.dao.AnalysisDao
import com.zhuangjy.entity.{AreaAnalysis, JobAnalysis}
import com.zhuangjy.util.ReadProperties
import org.apache.spark.SparkContext
import org.apache.spark.rdd.JdbcRDD
import scala.util.control._

/**
  * Created by johnny on 16/2/28.
  */
object JobAnalysis {
  val mapper = new ObjectMapper() with ScalaObjectMapper
  mapper.registerModule(DefaultScalaModule)

  def main(args: Array[String]) {
    val sc = new SparkContext("local", "mysql")
    val section = AnalysisDao.loadSection
    val classPathProperties = ReadProperties.readFromClassPathMultiplePro("analysis.properties", Array("area", "company_type", "finance_stage", "education"))
    val areas: Array[String] = classPathProperties("area").split(",")
    val industryField: Array[String] = classPathProperties("company_type").split(",")
    val financeStage: Array[String] = classPathProperties("finance_stage").split(",")
    val education: Array[String] = classPathProperties("education").split(",")
    //记载所有Area分析结果 TODO 接下来这个可以直接顺序下来不用特殊加载 这里先用于测试
    var areaMap: Map[String, AreaAnalysis] = Map()
    areas.iterator.foreach(i => {
      val areaAnas = AnalysisDao.loadAreaAnalysis(i)
      if (areaAnas != null)
        areaMap += (i -> areaAnas)
    })
    val jobMap = calcJob(section(0), section(1), sc, areas, industryField, financeStage, education, areaMap)
    jobMap.values.iterator.foreach(i => AnalysisDao.insertJobAnalysis(i))
    sc.stop()
  }

  def calcJob(min: Long, max: Long, sc: SparkContext, areas: Array[String],
              industrys: Array[String], financeStage: Array[String], education: Array[String], areaMap: Map[String, AreaAnalysis]): Map[String,JobAnalysis]= {
    var jobMap: Map[String, JobAnalysis] = Map()
    val rdd = new JdbcRDD(
      sc,
      () => {
        Class.forName("com.mysql.jdbc.Driver").newInstance()
        DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/jobs", "root", "")
      },
      "SELECT `job_name`,`job_type`,`company_city`,`salary`,`education`,`work_year`,`finance_stage`,`industry_field`,`company_size`" +
        " FROM job WHERE id >= ? AND id <= ?",
      min, max, 3,
      r => (r.getString("job_name"), r.getInt("job_type"), r.getString("company_city"), r.getFloat("salary"), r.getString("education"),
        r.getFloat("work_year"), r.getString("finance_stage"), r.getString("industry_field"), r.getString("company_size"))
    ).cache()

    val loop = new Breaks
    JobEnum.values().foreach(job => {
      loop.breakable {
        //不考虑其他工作
        if (job.getName.indexOf("其他") != -1)
          loop.break()
        val jobRdd = rdd.filter(line => line._1.toUpperCase().contains(job.getKeyWord.toUpperCase()))
        val count = jobRdd.count()
        if(count == 0)
          loop.break()
        val jobAnalysis = new JobAnalysis(job.getName)
        jobAnalysis.setCount(count)
        jobAnalysis.setAvgSalary((jobRdd.map(line => line._4).reduce((a, b) => (a + b)))/count)
        //学历分布
        val educationMap = jobRdd.map(line => (line._5, 1)).reduceByKey((a, b) => (a + b)).collect().toMap
        jobAnalysis.setEducation(outPutJson(educationMap))
        //工作年限
        val workYearMap = jobRdd.map(line => (line._6, 1)).reduceByKey((a, b) => (a + b)).collect().toMap
        jobAnalysis.setWorkYear(outPutJson(workYearMap))
        //需求公司类别
        var industryMap: Map[String, Long] = Map()
        industrys.foreach(i => industryMap += (i -> jobRdd.filter(_._8.toUpperCase.contains(i.toUpperCase)).count()))
        jobAnalysis.setIndustryField(outPutJson(industryMap))
        //公司规模
        var financeStageMap: Map[String, Long] = Map()
        for (s <- financeStage) {
          val count = jobRdd.filter(_._7.toUpperCase.contains(s.toUpperCase)).count()
          financeStageMap += (s -> count)
        }
        jobAnalysis.setFinanceStage(outPutJson(financeStageMap))

        //工作在不同城市的不同需求
        var areaCountMap: Map[String, Long] = Map()
        var areaSalaryMap: Map[String, Float] = Map()
        areaMap.iterator.foreach(s => {
          val detailCountMap: Map[Int, Map[String, Long]] = mapper.readValue[Map[Int, Map[String, Long]]](s._2.getJobDetailCount)
          val detailSalaryMap: Map[Int, Map[String, Float]] = mapper.readValue[Map[Int, Map[String, Float]]](s._2.getJobDetailSalary)
          if (detailCountMap(job.getTypeIndex).contains(job.getName))
            areaCountMap += (s._1 -> detailCountMap(job.getTypeIndex)(job.getName))
          if (detailSalaryMap(job.getTypeIndex).contains(job.getName))
            areaSalaryMap += (s._1 -> detailSalaryMap(job.getTypeIndex)(job.getName))
        })
        jobAnalysis.setAreaCount(outPutJson(areaCountMap))
        jobAnalysis.setAreaSalary(outPutJson(areaSalaryMap))
        jobMap += (job.getName -> jobAnalysis)
      }
    })
    jobMap
  }

  def outPutJson(attr: Any): String = {
    val stringWriter = new StringWriter()
    mapper.writeValue(stringWriter, attr)
    stringWriter.toString
  }
}
