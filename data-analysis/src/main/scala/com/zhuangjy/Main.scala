package com.zhuangjy

/**
  * Created by johnny on 16/3/22.
  */
object Main {
  def main(args: Array[String]): Unit = {
    var map = Map(1->2,2->3)
    var count = 0
    map.iterator.foreach(i=>{
      count += i._1
    })
    print(map)

  }
}
