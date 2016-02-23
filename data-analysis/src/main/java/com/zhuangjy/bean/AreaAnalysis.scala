package com.zhuangjy.bean

import scala.reflect._

/**
  * Created by zhuangjy on 2016/2/22.
  */
class AreaAnalysis(@BeanProperty val area:String, @BeanProperty var count:Long, @BeanProperty var avgSalary:Float,
                   @BeanProperty var companyType:String, @BeanProperty var companySize:String,
                  @BeanProperty var jobTypeCount:String,@BeanProperty var jobTypeSalary:String,
                   @BeanProperty var jobDetailCount:String,@BeanProperty var jobDetailSalary:String) {
  def this(name:String) = this(name,0,0.0f,"","","","","","")
}
