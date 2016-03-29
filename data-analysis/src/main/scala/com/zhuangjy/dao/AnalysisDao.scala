package com.zhuangjy.dao

import java.sql.{DriverManager, ResultSet}

import com.zhuangjy.entity.{AreaAnalysis, JobAnalysis, Origin}
import com.zhuangjy.util.ReadProperties


/**
  * Created by zhuangjy on 2016/2/24.
  */
object AnalysisDao {
  classOf[com.mysql.jdbc.Driver]
  val conn = DriverManager.getConnection(ReadProperties.readFromClassPath("database.properties", "url"))


  def insertAreaAnalysis(areaAnalysis: AreaAnalysis): Unit = {
    val sql = "INSERT INTO `area_analysis` (`area`,`count`,`avg_salary`,`industry_field`,`job_type_salary`,`job_type_count`,`job_detail_count`,`job_detail_salary`,`finance_stage`) VALUES (?,?,?,?,?,?,?,?,?)"
    val pstmt = conn.prepareStatement(sql)
    pstmt.setString(1, areaAnalysis.getArea)
    pstmt.setLong(2, areaAnalysis.getCount)
    pstmt.setFloat(3, areaAnalysis.getAvgSalary)
    pstmt.setString(4, areaAnalysis.getIndustryField)
    pstmt.setString(5, areaAnalysis.getJobTypeSalary)
    pstmt.setString(6, areaAnalysis.getJobTypeCount)
    pstmt.setString(7, areaAnalysis.getJobDetailCount)
    pstmt.setString(8, areaAnalysis.getJobDetailSalary)
    pstmt.setString(9,areaAnalysis.getFinanceStage)
    println(pstmt.toString)
    pstmt.execute()
  }


  def insertJobAnalysis(jobAnalysis: JobAnalysis):Unit={
    val sql = "INSERT INTO `job_analysis` (`job_name`,`count`,`avg_salary`,`education`,`work_year`,`industry_field`,`finance_stage`,`company_size`) VALUES(?,?,?,?,?,?,?)"
    val pstmt = conn.prepareStatement(sql)
    pstmt.setString(1,jobAnalysis.getJobName)
    pstmt.setLong(2,jobAnalysis.getCount)
    pstmt.setFloat(3,jobAnalysis.getAvgSalary)
    pstmt.setString(4,jobAnalysis.getEducation)
    pstmt.setString(5,jobAnalysis.getWorkYear)
    pstmt.setString(6,jobAnalysis.getIndustryField)
    pstmt.setString(7,jobAnalysis.getFinanceStage)
    pstmt.setString(8,jobAnalysis.getCompanySize)
    println(pstmt.toString)
    pstmt.execute()
  }

  def insertOriginAnalysis(origin:Origin):Unit={
    val sql = "INSERT INTO `origin` (`origin`,`count`,`salary`,`detail_count`) VALUES (?,?,?,?)"
    val pstmt = conn.prepareStatement(sql)
    pstmt.setString(1,origin.getOrigin)
    pstmt.setLong(2,origin.getCount)
    pstmt.setFloat(3,origin.getSalary)
    pstmt.setString(4,origin.getDetailCount)
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

  def loadAreaAnalysis(area:String):AreaAnalysis = {
    val pstmt = conn.prepareStatement("SELECT * FROM `area_analysis` WHERE `area` = ?")
    pstmt.setString(1,area)
    val rs = pstmt.executeQuery()
    var res:AreaAnalysis = null
    if(rs.next())
      res = new AreaAnalysis(rs.getString("area"),rs.getLong("count"),rs.getFloat("avg_salary"),rs.getString("industry_field"),
        rs.getString("job_type_salary"),rs.getString("job_type_count"),rs.getString("job_detail_count"),rs.getString("job_detail_salary"),rs.getString("finance_stage"))
    res
  }
}
