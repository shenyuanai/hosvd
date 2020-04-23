package hosvd

import Read.HSIInputFormat
import org.apache.spark.ml.linalg.DenseMatrix
import org.apache.spark.mllib.linalg
import org.apache.spark.mllib.linalg.{Matrix, SingularValueDecomposition, Vectors}
import org.apache.spark.mllib.linalg.distributed.{MatrixEntry, RowMatrix}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{Dataset, SparkSession}

import scala.collection.mutable.ListBuffer
object Test {
  def main(args: Array[String]): Unit = {
//   val config:SparkConf= new SparkConf().setMaster("local[*]").setAppName("test");
//    val sc=new SparkContext(config);
    val spark=SparkSession.builder().appName("test").master("local").getOrCreate();
    val sc=spark.sparkContext;
    val implicits=spark.implicits
    import implicits._
//    val denseData = Seq(
//      Vectors.dense(0.0, 1.0),
//      Vectors.dense(1.0, 1.0),
//      Vectors.dense(1.0, 0.0)
//    )
//    val mat: RowMatrix = new RowMatrix(sc.parallelize(denseData))
//    val svd: SingularValueDecomposition[RowMatrix, Matrix] = mat.computeSVD(1, computeU = true)
//    val U: RowMatrix = svd.U // The U factor is a RowMatrix.
//    val s: linalg.Vector = svd.s // The singular values are stored in a local dense vector.
//    val V: Matrix = svd.V // The V factor is a local dense matrix.
//    U.rows.collect().foreach(x=>println(x))
//
//    println("U:" + U)
//    println("s:" + s)
//    println("V:" + V)
     sc.newAPIHadoopFile("hdfs://localhost:9000/img/c350bip", classOf[HSIInputFormat], classOf[Integer], classOf[Array[Byte]]).foreach{
       case(a,b)=>{
         println(a+"-------"+b)
       }}
//    val ds = Seq(
//      MatrixEntry(0, 0, 1.0),
//      MatrixEntry(0, 1, 2.0),
//      MatrixEntry(0, 2, 3.0),
//      MatrixEntry(1, 0, 4.0),
//      MatrixEntry(1, 1, 5.0),
//      MatrixEntry(1, 2, 6.0)
//    ).toDS
//
//
//    val block = CoordinateBlock(ds, 2, 3)
//    val matrix1=new DenseMatrix(2,3,Array(1.0,2.0,3.0,4.0,5.0,6.0))
//
//
//
//
//
//    //测试hosvd
//    val data = Seq(
//      TensorEntry(0, 0, 0, 0.0 + 111),
//      TensorEntry(0, 1, 0, 10.0 + 111),
//      TensorEntry(0, 2, 0, 20.0 + 111),
//      TensorEntry(1, 0, 0, 100.0 + 111),
//      TensorEntry(1, 1, 0, 110.0 + 111),
//      TensorEntry(1, 2, 0, 120.0 + 111),
//      TensorEntry(2, 0, 0, 200.0 + 111),
//      TensorEntry(2, 1, 0, 210.0 + 111),
//      TensorEntry(2, 2, 0, 220.0 + 111),
//      TensorEntry(3, 0, 0, 300.0 + 111),
//      TensorEntry(3, 1, 0, 310.0 + 111),
//      TensorEntry(3, 2, 0, 320.0 + 111),
//      TensorEntry(0, 0, 1, 1.0 + 111),
//      TensorEntry(0, 1, 1, 11.0 + 111),
//      TensorEntry(0, 2, 1, 21.0 + 111),
//      TensorEntry(1, 0, 1, 101.0 + 111),
//      TensorEntry(1, 1, 1, 111.0 + 111),
//      TensorEntry(1, 2, 1, 121.0 + 111),
//      TensorEntry(2, 0, 1, 201.0 + 111),
//      TensorEntry(2, 1, 1, 211.0 + 111),
//      TensorEntry(2, 2, 1, 221.0 + 111),
//      TensorEntry(3, 0, 1, 301.0 + 111),
//      TensorEntry(3, 1, 1, 311.0 + 111),
//      TensorEntry(3, 2, 1, 321.0 + 111)
//    ).toDS
//    val arr=new ListBuffer[Integer]();
//    val rdd1=sc.makeRDD(List(1,2,3))
//    rdd1.map(x=>{
//      val res=x+1
//      arr.append(res)
//      (res)
//    }).collect()
//    println(arr.size)




    sc.stop()

  }
}
