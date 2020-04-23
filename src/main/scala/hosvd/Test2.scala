package main.scala.hosvd

import Read.{HSIInputFormat, HSIhdr}
import hosvd.{DistributedTensor, TensorEntry}
import main.java.JTool
import org.apache.spark.sql.{Dataset, SparkSession}

import scala.collection.mutable.ListBuffer

object Test2 {
  def main(args: Array[String]): Unit = {
    val spark=SparkSession.builder().appName("testhosvd").master("local[*]").getOrCreate();
    val sc=spark.sparkContext;

    val implicits=spark.implicits
    import implicits._

    val path = "hdfs://localhost:9000/header/"
    val filename = "c350bip.hdr"
    val header = new HSIhdr(filename, path)

    val bands = header.getBands
    val row = header.getRow
    val col = header.getCol
    val bconf = sc.broadcast((row, col, bands, header.getDatatype, header.getInter.toLowerCase()))


    val file=sc.newAPIHadoopFile("hdfs://localhost:9000/img/c350bip", classOf[HSIInputFormat], classOf[Integer], classOf[Array[Byte]])
    println("file的分区数："+file.partitions.length+"元素总数"+file.count()+"file类别： "+file.getClass)
    val rdd1=file.map(pair => {
      val datasize = bconf.value._4 match {
        case 2 => 2.toShort
        case 4 => 4.toShort
        case 12 => 2.toShort
        case _ => {
          println("不支持的datasize格式!"); 0.toShort
          sys.exit(-1)
        }
      }//一个像素点一个波段所占字节数
      val data = pair._2   //classOf[Array[Byte]] 数组类型数据
      val len = data.length   //图像总共的大小（Byte-字节）
      val col = bconf.value._2
      val bands = bconf.value._3
      val key = pair._1 / (bands * datasize)   //像素点的索引

      val pixel = len / (datasize * bands)
      val fdata = bconf.value._5 match {  //header.getInter.toLowerCase()
        case "bil" => JTool.BtoFBil(data, pixel, col, bands, datasize)
        case "bip" => JTool.BtoFBip(data, pixel, bands, datasize)
        case _ => {
          println("不支持的interleave格式!")
          sys.exit(-1)
        }
      }
      val tensorSeq=new ListBuffer[TensorEntry]()
      for(i<- 0 until fdata.length){
        for(j<-0 until fdata(i).length){
          val rowIndex=i/col
          val colIndex=i%col
          val layerIndex=j
          //          println(rowIndex+"&"+colIndex+"&"+layerIndex+"&"+fdata(i)(j))
          tensorSeq.append(TensorEntry(rowIndex,colIndex,layerIndex,fdata(i)(j)))
        }
      }
      println("key: "+key+"   pair_1： "+pair._1)
      println("每个分区的tensorseq的size"+tensorSeq.length)
      (key, tensorSeq)
    }).foreach{

      case (k,v)=>{
        println("entered")
        print("k: "+k+"v:"+v)
        val data: Dataset[TensorEntry]=v.toDS()
        val tensor = new DistributedTensor(data)
        val ho = tensor.hosvd(2, 2, 2).coreTensor.asInstanceOf[DistributedTensor]
        println(ho.numCols+"&"+ho.numLayers+"&"+ho.numRows)
      }
    }



  }
}
