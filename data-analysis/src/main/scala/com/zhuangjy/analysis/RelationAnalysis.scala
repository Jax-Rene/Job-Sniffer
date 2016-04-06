package com.zhuangjy.analysis

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.zhuangjy.dao.AnalysisDao
import com.zhuangjy.entity.AreaAnalysis
import com.zhuangjy.util.ReadProperties
import org.apache.spark.SparkContext

/**
  * Created by johnny on 16/4/5.
  */
object RelationAnalysis {
  val mapper = new ObjectMapper() with ScalaObjectMapper
  mapper.registerModule(DefaultScalaModule)

}
