/*
 * Copyright 2015 Sanford Ryza, Uri Laserson, Sean Owen and Joshua Wills
 *
 * See LICENSE file for further information.
 */

package com.trendmicro.lsa

import breeze.linalg.{DenseMatrix => BDenseMatrix, DenseVector => BDenseVector, SparseVector => BSparseVector}
import com.trendmicro.lsa.ParseWikipedia._
import com.trendmicro.skyaid.common.GccJpTextSegmentation
import org.apache.spark.SparkContext
import org.apache.spark.mllib.linalg._
import org.apache.spark.mllib.linalg.distributed.RowMatrix
import org.apache.spark.rdd.RDD

import scala.collection.Map
import scala.collection.mutable.ArrayBuffer

object RunLSA {

  def main(args: Array[String]) {

    //    val clusterArray = Array("01", "02", "03", "04")
    //        val clusterArray = Array("05", "06", "07", "08")
    //        val clusterArray = Array("09", "10", "11")

//    val clusterArray = Array("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12")
//    for (i <- clusterArray) {
//      run(i)
//    }
            run("12")
  }

  val sc = new SparkContext("local", "app name")
  //  val k = if (args.length > 0) args(0).toInt else 500
  //  val numTerms = if (args.length > 1) args(1).toInt else 3000
  //  val sampleSize = if (args.length > 2) args(2).toDouble else 0.1
  val k = 300
  val numTerms = 1500
  val sampleSize = 0.1

  var trainFile = ""
  var testFile = ""
  var testResultPath = ""

  var traincategoryCount = ""
  var testcategoryCount = ""
  var category2Id = ""

  var termsfreqs = ""
  var termsidfs = ""

  val dataFilterLength: Int = 2
  val topResult: Int = 5
  val filteredNum: Int = 20

  def run(pic: String) = {
    inputOutputPath("D:\\03datafolder\\tombodir\\01_VBC_0914.csv",
      "D:\\03datafolder\\tombodir\\se_result_modify.csv",
      "D:\\03datafolder\\data\\output\\0914\\result_tombo_0914.csv",
      "D:\\03datafolder\\data\\output\\0914\\category_tombo0914.csv",
      "D:\\03datafolder\\data\\output\\0914\\category_2015_0914.csv",
      "D:\\03datafolder\\data\\output\\0914\\categoryIds_tombo0914.csv",
      "D:\\03datafolder\\data\\output\\0914\\termsfreqs_tombo0914.csv",
      "D:\\03datafolder\\data\\output\\0914\\termsidfs_tombo0914.csv")

//    inputOutputPath("D:\\03datafolder\\tombodir\\01_VBC_0912.csv",
//      "D:\\03datafolder\\email_outbound_0912_m\\2015" + pic + ".csv",
//      "D:\\03datafolder\\data\\output\\0901\\result_tombo_0912\\result_tombo2015" + pic + ".csv",
//      "D:\\03datafolder\\data\\output\\0901\\category_tombo0912.csv",
//      "D:\\03datafolder\\data\\output\\0901\\category_2015" + pic + "0912.csv",
//      "D:\\03datafolder\\data\\output\\0901\\categoryIds_tombo0912.csv",
//      "D:\\03datafolder\\data\\output\\0901\\termsfreqs_tombo0912.csv",
//      "D:\\03datafolder\\data\\output\\0901\\termsidfs_tombo0912.csv")

    //    inputOutputPath("D:\\03datafolder\\data\\input\\kbtop80\\kbtop83_out.csv",
    //      "D:\\03datafolder\\data\\input\\0901\\kbtop83_test.csv",
    //      "D:\\03datafolder\\data\\output\\0901\\lsatestresult.csv",
    //      "D:\\03datafolder\\data\\output\\0901\\allcategory.csv",
    //      "D:\\03datafolder\\data\\output\\0901\\test83Category.csv",
    //      "D:\\03datafolder\\data\\output\\0901\\allcategoryIds.csv",
    //      "D:\\03datafolder\\data\\output\\0901\\alltermsfreqs.csv",
    //      "D:\\03datafolder\\data\\output\\0901\\alltermsidfs.csv")

    //getMultipleTermQuery()
    getDoc2DocRelevance()
  }

