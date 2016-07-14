/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.trendmicro.mllib

// scalastyle:off println

// $example on$

import org.apache.spark.mllib.clustering.{KMeansModel, KMeans}
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.{SparkContext, SparkConf}

// $example off$

import com.trendmicro.util.localReadWrite

/**
 * An example demonstrating k-means clustering.
 * Run with
 * {{{
 * bin/run-example ml.KMeansExample
 * }}}
 */
object KMeansExample {

  def main(args: Array[String]): Unit = {
    // Creates a SparkSession.

    val conf = new SparkConf().setAppName("LR").setMaster("local").set("spark.hadoop.validateOutputSpecs", "false")
    val sc = new SparkContext(conf)

    //  val sc = new SparkContext("local", "app name")

    // Load training data in LIBSVM format.
    //  val data = MLUtils.loadLibSVMFile(sc, "data/mllib/sample_libsvm_data10.txt")
    val data = MLUtils.loadLibSVMFile(sc, "D:\\2015callintop74idfs.csv")
    //  val data = MLUtils.loadLibSVMFile(sc, "D:\\alltop74idfs.csv")
    //  val data = MLUtils.loadLibSVMFile(sc, "D:\\alltop75idfs.csv")
    //  val data = MLUtils.loadLibSVMFile(sc, "D:\\top10inputidfs.csv")
    //  val data = MLUtils.loadLibSVMFile(sc, "D:\\callintop21idfs.csv")
    //    val data = MLUtils.loadLibSVMFile(sc, "D:\\top100idfs.csv")
    //  val data = MLUtils.loadLibSVMFile(sc, "D:\\top100idfsnew.csv")
    //  val data = MLUtils.loadLibSVMFile(sc, "D:\\sortedtop100idfsnew.csv")

    //  val data = MLUtils.loadLibSVMFile(sc, "D:\\top100idfs1000w.csv")

    var model: KMeansModel = null
    var result: Array[(Double, Double)] = null

    // Split data into training (60%) and test (40%).
    val splits = data.randomSplit(Array(0.7, 0.3), seed = 11L)
    val training = splits(0).cache()

    // Trains a k-means model
    val kmeans = new KMeans()
      .setK(30)
    model = kmeans.run(training.map(xx => xx.features))

    // Shows the result
    println("Final Centers: ")
    //  model.clusterCenters.foreach(println)

    val outkmeams = model.predict(data.map(xy => xy.features)).zipWithIndex().map(vk => vk.swap)


    val lab = data.map(xy => xy.label).zipWithIndex().map(_.swap)

    val output = lab.join(outkmeams).map(o => o._2)

    val outArray = output.collect()

    var outStr: String = ""
    outArray.foreach(r => outStr = outStr.concat(r.toString().replaceAll("[\\(\\)]","").concat("\n")))


    //    output.foreach(r => outStr = outStr.concat(r._2.toString().replaceAll("\\(\\)", "").concat("\\n")))

    localReadWrite.write("D://keams.txt", outStr)


  }
}

// scalastyle:on println
