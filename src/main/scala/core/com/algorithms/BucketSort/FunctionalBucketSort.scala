package core.com.algorithms.BucketSort

import core.Sorter

class FunctionalBucketSort extends Sorter {
  override val name: String = "BucketSort"
  override val paradigm: String = "Functional"

  override def sort(data: Array[Int]): Array[Int] = {
    if (data.isEmpty) return data
    val maxVal = data.max
    val bucketCount = data.length

    // 1. Funkcja przypisująca element do indeksu kubełka
    def getBucketIdx(v: Int): Int = (v.toDouble / (maxVal + 1) * bucketCount).toInt

    // 2. Grupowanie elementów w mapę (kubełki)
    val bucketsMap = data.toList.groupBy(getBucketIdx)

    // 3. Sortowanie kubełków i ich płaskie złączenie
    val sortedResult = (0 until bucketCount).flatMap { idx =>
      val bucket = bucketsMap.getOrElse(idx, Nil)
      functionalInsertionSort(bucket)
    }

    sortedResult.toArray
  }

  private def functionalInsertionSort(list: List[Int]): List[Int] = {
    def insert(x: Int, sorted: List[Int]): List[Int] = sorted match {
      case Nil => List(x)
      case h :: t => if (x <= h) x :: sorted else h :: insert(x, t)
    }
    list.foldLeft(List.empty[Int])((acc, elem) => insert(elem, acc))
  }
}