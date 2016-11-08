package com.trendmicro.mllib

import java.io.{File, PrintWriter}

import org.apache.commons.io.FileUtils
import org.apache.spark.mllib.classification.{LogisticRegressionModel, LogisticRegressionWithLBFGS}
import org.apache.spark.mllib.evaluation.MulticlassMetrics
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.{SparkConf, SparkContext}


/**
 * Created by herbert_yin on 2016/6/15.
 */
object LogisticRegressionWithLBFGSImpl {

  val conf = new SparkConf().setAppName("LR").setMaster("local").set("spark.hadoop.validateOutputSpecs", "false")
  val sc = new SparkContext(conf)

  //  val sc = new SparkContext("local", "app name")

  // Load training data in LIBSVM format.
  //  val data = MLUtils.loadLibSVMFile(sc, "data/mllib/sample_libsvm_data10.txt")

  def run(input: String, output: String): Unit = {
    val data = MLUtils.loadLibSVMFile(sc, input)

    var model: LogisticRegressionModel = null
    var result: Array[(Double, Double)] = null

    // Split data into training (60%) and test (40%).
    val splits = data.randomSplit(Array(0.8, 0.2), seed = 11L)
    val training = splits(0).cache()
    val test = splits(1)

    val labelCount = data.map { case LabeledPoint(label, features) => label
    }.distinct().collect().max.toInt

    println("labelCount: " + labelCount)

    println("training.count: " + training.count())
    // Run training algorithm to build the model
    //allidfs class 805
    model = new LogisticRegressionWithLBFGS()
      .setNumClasses(labelCount + 1)
      .run(training)

    // Compute raw scores on the test set.
    val predictionAndLabels = test.map { case LabeledPoint(label, features) =>
      val prediction = model.predict(features)
      println(prediction, label)
      (prediction, label)
    }

    result = predictionAndLabels.collect()

    // Get evaluation metrics.
    val metrics = new MulticlassMetrics(predictionAndLabels)
    val precision = metrics.precision
    println("Precision = " + precision)
    resultWrite2local(output, result)

  }

  /**
   * result 2 local
   *
   * @param fpath
   * @param data
   */
  def resultWrite2local(fpath: String, data: Array[(Double, Double)]) = {

    val localFile = new File(fpath)
    if (localFile.exists())
      FileUtils.deleteQuietly(localFile)
    //    Some(new PrintWriter("filename")).foreach{p => p.write("hello world"); p.close}
    val writer = new PrintWriter(localFile)
    data.foreach(p => writer.write(p.toString().concat("\n")))
    //    writer.write(data)
    writer.close()

  }

  // Save and load model
  def saveLrModel2Local(pathl: String, mm: LogisticRegressionModel): Unit = {
    val modelFile =
      mm.save(sc, pathl)
  }

  def loadLrModel(pathl: String): LogisticRegressionModel = {
    val sameModel = LogisticRegressionModel.load(sc, pathl)
    sameModel
  }

  def main(args: Array[String]): Unit = {

        run("D:\\03datafolder\\data\\input\\keywordidfs.csv",
          "D:\\03datafolder\\data\\output\\0711\\lrkeywordout.csv")

    //        val clusterArray = Array(50, 100, 150, 200, 250, 300)
    val clusterArray = Array(30, 50, 100, 150, 0)
    //    val clusterArray = Array(200, 250)
//    for (i <- clusterArray) {
//      var inputbase = "D:\\03datafolder\\data\\input\\0711\\gccallidfs_" + i.toString + ".csv"
//      var outpath = "D:\\03datafolder\\data\\output\\0711\\lrResallidfs_" + i.toString + ".csv"
//
//      run(inputbase, outpath)
//    }

    //    saveLrModel2Local("data/output/lr_tip75idf")
  }
}