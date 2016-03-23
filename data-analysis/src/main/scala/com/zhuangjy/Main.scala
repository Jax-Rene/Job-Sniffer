package com.zhuangjy

/**
  * Created by johnny on 16/3/22.
  */
object Main {
  def main(args: Array[String]) {
    var map = Map(1->2,3->4)
    map = map.map(i=>(i._1 *10 -> i._2 *20))
    print(map)
  }
}
