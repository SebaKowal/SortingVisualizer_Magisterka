package core.com.algorithms.InsertionSort

import core.Sorter
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class ParallelInsertionSort extends Sorter {
  override val name: String = "InsertionSort"
  override val paradigm: String = "Parallel"

  override def sort(data: Array[Int]): Array[Int] = {
    val n = data.length
    if (n <= 1) return data.clone()

    // 1. Określamy liczbę wątków i wielkość paczki
    val numThreads = Runtime.getRuntime.availableProcessors()
    val chunkSize = Math.max(1, n / numThreads)

    // 2. Dzielimy tablicę i sortujemy każdą część równolegle przez Insertion Sort
    val futures = (0 until numThreads).map { i =>
      val start = i * chunkSize
      val end = if (i == numThreads - 1) n else Math.min((i + 1) * chunkSize, n)

      Future {
        val subArray = data.slice(start, end)
        insertionSortInPlace(subArray)
      }
    }

    // 3. Czekamy na wyniki z wątków
    val sortedChunks = Await.result(Future.sequence(futures), Duration.Inf)

    // 4. Scalamy posortowane kawałki w jeden finalny wynik (Parallel Merge)
    sortedChunks.reduce((a, b) => merge(a, b))
  }

  // Klasyczny Insertion Sort używany lokalnie w wątku
  private def insertionSortInPlace(arr: Array[Int]): Array[Int] = {
    for (i <- 1 until arr.length) {
      val key = arr(i)
      var j = i - 1
      while (j >= 0 && arr(j) > key) {
        arr(j + 1) = arr(j)
        j -= 1
      }
      arr(j + 1) = key
    }
    arr
  }

  // Funkcja scalająca dwa posortowane fragmenty
  private def merge(left: Array[Int], right: Array[Int]): Array[Int] = {
    val result = new Array[Int](left.length + right.length)
    var i = 0; var j = 0; var k = 0
    while (i < left.length && j < right.length) {
      if (left(i) <= right(j)) { result(k) = left(i); i += 1 }
      else { result(k) = right(j); j += 1 }
      k += 1
    }
    while (i < left.length) { result(k) = left(i); i += 1; k += 1 }
    while (j < right.length) { result(k) = right(j); j += 1; k += 1 }
    result
  }
}