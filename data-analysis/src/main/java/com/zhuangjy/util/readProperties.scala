package com.zhuangjy.util

import java.io.{InputStreamReader, FileInputStream}
import java.util.Properties


/**
  * Created by johnny on 16/2/21.
  */
object ReadProperties {
  def readFromClassPath(fileName: String, property: String): String = {
    val prop = new Properties()
    prop.load(new InputStreamReader(ClassLoader.getSystemResourceAsStream(fileName),"UTF-8"))
    prop.getProperty(property)
  }

  def readFromFilePath(filePath: String, property: String): String = {
    val prop = new Properties()
    prop.load(new InputStreamReader(new FileInputStream(filePath),"UTF-8"))
    prop.getProperty(property)
  }
}
