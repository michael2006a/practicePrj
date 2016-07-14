package com.trendmicro.util

import com.google.common.collect.Lists
import org.apache.commons.io.FileUtils

import scala.collection.JavaConverters._
import scala.collection._

import java.io.{File, Reader}

import org.apache.lucene.analysis.{Analyzer, AnalyzerWrapper, Tokenizer, TokenStream}
import org.apache.lucene.analysis.cjk._
import org.apache.lucene.analysis.core._
import org.apache.lucene.analysis.ja._
import org.apache.lucene.analysis.ngram._
import org.apache.lucene.analysis.standard._
import org.apache.lucene.analysis.tokenattributes._
import org.apache.lucene.analysis.util._
import org.apache.lucene.util.Version

//import scala.reflect.io.File

/**
 * Created by herbert_yin on 2016/6/8.
 */
object LuceneAnalyzerPerField {

  val FIELD_NAME = "text-japanese-search"

  def main(args: Array[String]): Unit = {
    val luceneVersion = Version.LATEST
    val analyzer = createAnalyzer()

    val inputList = FileUtils.readLines(new File("D:\\plaintext.txt")).toArray
//        val outputLis = Lists.newArrayListWithCapacity(inputList.size)
//        import scala.collection.JavaConversions._
//        for (text <- inputList) {
//          val tokenStream = analyzer.tokenStream(FIELD_NAME, text.toString)
//          val charTermAttribute = tokenStream.addAttribute(classOf[CharTermAttribute])
//
//          tokenStream.reset()
//
//          val temp = Iterator
//            .continually(tokenStream)
//            .takeWhile(_.incrementToken())
//            .map(t => charTermAttribute.toString)
//            .mkString("  Tokenize[", " ", "]")
//          println {
//            temp
//          }
//
//          tokenStream.close()
//
//          println()
//        }

    for (line <- inputList) {
      val outSeq = texttokenWithJapaneseAnalyzer(line.toString)
      println(outSeq)
      println()
    }

    //    for {
//          text <- Array("すもももももももものうち。",
//            "メガネは顔の一部です。",
//            "日本経済新聞でモバゲーの記事を読んだ。",
//            "日本経済新聞でモバゲーの記事を読んだ。日本経済新聞でモバゲーの記事を読んだ,日本経済新聞でモバゲーの記事を読んだ",
//            "Java, Scala, Groovy, Clojure",
//            "ＬＵＣＥＮＥ、ＳＯＬＲ、Lucene, Solr",
//            "ｱｲｳｴｵカキクケコさしすせそABCＸＹＺ123４５６",
//            "Lucene is a full-featured text search engine library written in Java.")
    //      fieldName <- Array("text-cjk", "text-japanese-search")
    //    } {
    //      println(s">$fieldName:")
    //      println(s"  Original[$text]")
    //
    //      val tokenStream = analyzer.tokenStream(fieldName, text)
    //      val charTermAttribute = tokenStream.addAttribute(classOf[CharTermAttribute])
    //
    //      tokenStream.reset()
    //
    //      println {
    //        Iterator
    //          .continually(tokenStream)
    //          .takeWhile(_.incrementToken())
    //          .map(t => charTermAttribute.toString)
    //          .mkString("  Tokenize[", " ", "]")
    //      }
    //
    //      tokenStream.close()
    //
    //      println()
    //    }

  }

  def texttokenWithJapaneseAnalyzer(line: String): Seq[String] = {

    val luceneVersion = Version.LATEST
    val analyzer = createAnalyzer()
println("analyze: " + analyzer.toString)
    val tokenStream = analyzer.tokenStream(FIELD_NAME, line)
    val charTermAttribute = tokenStream.addAttribute(classOf[CharTermAttribute])

    tokenStream.reset()

    val splittedWords = Iterator
      .continually(tokenStream)
      .takeWhile(_.incrementToken())
      .map(t => charTermAttribute.toString)

    tokenStream.end()
    tokenStream.close()
    splittedWords.toSeq
  }

  private def createAnalyzer(): Analyzer = {
    new JapaneseAnalyzer()
  }

  private def createAnalyzer(luceneVersion: Version): Analyzer = {
    //new JapaneseAnalyzer(luceneVersion)
    //new MyJapaneseAnalyzer(luceneVersion)
    //new AnalyzerPerField(luceneVersion)
    new AnalyzerWrapperPerField(luceneVersion)
  }
}

class MyJapaneseAnalyzer(luceneVersion: Version) extends JapaneseAnalyzer() {
  override protected def createComponents(fieldName: String): Analyzer.TokenStreamComponents = {
    println(s"fieldName = $fieldName")
    super.createComponents(fieldName)
  }
}

class AnalyzerPerField(matchVersion: Version) extends Analyzer(Analyzer.PER_FIELD_REUSE_STRATEGY) {
  override protected def createComponents(fieldName: String): Analyzer.TokenStreamComponents =
    fieldName match {
      case "text-cjk" =>
        val tokenizer = new StandardTokenizer()
        var stream: TokenStream = new CJKWidthFilter(tokenizer)
        stream = new LowerCaseFilter(stream)
        stream = new CJKBigramFilter(stream)
        stream = new StopFilter(stream, CJKAnalyzer.getDefaultStopSet)
        new Analyzer.TokenStreamComponents(tokenizer, stream)
      case "text-ngram" =>
        val tokenizer = new WhitespaceTokenizer()
        var stream: TokenStream = new CJKWidthFilter(tokenizer)
        stream = new NGramTokenFilter(stream, 2, 2)
        stream = new LowerCaseFilter(stream)
        new Analyzer.TokenStreamComponents(tokenizer, stream)
      case "text-japanese-search" =>
        val tokenizer = new JapaneseTokenizer(null, true, JapaneseTokenizer.Mode.SEARCH)
        var stream: TokenStream = new JapaneseBaseFormFilter(tokenizer)
        stream = new JapanesePartOfSpeechStopFilter(stream, JapaneseAnalyzer.getDefaultStopTags)
        stream = new CJKWidthFilter(stream)
        stream = new StopFilter(stream, JapaneseAnalyzer.getDefaultStopSet)
        stream = new JapaneseKatakanaStemFilter(stream)
        stream = new LowerCaseFilter(stream)
        new Analyzer.TokenStreamComponents(tokenizer, stream)
    }
}

class AnalyzerWrapperPerField(matchVersion: Version) extends AnalyzerWrapper(Analyzer.PER_FIELD_REUSE_STRATEGY) {
  override def getWrappedAnalyzer(fieldName: String): Analyzer =
    fieldName match {
      case "text-cjk" => new CJKAnalyzer()
      case "text-ngram" =>
        new Analyzer {
          override protected def createComponents(fieldName: String): Analyzer.TokenStreamComponents = {
            val tokenizer = new WhitespaceTokenizer()
            var stream: TokenStream = new CJKWidthFilter(tokenizer)
            stream = new NGramTokenFilter(stream, 2, 2)
            stream = new LowerCaseFilter(stream)
            new Analyzer.TokenStreamComponents(tokenizer, stream)
          }
        }
      case "text-japanese-search" => new JapaneseAnalyzer()
    }

  def getWrappedAnalyzer(): Analyzer = new JapaneseAnalyzer()
}