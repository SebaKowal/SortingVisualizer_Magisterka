package core.com.algorithms.BucketSort

import core.Sorter
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class ParallelBucketSort extends Sorter {
  override val name: String = "BucketSort"
  override val paradigm: String = "Parallel"

  override def sort(data: Array[Int]): Array[Int] = {
    if (data.isEmpty) return data
    val maxVal = data.max
    val n = data.length

    // 1. Rozdzielenie do kubełków (sekwencyjne)
    val buckets = Array.fill(n)(scala.collection.mutable.ListBuffer[Int]())
    for (x <- data) {
      val idx = (x.toDouble / (maxVal + 1) * n).toInt
      buckets(idx) += x
    }

    // 2. Sortowanie kubełków równolegle
    val futures = buckets.map { bucket =>
      Future {
        val arr = bucket.toArray
        java.util.Arrays.sort(arr) // Wykorzystujemy szybkie sortowanie systemowe
        arr
      }
    }

    // 3. Oczekiwanie na wyniki i scalenie
    val sortedChunks = Await.result(Future.sequence(futures.toList), Duration.Inf)

    // Spłaszczanie wyników do jednej tablicy
    val result = new Array[Int](n)
    var curr = 0
    for (chunk <- sortedChunks; item <- chunk) {
      result(curr) = item
      curr += 1
    }
    result
  }
}