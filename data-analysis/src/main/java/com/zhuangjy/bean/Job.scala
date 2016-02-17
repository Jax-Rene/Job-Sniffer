package com.zhuangjy.bean

/**
  * Created by zhuangjy on 2016/2/17.
  */
class Job (val jobId:Long,val jobName:String){
  @scala.reflect.BeanProperty
  var id1 = jobId;

  def main(args: Array[String]) {
    val test =
      if ( 2 > 3)
        "true"
      else
        "false"
    print(test)
  }
}
