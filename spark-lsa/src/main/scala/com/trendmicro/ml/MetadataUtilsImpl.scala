package com.trendmicro.ml

import scala.collection.immutable.HashMap

import org.apache.spark.ml.attribute._
import org.apache.spark.mllib.linalg.VectorUDT
import org.apache.spark.sql.types.StructField


/**
 * Created by herbert_yin on 2016/6/27.
 */
object MetadataUtilsImpl {

  /**
   * Examine a schema to identify the number of classes in a label column.
   * Returns None if the number of labels is not specified, or if the label column is continuous.
   */
  def getNumClasses(labelSchema: StructField): Option[Int] = {
    Attribute.fromStructField(labelSchema) match {
      case binAttr: BinaryAttribute => Some(2)
      case nomAttr: NominalAttribute => nomAttr.getNumValues
      case _: NumericAttribute | UnresolvedAttribute => None
    }
  }

  /**
   * Examine a schema to identify categorical (Binary and Nominal) features.
   *
   * @param featuresSchema  Schema of the features column.
   *                        If a feature does not have metadata, it is assumed to be continuous.
   *                        If a feature is Nominal, then it must have the number of values
   *                        specified.
   * @return  Map: feature index --> number of categories.
   *          The map's set of keys will be the set of categorical feature indices.
   */
  def getCategoricalFeatures(featuresSchema: StructField): Map[Int, Int] = {
    val metadata = AttributeGroup.fromStructField(featuresSchema)
    if (metadata.attributes.isEmpty) {
      HashMap.empty[Int, Int]
    } else {
      metadata.attributes.get.zipWithIndex.flatMap { case (attr, idx) =>
        if (attr == null) {
          Iterator()
        } else {
          attr match {
            case _: NumericAttribute | UnresolvedAttribute => Iterator()
            case binAttr: BinaryAttribute => Iterator(idx -> 2)
            case nomAttr: NominalAttribute =>
              nomAttr.getNumValues match {
                case Some(numValues: Int) => Iterator(idx -> numValues)
                case None => throw new IllegalArgumentException(s"Feature $idx is marked as" +
                  " Nominal (categorical), but it does not have the number of values specified.")
              }
          }
        }
      }.toMap
    }
  }

  /**
   * Takes a Vector column and a list of feature names, and returns the corresponding list of
   * feature indices in the column, in order.
   * @param col  Vector column which must have feature names specified via attributes
   * @param names  List of feature names
   */
  def getFeatureIndicesFromNames(col: StructField, names: Array[String]): Array[Int] = {
    require(col.dataType.isInstanceOf[VectorUDT], s"getFeatureIndicesFromNames expected column $col"
      + s" to be Vector type, but it was type ${col.dataType} instead.")
    val inputAttr = AttributeGroup.fromStructField(col)
    names.map { name =>
      require(inputAttr.hasAttr(name),
        s"getFeatureIndicesFromNames found no feature with name $name in column $col.")
      inputAttr.getAttr(name).index.get
    }
  }
}