  def getDoc2DocRelevance() {
    val (termDocMatrix, termIds, docIds, idfs, docFreqsArray) = preprocessing(sampleSize, numTerms, sc)
    termDocMatrix.cache()
    println("termDocMatrix.count(): " + termDocMatrix.count())
    val mat = new RowMatrix(termDocMatrix)
    val svd = mat.computeSVD(k, computeU = true)

    //        println("Singular values: " + svd.s)
    //            val topConceptTerms = topTermsInTopConcepts(svd, 6, 4, termIds)
    //            val topConceptDocs = topDocsInTopConcepts(svd, 6, 4, docIds)
    //            for ((terms, docs) <- topConceptTerms.zip(topConceptDocs)) {
    //              println("homogeneous  terms: " + terms.map(_._1).mkString(", "))
    //              println("homogeneous  category: " + docs.map(_._1).mkString(", "))
    //              println()
    //            }

    val US = multiplyByDiagonalMatrix(svd.U, svd.s)
    val normalizedUS = rowsNormalized(US)
    def printRelevantDocs(mdocName: String): Seq[(String, String, Double)] = {
      val id = getDocIDbyDocName(mdocName, docIds)
      val mtemp = topDocsForDoc(normalizedUS, id)
      saveIdWeights(topDocsForDoc(normalizedUS, id), docIds, mdocName)
    }

    val mTestFile = sc.textFile(traincategoryCount).collect()
//    val mTestFile = sc.textFile("D:\\03datafolder\\data\\input\\0711\\category.csv").collect()
    var muserItemScore: Seq[(String, String, Double)] = Seq[(String, String, Double)]()
    for (x <- mTestFile) {
      println("printRelevantDocs: " + x)
      muserItemScore = muserItemScore.++(printRelevantDocs(x.split("\\,")(0)))
    }
//    mTestFile.foreach(cat => muserItemScore = muserItemScore.++(printRelevantDocs(cat.trim)))
    saveUserItemScore("D:\\03datafolder\\data\\output\\0914\\tombo2tomboScore.csv", muserItemScore)
//    printRelevantDocs("ウイルスログ採取")
//    printRelevantDocs("参考「表示の選択」から確認できる項目")
//    printRelevantDocs("隠しフォルダが表示出来ない場合")
//    printRelevantDocs("正常なファイルと判明")
//    printRelevantDocs("【XP&VISTA&7】HOUSECALL (VER.8.0)")
//    printRelevantDocs("削除ツール+常駐停止+TI10.0インストール")
  }

  /*
  *******************************************
      this is the begin of sentence query
  *******************************************
 */

