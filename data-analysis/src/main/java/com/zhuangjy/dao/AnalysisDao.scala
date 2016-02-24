package com.zhuangjy.dao

import java.sql.{ResultSet, DriverManager}

import com.zhuangjy.bean.AreaAnalysis
import com.zhuangjy.util.ReadProperties

/**
  * Created by zhuangjy on 2016/2/24.
  */
object AnalysisDao {
  classOf[com.mysql.jdbc.Driver]
  val conn = DriverManager.getConnection(ReadProperties.readFromClassPath("database.properties", "url"))

  def main(args: Array[String]) {
    val a = new AreaAnalysis("是的",123,123.23f,"都是","12321.12","12332","是的","啊","请问")

    insertAreaAnalysis(a)
  }


  def insertAreaAnalysis(areaAnalysis: AreaAnalysis): Unit = {
    val sql = "INSERT INTO `area_analysis` (`area`,`count`,`avg_salary`,`industry_field`,`job_type_salary`,`job_type_count`,`job_detail_count`,`job_detail_salary`) VALUES (?,?,?,?,?,?,?,?)"
    val pstmt = conn.prepareStatement(sql)
    println(areaAnalysis)
    pstmt.setString(1, areaAnalysis.area)
    pstmt.setLong(2, areaAnalysis.count)
    pstmt.setFloat(3, areaAnalysis.avgSalary)
    pstmt.setString(4, areaAnalysis.industryField)
    pstmt.setString(5, areaAnalysis.jobTypeSalary)
    pstmt.setString(6, areaAnalysis.jobTypeCount)
    pstmt.setString(7, areaAnalysis.jobDetailCount)
    pstmt.setString(8, areaAnalysis.jobDetailSalary)
    println(pstmt.toString)
    pstmt.execute()
  }


  /**
    * 获取Job表 id区间
    */
  def loadSection: Array[Long] = {
    val res = new Array[Long](2)
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
}
