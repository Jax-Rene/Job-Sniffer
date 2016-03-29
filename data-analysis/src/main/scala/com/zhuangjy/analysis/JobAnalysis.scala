package com.zhuangjy.analysis

import java.io.StringWriter
import java.sql.DriverManager

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.zhuangjy.common.JobEnum
import com.zhuangjy.dao.AnalysisDao
import com.zhuangjy.entity.{AreaAnalysis, JobAnalysis}
import com.zhuangjy.util.ReadProperties
import org.apache.spark.SparkContext
import org.apache.spark.rdd.JdbcRDD

/**
  * Created by johnny on 16/2/28.
  */
object JobAnalysis {
  val mapper = new ObjectMapper
  mapper.registerModule(DefaultScalaModule)

  def main(args: Array[String]) {
    val sc = new SparkContext("local", "mysql")
    val section = AnalysisDao.loadSection
    var jobMap: Map[String, JobAnalysis] = Map()
    val classPathProperties = ReadProperties.readFromClassPathMultiplePro("analysis.properties", Array("area", "company_type", "finance_stage"))
    val areas: Array[String] = classPathProperties("area").split(",")
    val industryField: Array[String] = classPathProperties("company_type").split(",")
    val financeStage: Array[String] = classPathProperties("finance_stage").split(",")
    val education:Array[String] = classPathProperties("education").split(",")
    //初始化
    for (s:String <- JobEnum.listAllJobs())
      jobMap += (s -> new JobAnalysis(s))

    //记载所有Area分析结果 TODO 接下来这个可以直接顺序下来不用特殊加载 这里先用于测试
    var areaMap:Map[String,AreaAnalysis] = Map()
    for(s<-areas){
      val areaAnalysis:AreaAnalysis = AnalysisDao.loadAreaAnalysis(s)
      areaMap += (s->areaAnalysis)
    }


    calcJob(section(0), section(1), sc, areas, industryField, financeStage,education, jobMap)
    for (s <- jobMap.values) {
      AnalysisDao.insertJobAnalysis(s)
    }
    sc.stop()
  }

  def calcJob(min: Long, max: Long, sc: SparkContext, areas: Array[String],
               industrys: Array[String], financeStage: Array[String],education:Array[String],areaMap:Map[String,AreaAnalysis],
               map: Map[String, JobAnalysis]): Map[String, JobAnalysis] = {
    val rdd = new JdbcRDD(
      sc,
      () => {
        Class.forName("com.mysql.jdbc.Driver").newInstance()
        DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/jobs", "root", "")
      },
      "SELECT `job_name`,`job_type`,`company_city`,`salary`,`education`,`work_year`,`finance_stage`,`industry_field`,`company_size`" +
        " FROM job WHERE id >= ? AND id <= ?",
      min, max, 3,
      r => (r.getString("job_name"), r.getInt("job_type"), r.getString("company_city"), r.getFloat("salary"),r.getString("education"),
        r.getFloat("work_year"),  r.getString("finance_stage"), r.getString("industry_field"), r.getString("company_size"))
    ).cache()

    for(job:JobEnum <- JobEnum.values()){
      val jobRdd = rdd.filter(line =>  line._1.toUpperCase().contains(job.getKeyWord.toUpperCase()))
      map(job.getName).setCount(jobRdd.count())
      map(job.getName).setAvgSalary(jobRdd.map(line => line._4).reduce((a,b) => (a+b)))
      //学历分布
      val educationMap = jobRdd.map(line=>(line._5,1)).reduceByKey((a,b)=>(a+b)).collect().toMap
      var strWriter = new StringWriter()
      mapper.writeValue(strWriter,educationMap)
      map(job.getName).setEducation(strWriter.toString)
      //工作年限
      val workYearMap = jobRdd.map(line=>(line._6,1)).reduceByKey((a,b)=>(a+b)).collect().toMap
      strWriter = new StringWriter()
      mapper.writeValue(strWriter,workYearMap)
      map(job.getName).setWorkYear(strWriter.toString)
      //需求公司类别
      var industryMap: Map[String, Long] = Map()
      for (s <- industrys) {
        val count = jobRdd.filter(_._8.toUpperCase.contains(s.toUpperCase)).count()
        industryMap += (s -> count)
      }
      strWriter = new StringWriter
      mapper.writeValue(strWriter, industryMap)
      map(job.getName).setIndustryField(strWriter.toString)
      //公司规模
      var financeStageMap: Map[String, Long] = Map()
      for (s <- financeStage) {
        val count = jobRdd.filter(_._7.toUpperCase.contains(s.toUpperCase)).count()
        financeStageMap += (s -> count)
      }
      strWriter = new StringWriter
      mapper.writeValue(strWriter, financeStageMap)
      map(job.getName).setFinanceStage(strWriter.toString)
      //工作在不同城市的不同需求
      var areaCountMap = Map()
      var areaSalaryMap = Map()
      areaMap.iterator.foreach(line => {
//        val s = mapper.readValue(line._2.getJobDetailCount,Map.getClass)
      })

    }

  }
}