  def getMultipleTermQuery() {

    val (termDocMatrix, termIds, docIds, idfs, docFreqsArray) = preprocessing(sampleSize, numTerms, sc)
    termDocMatrix.cache()
    println("termDocMatrix.count(): " + termDocMatrix.count())
    val mat = new RowMatrix(termDocMatrix)
    val svd = mat.computeSVD(k, computeU = true)

    //        println("Singular values: " + svd.s)
    //            val topConceptTerms = topTermsInTopConcepts(svd, 6, 4, termIds)
    //            val topConceptDocs = topDocsInTopConcepts(svd, 6, 4, docIds)
    //            for ((terms, docs) <- topConceptTerms.zip(topConceptDocs)) {
    //              println("homogeneous  terms: " + terms.map(_._1).mkString(", "))
    //              println("homogeneous  category: " + docs.map(_._1).mkString(", "))
    //              println()
    //            }

    val US = multiplyByDiagonalMatrix(svd.U, svd.s)
    val normalizedUS = rowsNormalized(US)

    val idTerms = termIds.map(_.swap)

    def printTermsRelevantDocs(terms: (String, Seq[String])): (String, Int, Seq[(String, Double)]) = {
      val queryVec = termsToQueryVector(terms._2, idTerms, idfs)
      val idWeights = topDocsForTermQuery(US, svd.V, queryVec)
      val docNameWeiths = idWeights.map { case (score, id) => (docIds(id), score) }

      (terms._1, if (filterSeqStringTerms(docNameWeiths, terms._1)) 1 else 0, docNameWeiths)

      //      //      printIdWeights(topDocsForTermQuery(US, svd.V, queryVec), docIds)
      //      //      println("the right category is :" + terms._1)
    }

    val mTestFile = sc.textFile(testFile)
    val plainText = mTestFile.map(s => s.split("\\,"))
    val filteredPlainText = {
      plainText.filter(ptArray => ptArray.length >= dataFilterLength)
    }
    println("filteredPlainText.count: " + filteredPlainText.count())
    val jpSeqrdd = filteredPlainText.map(sa => (sa(4), GccJpTextSegmentation.wordSegmentationForJPWords(sa(5)).toArray(new Array[String](0)).toSeq))

    saveCategorycount(testcategoryCount, jpSeqrdd)
    val maprdd = jpSeqrdd.map(filterArrayArrayRdd(docFreqsArray, _)).filter(_._2.size > filteredNum)
    val filteredArray = maprdd.collect()

    var seqResult = Seq[(String, Int, Seq[(String, Double)])]()
    for (mTulple <- filteredArray) {
      val result = printTermsRelevantDocs(mTulple)
      seqResult = seqResult :+ result
    }

    println("seqResult:" + seqResult.size)
    saveResult(testResultPath, seqResult)

  }


  /**
   * inorder to change path easily
   *
   * @param mtrainFile
   * @param mtestFile
   * @param mtestResult
   * @param mcategorycount
   * @param mcategory2Id
   * @param mtermsfreqs
   * @param mtermsidfs
   */
  def inputOutputPath(mtrainFile: String, mtestFile: String, mtestResult: String, mcategorycount: String, mtestcategoryCount: String,
                      mcategory2Id: String, mtermsfreqs: String, mtermsidfs: String) = {
    trainFile = mtrainFile
    testFile = mtestFile
    testResultPath = mtestResult
    traincategoryCount = mcategorycount
    testcategoryCount = mtestcategoryCount
    category2Id = mcategory2Id
    termsfreqs = mtermsfreqs
    termsidfs = mtermsidfs
  }


  /**
   * filter rdd
   *
   * @param termArray
   * @param categoryTermTuple
   * @return
   */
  def filterArrayArrayRdd(termArray: Array[(String, Int)], categoryTermTuple: (String, Seq[String])): (String, Seq[String]) = {
    val filteredjpSeq = categoryTermTuple._2.filter(word => filterArrayStringTerms(termArray, word.toString))
    (categoryTermTuple._1, filteredjpSeq)
  }

  /**
   * filter rdd
   *
   * @param termArray
   * @param categoryTermTuple
   * @return
   */
  def filterArrayArrayRddflag(termArray: Array[(String, Int)], categoryTermTuple: (String, Seq[String])): Boolean = {

    var filterFlag = false

    for (jpSeq <- categoryTermTuple._2) {
      val filteredjpSeq = jpSeq.filter(word => filterArrayStringTerms(termArray, word.toString))
      //            println(jpSeq.toString())
      //      println("filteredjpSeq: "+filteredjpSeq.toString())
      //      println("###################################################")
      if (filteredjpSeq.size > 0) {
        filterFlag = true
      } else {
        println("error: this case didn't have any invalid terms. #" + jpSeq.toString())
      }

    }

    filterFlag
  }


