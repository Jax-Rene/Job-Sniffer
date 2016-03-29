package com.zhuangjy

import scala.util.control._

/**
  * Created by johnny on 16/3/22.
  */
object Main {

  def main(args: Array[String]): Unit = {
    val a: Array[Int] = Array(1, 2, 3, 4)
    val loop = new Breaks
    a.iterator.foreach(i => {
      loop.breakable {
        if (i == 1)
          loop.break()
        print(i)
      }
    })
  }
}
