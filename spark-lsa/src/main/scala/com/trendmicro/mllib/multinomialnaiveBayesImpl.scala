package com.trendmicro.mllib

import org.apache.spark.SparkContext
import org.apache.spark.mllib.classification.{NaiveBayes, NaiveBayesModel}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.util.MLUtils

/**
 * Created by herbert_yin on 2016/6/17.
 */
object multinomialnaiveBayesImpl {

  val sc = new SparkContext("local", "app name")

  var model: NaiveBayesModel = null

//  val data = sc.textFile("data/mllib/sample_naive_bayes_data.txt")
//  val parsedData = data.map { line =>
//    val parts = line.split(',')
//    LabeledPoint(parts(0).toDouble, Vectors.dense(parts(1).split(' ').map(_.toDouble)))
//  }

//  val data = MLUtils.loadLibSVMFile(sc, "data/mllib/sample_libsvm_data10.txt")
//  val data = MLUtils.loadLibSVMFile(sc, "D:\\allidfs.csv")
//  val data = MLUtils.loadLibSVMFile(sc, "D:\\top10inputidfs.csv")

//  val data = MLUtils.loadLibSVMFile(sc, "D:\\callintop21idfs.csv")
//  val data = MLUtils.loadLibSVMFile(sc, "D:\\top100idfs.csv")
  val data = MLUtils.loadLibSVMFile(sc, "D:\\alltop75idfs.csv")

  // Split data into training (60%) and test (40%).
  val splits = data.randomSplit(Array(0.6, 0.4), seed = 11L)
  val training = splits(0)
  val test = splits(1)

  /**
   *
   */
  def run() = {

    model = NaiveBayes.train(training, lambda = 1.0, modelType = "multinomial")

    val predictionAndLabel = test.map(p => (model.predict(p.features), p.label))
    val accuracy = 1.0 * predictionAndLabel.filter(x => x._1 == x._2).count() / test.count()

    println("accuracy: " + accuracy)
  }

  /**
   *
   * @param pathb
   */
  def saveLrModel2Local(pathb: String) = {
    // Save and load model
    model.save(sc, pathb)
    val sameModel = NaiveBayesModel.load(sc, pathb)
  }

  def main(args: Array[String]): Unit = {
    run
    //    saveLrModel2Local("data/out/lr_allidf.mod")
  }

}