  /**
   * filter the words didn't contains in the top n List
   *
   * @param termArray
   * @param termStr
   * @return
   */
  def filterArrayStringTerms(termArray: Array[(String, Int)], termStr: String): Boolean = {
    var i = 0
    var foundIt = false
    while (i < termArray.length && !foundIt) {
      if (termArray(i)._1.equals(termStr)) {
        foundIt = true
      }
      i = i + 1
    }
    foundIt
  }

  /**
   * filter the words didn't contains in the top n List
   *
   * @param termArray
   * @param termStr
   * @return
   */
  def filterSeqStringTerms(termArray: Seq[(String, Double)], termStr: String): Boolean = {
    var i = 0
    var foundIt = false
    while (i < termArray.length && !foundIt) {
      if (termArray(i)._1.equals(termStr)) {
        foundIt = true
      }
      i = i + 1
    }
    foundIt
  }

  /**
   * get docID by the docName, if not match, return the Long.MaxValue
   *
   * @param docName
   * @param idDocsmap
   * @return
   */
  def getDocIDbyDocName(docName: String, idDocsmap: Map[Long, String]): Long = {
    for ((k, v) <- idDocsmap) {
      if (v.equals(docName))
        return k
    }
    Long.MaxValue
  }

  /**
   * Returns an RDD of rows of the document-term matrix, a mapping of column indices to terms, and a
   * mapping of row IDs to document titles.
   */
  def preprocessing(sampleSize: Double, numTerms: Int, sc: SparkContext)
  : (RDD[Vector], Map[Int, String], Map[Long, String], Map[String, Double], Array[(String, Int)]) = {

    val gccTrainingData = sc.textFile(trainFile)
    //    val gccTrainingData = sc.wholeTextFiles(trainFile)
    val plainText = gccTrainingData.map(s => s.split("\\,"))


    //        val collectpl = plainText.collect()
    //        for(pt <- collectpl) {
    //          if(pt.length != 3)
    //            println("pt.length: " + pt.length + " # " + pt(0))
    //        }

    //    println("plainText.count: " + plainText.count())
    //note: a filter is needed to filter the array length != 6
    val filteredPlainText = {
      plainText.filter(ptArray => ptArray.length >= dataFilterLength)
    }
    //        println("filteredPlainText.count: " + filteredPlainText.collect().length)

    val plainTextSeq = filteredPlainText.map(sa => (sa(0), GccJpTextSegmentation.wordSegmentationForJPWords(sa(1)).toArray(new Array[String](0)).toSeq))
    //    val plainTextSeq = filteredPlainText.map(sa => (sa(7), GccJpTextSegmentation.wordSegmentationForJPWords(sa(16)).toArray(new Array[String](0)).toSeq))

    val filterRDD = plainTextSeq.filter(_._2.size > filteredNum)
    val filtered = filterRDD.reduceByKey((x, y) => x ++ y)

    saveCategorycount(traincategoryCount, filterRDD)

    val stopWords = sc.broadcast(loadStopWords("D:\\03datafolder\\data\\input\\jpstopwords.txt")).value

    documentTermMatrix(filtered, stopWords, numTerms, sc)
  }

  /**
   *
   * @param filePath
   * @param rddc
   */
  def saveCategorycount(filePath: String, rddc: RDD[(String, Seq[String])]): Unit = {
    val categorycount = rddc.map(xy => (xy._1, 1)).reduceByKey(_ + _).collect()

    saveDocFreqs(filePath, categorycount)

  }

  /**
   * get top
   *
   * @param svd
   * @param numConcepts
   * @param numTerms
   * @param termIds
   * @return
   */
  def topTermsInTopConcepts(svd: SingularValueDecomposition[RowMatrix, Matrix], numConcepts: Int,
                            numTerms: Int, termIds: Map[Int, String]): Seq[Seq[(String, Double)]] = {
    val v = svd.V
    val topTerms = new ArrayBuffer[Seq[(String, Double)]]()
    val arr = v.toArray
    for (i <- 0 until numConcepts) {
      val offs = i * v.numRows
      val termWeights = arr.slice(offs, offs + v.numRows).zipWithIndex
      val sorted = termWeights.sortBy(-_._1)
      topTerms += sorted.take(numTerms).map { case (score, id) => (termIds(id), score) }
    }
    topTerms
  }

