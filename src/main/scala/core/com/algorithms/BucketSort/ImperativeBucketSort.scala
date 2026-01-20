package core.com.algorithms.BucketSort

import core.Sorter
import scala.collection.mutable.ListBuffer

class ImperativeBucketSort extends Sorter {
  override val name: String = "BucketSort"
  override val paradigm: String = "Imperative"

  override def sort(data: Array[Int]): Array[Int] = {
    if (data.length == 0) return data
    val arr = data.clone()
    val n = arr.length

    // 1. Znalezienie wartości maksymalnej do określenia zakresu kubełków
    var maxVal = arr(0)
    for (i <- 1 until n) {
      if (arr(i) > maxVal) maxVal = arr(i)
    }

    // 2. Inicjalizacja kubełków (liczba kubełków = n)
    val bucketCount = n
    val buckets = Array.fill(bucketCount)(new ListBuffer[Int]())

    // 3. Rozdzielenie elementów do kubełków
    for (i <- 0 until n) {
      val bucketIdx = (arr(i).toDouble / (maxVal + 1) * bucketCount).toInt
      buckets(bucketIdx) += arr(i)
    }

    // 4. Sortowanie każdego kubełka i przepisanie do tablicy głównej
    var currentIndex = 0
    for (i <- 0 until bucketCount) {
      val sortedBucket = buckets(i).toArray
      insertionSort(sortedBucket) // sortowanie wewnątrz kubełka
      for (item <- sortedBucket) {
        arr(currentIndex) = item
        currentIndex += 1
      }
    }
    arr
  }

  private def insertionSort(arr: Array[Int]): Unit = {
    for (i <- 1 until arr.length) {
      val key = arr(i)
      var j = i - 1
      while (j >= 0 && arr(j) > key) {
        arr(j + 1) = arr(j)
        j -= 1
      }
      arr(j + 1) = key
    }
  }
}