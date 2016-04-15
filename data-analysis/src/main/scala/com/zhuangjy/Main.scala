package com.zhuangjy

import com.zhuangjy.analysis.{AreaAnalysisObj, JobAnalysisObj, OriginAnalysisObj}
import com.zhuangjy.dao.AnalysisDao
import com.zhuangjy.entity.{AreaAnalysis, Origin, PropertiesMap}
import com.zhuangjy.util.ReadProperties
import org.apache.spark.SparkContext

import scala.collection.mutable.ArrayBuffer
import scala.util.control._

/**
  * Created by johnny on 16/3/22.
  */
object Main extends App {
  val sc = new SparkContext("local", "mysql")
  val section = AnalysisDao.loadSection
  val properties: PropertiesMap = AnalysisDao.loadConfig
  val dataBase = ReadProperties.readFromClassPathMultiplePro("database.properties", Array("url", "username", "password"))
  val jdbcUrl = dataBase("url")
  val userName = dataBase("username")
  val passWord = dataBase("password")

  //area
  var areaMap: Map[String, AreaAnalysis] = Map()
  properties.getArea.split(",").foreach(i => areaMap += (i -> new AreaAnalysis(i)))
  AreaAnalysisObj.calcArea(section(0), section(1), sc, properties, jdbcUrl, userName, passWord, areaMap)
  val loop = new Breaks
  areaMap.values.iterator.foreach(i => {
    loop.breakable {
      if (i.getCount == 0)
        loop.break()
      AnalysisDao.insertAreaAnalysis(i)
    }
  })

  //origin
  val origins: ArrayBuffer[Origin] = OriginAnalysisObj.calcNormal(section(0), section(1), sc)
  OriginAnalysisObj.calcDetail(section(0), section(1), sc, origins)
  origins.foreach(s => AnalysisDao.insertOriginAnalysis(s))

  //Job
  val jobMap = JobAnalysisObj.calcJob(section(0), section(1), sc, properties.getArea.split(","), properties
    .getCompanyType.split(","), properties.getFinanceStage.split(","), properties.getEducation.split(","), areaMap)
  jobMap.values.iterator.foreach(i => AnalysisDao.insertJobAnalysis(i))
  sc.stop()
}
