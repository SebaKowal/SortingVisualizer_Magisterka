package core.com.algorithms.BinarySearch

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class ParallelBinarySearch {
  val name: String = "BinarySearch"
  val paradigm: String = "Parallel"

  def search(data: Array[Int], target: Int): Int = {
    if (data.isEmpty) return -1

    // Dzielimy tablicę na 4 części sprawdzane jednocześnie
    val numParts = 4
    val chunkSize = Math.max(1, data.length / numParts)

    val futures = (0 until numParts).map { i =>
      Future {
        val start = i * chunkSize
        val end = if (i == numParts - 1) data.length else (i + 1) * chunkSize

        // W każdym wątku odpalamy klasyczny Binary Search na fragmencie
        searchInRange(data, target, start, end - 1)
      }
    }

    val results = Await.result(Future.sequence(futures), Duration.Inf)
    // Zwracamy pierwszy indeks, który nie jest -1
    results.find(_ != -1).getOrElse(-1)
  }

  private def searchInRange(arr: Array[Int], target: Int, low: Int, high: Int): Int = {
    var l = low
    var h = high
    while (l <= h) {
      val mid = l + (h - l) / 2
      if (arr(mid) == target) return mid
      else if (arr(mid) < target) l = mid + 1
      else h = mid - 1
    }
    -1
  }
}