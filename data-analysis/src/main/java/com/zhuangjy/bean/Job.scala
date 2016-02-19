package com.zhuangjy.bean

/**
  * Created by zhuangjy on 2016/2/17.
  */
class Job (var _jobId:Long,var _jobName:String,var _jobType:String,var _companyCity:String,var _companyName:String,
           var _workYear:Float,var _salary:Float,var _education:String,var _financeStage:String,var _industryField:String,
           var _companySize:Float){
  @scala.reflect.BeanProperty
  var jobId = _jobId
  @scala.reflect.BeanProperty
  var jobName = _jobName
  @scala.reflect.BeanProperty
  var jobType = _jobType
  @scala.reflect.BeanProperty
  var companyCity = _companyCity
  @scala.reflect.BeanProperty
  var companyName = _companyName
  @scala.reflect.BeanProperty
  var workYear = _workYear
  @scala.reflect.BeanProperty
  var salary = _salary
  @scala.reflect.BeanProperty
  var education = _education
  @scala.reflect.BeanProperty
  var financeStage = _financeStage
  @scala.reflect.BeanProperty
  var industryField = _industryField
  @scala.reflect.BeanProperty
  var companySize = _companySize
}
