package com.trendmicro.mllib

import com.trendmicro.util.localReadWrite
import org.apache.spark.mllib.clustering.{PowerIterationClustering, PowerIterationClusteringModel}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ArrayBuffer

/**
  * Created by herbert_yin on 2016/7/4.
  */
object PICImpl {

  val conf = new SparkConf().setAppName("LR").setMaster("local").set("spark.hadoop.validateOutputSpecs", "false")
  val sc = new SparkContext(conf)

  var model: PowerIterationClusteringModel = null

  // Load and parse the data
  val data = sc.textFile("D:\\03datafolder\\data\\output\\0711\\userItemScorePIC.csv")
  //  val data = sc.textFile("data/mllib/pic_data.txt")
  val similarities = data.map { line =>
    val parts = line.split(',')
    (parts(0).toLong, parts(1).toLong, parts(2).toDouble)
  }

  def run(path: String, msetK: Int): Unit = {


    // Cluster the data into two classes using PowerIterationClustering
    val pic = new PowerIterationClustering()
      .setK(msetK)
      .setMaxIterations(60)
    model = pic.run(similarities)

    //   var resultSet:Seq[String]= Seq[String]();
    var resultarray: ArrayBuffer[String] = new ArrayBuffer[String]


    //  model.assignments.foreach { a =>
    //    println(s"${a.id} , ${a.cluster}")
    //  }
    val cc = model.assignments.collect()
    cc.foreach { a =>
      println(s"${a.id} , ${a.cluster}")
      resultarray += (a.id.toString + "," + a.cluster.toString)
    }

    localReadWrite.resultWrite2local(path, resultarray.toArray)
  }

  // Save and load model
  //  model.save(sc, "myModelPath")
  //  val sameModel = PowerIterationClusteringModel.load(sc, "myModelPath")

  def main(args: Array[String]): Unit = {
    val clusterArray = Array(15, 30, 50, 100, 150)
//    val clusterArray = Array(30, 50, 100, 150)
    for (i <- clusterArray) {
      run("D:\\03datafolder\\data\\output\\0711\\pic_" + i.toString + ".csv", i)
    }

    //    resultWrite2local("",model.assignments.collect().)
  }

}
