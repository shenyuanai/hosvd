package main.scala.hosvd

import hosvd.TensorEntry

import scala.collection.mutable.ListBuffer

object TestJust {
  def main(args: Array[String]): Unit = {
    val tensorSeq=new ListBuffer[TensorEntry]()
    tensorSeq.append(TensorEntry(-1,-1,-1,0.5))
    tensorSeq.append(TensorEntry(-1,-1,-1,0.5))
    println(tensorSeq.size)
  }
}
