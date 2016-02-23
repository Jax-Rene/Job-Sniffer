package com.zhuangjy.analysis

import java.io.{StringWriter}
import java.sql.{DriverManager, ResultSet}

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.zhuangjy.bean.AreaAnalysis
import com.zhuangjy.util.ReadProperties
import org.apache.spark.SparkContext
import org.apache.spark.rdd.JdbcRDD

/**
  * Created by zhuangjy on 2016/2/17.
  */
object AreaAnalysis {
  val mapper = new ObjectMapper
  mapper.registerModule(DefaultScalaModule)

  def main(args: Array[String]) {
    val conn_str = "jdbc:mysql://127.0.0.1:3306/jobs?user=root&password="
    val sc = new SparkContext("local", "mysql")
    val section = loadSection(conn_str)
    var areaMap: Map[String, AreaAnalysis] = Map()
    val areas: Array[String] = ReadProperties.readFromClassPath("analysis.properties", "area").split(",")
    val industryField: Array[String] = ReadProperties.readFromClassPath("analysis.properties", "company_type").split(",")
    //初始化地区分析Map
    for (s <- areas)
      areaMap += (s -> new AreaAnalysis(s))
    calcAreaNormal(section(0), section(1), sc, areas, areaMap)
    sc.stop()
  }

  /**
    * 获取id区间
    */
  def loadSection(src: String) = {
    classOf[com.mysql.jdbc.Driver]
    val res = new Array[Long](2)
    val conn = DriverManager.getConnection(src)
    val statement = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)
    var rs = statement.executeQuery("select id from job ORDER BY id limit 1")
    if (rs.next()) {
      res(0) = rs.getLong("id");
    }
    rs = statement.executeQuery("select id from job ORDER BY id desc limit 1")
    if (rs.next()) {
      res(1) = rs.getLong("id")
    }
    res
  }

  def calcAreaNormal(min: Long, max: Long, sc: SparkContext, areas: Array[String],
                     industrys: Array[String], map: Map[String, AreaAnalysis]): Map[String, AreaAnalysis] = {
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
      val specificAreaRdd = rdd.filter(line => line._3.contains(s)).cache()
      //所有地区对比
      //1.指定地区需求量
      val count = specificAreaRdd.count()
      map(s).setCount(count)
      //2.指定地区平均薪水
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
      typeSalary.iterator.foreach(i => (i._1, i._1 / typeCountMap(i._1)))
      val typeSalaryMap = typeSalary.toMap
      strWriter = new StringWriter
      mapper.writeValue(strWriter, typeSalaryMap)
      map(s).setJobTypeSalary(strWriter.toString)
      //3.指定地区公司类型分布


    }
    map
  }

  /**
    * 获取指定工作的数量
    *
    * @param min
    * @param max
    * @param jobName
    * @return
    */
  def generateJobCount(min: Int, max: Int, jobName: String, sc: SparkContext): Long = {
    val rdd = new JdbcRDD(
      sc,
      () => {
        Class.forName("com.mysql.jdbc.Driver").newInstance()
        DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/jobs", "root", "")
      },
      "SELECT * FROM job WHERE id >=? AND id <= ?",
      min, max, 3,
      //第二列job_name
      r => r.getString(2)).cache()
    rdd.filter(_.contains(jobName)).count()
  }
}
