package com.trendmicro.util

import java.io.{File, PrintWriter}
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths, StandardOpenOption}

import org.apache.commons.io.FileUtils

import scala.collection.JavaConverters._

/**
  * Created by herbert_yin on 2016/6/30.
  */
object localReadWrite {

  /**
    * result 2 local
    *
    * @param fpath
    * @param data
    */
  def resultWrite2local(fpath: String, data: Array[String]) = {

    val localFile = new File(fpath)
    if (localFile.exists())
      FileUtils.deleteQuietly(localFile)
    //    Some(new PrintWriter("filename")).foreach{p => p.write("hello world"); p.close}
    val writer = new PrintWriter(localFile)
    data.foreach(p => writer.write(p.toString().concat("\n")))
    //    writer.write(data)
    writer.close()
  }

  def write(filePath: String, contents: String) = {
    Files.write(Paths.get(filePath), contents.getBytes(StandardCharsets.UTF_8), StandardOpenOption.TRUNCATE_EXISTING)
  }

  def read(filePath: String): String = {
    Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8).asScala.mkString
  }

}
