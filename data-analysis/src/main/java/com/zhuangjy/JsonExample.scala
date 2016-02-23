package com.zhuangjy

import java.io.StringWriter

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.zhuangjy.util.ReadProperties

import scala.collection.immutable.HashSet
import scala.io.Source

/**
  * Created by zhuangjy on 2016/2/23.
  */
object JsonExample extends App {
  val lines = Source.fromFile("C:\\Users\\iamjo\\Desktop\\company.txt").getLines.toList
  var res: Set[String] = HashSet()
  for(line:String<-lines){
    for(s:String <- line.split("·")){
      res += s
    }
  }

  res.iterator.foreach(
    i => print(i + ",")
  )


}