  def topDocsInTopConcepts(svd: SingularValueDecomposition[RowMatrix, Matrix], numConcepts: Int,
                           numDocs: Int, docIds: Map[Long, String]): Seq[Seq[(String, Double)]] = {
    val u = svd.U
    val topDocs = new ArrayBuffer[Seq[(String, Double)]]()
    for (i <- 0 until numConcepts) {
      val docWeights = u.rows.map(_.toArray(i)).zipWithUniqueId
      topDocs += docWeights.top(numDocs).map { case (score, id) => (docIds(id), score) }
    }
    topDocs
  }

  /**
   * Selects a row from a matrix.
   */
  def row(mat: BDenseMatrix[Double], index: Int): Seq[Double] = {
    (0 until mat.cols).map(c => mat(index, c))
  }

  /**
   * Selects a row from a matrix.
   */
  def row(mat: Matrix, index: Int): Seq[Double] = {
    val arr = mat.toArray
    (0 until mat.numCols).map(i => arr(index + i * mat.numRows))
  }

  /**
   * Selects a row from a distributed matrix.
   */
  def row(mat: RowMatrix, id: Long): Array[Double] = {
    mat.rows.zipWithUniqueId.map(_.swap).lookup(id).head.toArray
  }

  /**
   * Finds the product of a dense matrix and a diagonal matrix represented by a vector.
   * Breeze doesn't support efficient diagonal representations, so multiply manually.
   */
  def multiplyByDiagonalMatrix(mat: Matrix, diag: Vector): BDenseMatrix[Double] = {
    val sArr = diag.toArray
    new BDenseMatrix[Double](mat.numRows, mat.numCols, mat.toArray)
      .mapPairs { case ((r, c), v) => v * sArr(c) }
  }

  /**
   * Finds the product of a distributed matrix and a diagonal matrix represented by a vector.
   */
  def multiplyByDiagonalMatrix(mat: RowMatrix, diag: Vector): RowMatrix = {
    val sArr = diag.toArray
    new RowMatrix(mat.rows.map(vec => {
      val vecArr = vec.toArray
      val newArr = (0 until vec.size).toArray.map(i => vecArr(i) * sArr(i))
      Vectors.dense(newArr)
    }))
  }

  /**
   * Returns a matrix where each row is divided by its length.
   */
  def rowsNormalized(mat: BDenseMatrix[Double]): BDenseMatrix[Double] = {
    val newMat = new BDenseMatrix[Double](mat.rows, mat.cols)
    for (r <- 0 until mat.rows) {
      val length = math.sqrt((0 until mat.cols).map(c => mat(r, c) * mat(r, c)).sum)
      (0 until mat.cols).map(c => newMat.update(r, c, mat(r, c) / length))
    }
    newMat
  }

  /**
   * Returns a distributed matrix where each row is divided by its length.
   */
  def rowsNormalized(mat: RowMatrix): RowMatrix = {
    new RowMatrix(mat.rows.map(vec => {
      val length = math.sqrt(vec.toArray.map(x => x * x).sum)
      Vectors.dense(vec.toArray.map(_ / length))
    }))
  }

  /**
   * Finds terms relevant to a term. Returns the term IDs and scores for the terms with the highest
   * relevance scores to the given term.
   */
  def topTermsForTerm(normalizedVS: BDenseMatrix[Double], termId: Int): Seq[(Double, Int)] = {
    // Look up the row in VS corresponding to the given term ID.
    val termRowVec = new BDenseVector[Double](row(normalizedVS, termId).toArray)

    // Compute scores against every term
    val termScores = (normalizedVS * termRowVec).toArray.zipWithIndex

    // Find the terms with the highest scores
    termScores.sortBy(-_._1).take(10)
  }

