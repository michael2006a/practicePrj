package com.trendmicro.mllib

import java.io.{PrintWriter, File}

import org.apache.commons.io.FileUtils
import org.apache.spark.ml.classification.RandomForestClassifier
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.tree.RandomForest
import org.apache.spark.mllib.tree.model.RandomForestModel
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.ml.classification.RandomForestClassificationModel

/**
 * Created by herbert_yin on 2016/6/17.
 */
  object RandomForestImpl {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RandomForestClassificationExample").setMaster("local")
    val sc = new SparkContext(conf)
    // $example on$
    // Load and parse the data file.
    //    val data = MLUtils.loadLibSVMFile(sc, "data/mllib/sample_libsvm_data10.txt")
//    val data = MLUtils.loadLibSVMFile(sc, "D:\\allidfs.csv")
//    val data = MLUtils.loadLibSVMFile(sc, "D:\\top10inputidfs.csv")
//    val data = MLUtils.loadLibSVMFile(sc, "D:\\top100idfs.csv")
//    val data = MLUtils.loadLibSVMFile(sc, "D:\\top100idfs1000w.csv")
    val data = MLUtils.loadLibSVMFile(sc, "D:\\03datafolder\\data\\input\\keywordidfs.csv")
    // Split the data into training and test sets (30% held out for testing)
    val splits = data.randomSplit(Array(0.7, 0.3))
    val (trainingData, testData) = (splits(0), splits(1))

    val labelCount = data.map { case LabeledPoint(label, features) => label
    }.distinct().collect().max.toInt

    println("labelCount: " + labelCount)

    // Train a RandomForest model.
    // Empty categoricalFeaturesInfo indicates all features are continuous.
    val numClasses = labelCount + 1
    val categoricalFeaturesInfo = Map[Int, Int]()
    val numTrees = 100 // Use more in practice.
    val featureSubsetStrategy = "auto" // Let the algorithm choose.
    val impurity = "gini"
    val maxDepth = 10
    val maxBins = 32

    val model = RandomForest.trainClassifier(trainingData, numClasses, categoricalFeaturesInfo,
      numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins)

    val rfc= new RandomForestClassifier();
//    RandomForestClassificationModel rfm = RandomForestClassificationModel.fromOld(model, rfc, categoricalFeaturesInfo, numClasses);
//    println(rfm.featureImportances());

    // Evaluate model on test instances and compute test error
    val labelAndPreds = testData.map { point =>
      val prediction = model.predict(point.features)
      (point.label, prediction)
    }


    val testErr = labelAndPreds.filter(r => r._1 != r._2).count.toDouble / testData.count()
    println("Test Error = " + testErr)
    //    println("Learned classification forest model:\n" + model.toDebugString)

    resultWrite2local("D:\\03datafolder\\data\\input\\rfkeywordidfs.csv",labelAndPreds.collect())
    // Save and load model
    //    model.save(sc, "target/tmp/myRandomForestClassificationModel")
    //    val sameModel = RandomForestModel.load(sc, "target/tmp/myRandomForestClassificationModel")
  }

  /**
   * result 2 local
   * @param fpath
   * @param data
   */
  def resultWrite2local(fpath:String,data:Array[(Double,Double)])={

    val localFile = new File(fpath)
    if(localFile.exists())
      FileUtils.deleteQuietly(localFile)
    //    Some(new PrintWriter("filename")).foreach{p => p.write("hello world"); p.close}
    val writer = new PrintWriter(localFile)
    data.foreach(p => writer.write(p.toString().concat("\n")))
    //    writer.write(data)
    writer.close()

  }
}
