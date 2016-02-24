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
  var map  = Map((1->2),(2->3))
  println(map)
  map += (1->5)
  println(map)

}