  /**
   * Finds docs relevant to a doc. Returns the doc IDs and scores for the docs with the highest
   * relevance scores to the given doc.
   */
  def topDocsForDoc(normalizedUS: RowMatrix, docId: Long): Seq[(Double, Long)] = {
    // Look up the row in US corresponding to the given doc ID.
    val docRowArr = row(normalizedUS, docId)
    val docRowVec = Matrices.dense(docRowArr.length, 1, docRowArr)

    // Compute scores against every doc
    val docScores = normalizedUS.multiply(docRowVec)

    // Find the docs with the highest scores
    val allDocWeights = docScores.rows.map(_.toArray(0)).zipWithUniqueId

    // Docs can end up with NaN score if their row in U is all zeros.  Filter these out.
    allDocWeights.filter(!_._1.isNaN).top(topResult)
  }

  /**
   * Finds docs relevant to a term. Returns the doc IDs and scores for the docs with the highest
   * relevance scores to the given term.
   */
  def topDocsForTerm(US: RowMatrix, V: Matrix, termId: Int): Seq[(Double, Long)] = {
    val termRowArr = row(V, termId).toArray
    val termRowVec = Matrices.dense(termRowArr.length, 1, termRowArr)

    // Compute scores against every doc
    val docScores = US.multiply(termRowVec)

    // Find the docs with the highest scores
    val allDocWeights = docScores.rows.map(_.toArray(0)).zipWithUniqueId
    allDocWeights.top(topResult)
  }

  def termsToQueryVector(terms: Seq[String], idTerms: Map[String, Int], idfs: Map[String, Double])
  : BSparseVector[Double] = {
    val indices = terms.map(idTerms(_)).toArray
    val values = terms.map(idfs(_)).toArray
    new BSparseVector[Double](indices, values, idTerms.size)
  }

  def topDocsForTermQuery(US: RowMatrix, V: Matrix, query: BSparseVector[Double])
  : Seq[(Double, Long)] = {
    val breezeV = new BDenseMatrix[Double](V.numRows, V.numCols, V.toArray)
    val termRowArr = (breezeV.t * query).toArray

    val termRowVec = Matrices.dense(termRowArr.length, 1, termRowArr)

    // Compute scores against every doc
    val docScores = US.multiply(termRowVec)

    // Find the docs with the highest scores
    val allDocWeights = docScores.rows.map(_.toArray(0)).zipWithUniqueId
    allDocWeights.top(topResult)
  }

  def printTopTermsForTerm(normalizedVS: BDenseMatrix[Double],
                           term: String, idTerms: Map[String, Int], termIds: Map[Int, String]) {
    printIdWeights(topTermsForTerm(normalizedVS, idTerms(term)), termIds)
  }

  /**
   * doc2doc
   *
   * @param normalizedUS
   * @param doc
   * @param idDocs
   * @param docIds
   */
  def printTopDocsForDoc(normalizedUS: RowMatrix, doc: String, idDocs: Map[String, Long],
                         docIds: Map[Long, String]) {
    printIdWeights(topDocsForDoc(normalizedUS, idDocs(doc)), docIds)
  }

  def printTopDocsForTerm(US: RowMatrix, V: Matrix, term: String, idTerms: Map[String, Int],
                          docIds: Map[Long, String]) {
    printIdWeights(topDocsForTerm(US, V, idTerms(term)), docIds)
  }

  def printIdWeights[T](idWeights: Seq[(Double, T)], entityIds: Map[T, String]) {
    println(idWeights.map { case (score, id) => (entityIds(id), score) }.mkString(", "))
  }

  def saveIdWeights[T](idWeights: Seq[(Double, T)], entityIds: Map[T, String], docname: String): Seq[(String, String, Double)] = {
    //    println(idWeights.map { case (score, id) => (docname,entityIds(id), score) }.filter(_._2.equals(docname)).mkString(","))
    val userItemScore = idWeights.map { case (score, id) => (docname, entityIds(id), score) }.filter(!_._2.equals(docname))
    userItemScore
  }
}
