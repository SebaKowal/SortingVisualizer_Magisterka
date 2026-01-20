package core.com.algorithms.SelectionSort

import core.Sorter
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class ParallelSelectionSort extends Sorter {
  override val name: String = "SelectionSort"
  override val paradigm: String = "Parallel"

  override def sort(data: Array[Int]): Array[Int] = {
    val arr = data.clone()
    val n = arr.length
    val numThreads = Runtime.getRuntime.availableProcessors()

    for (i <- 0 until n - 1) {
      val remainingLength = n - i
      val chunkSize = Math.max(1, remainingLength / numThreads)

      val futures = (0 until numThreads).map { t =>
        Future {
          val start = i + (t * chunkSize)
          val end = if (t == numThreads - 1) n else Math.min(start + chunkSize, n)

          var localMinIdx = start
          if (start < n) {
            for (j <- start until end) {
              if (arr(j) < arr(localMinIdx)) localMinIdx = j
            }
          }
          localMinIdx
        }
      }

      val localMinimaIndices = Await.result(Future.sequence(futures), Duration.Inf)
      var globalMinIdx = i
      for (idx <- localMinimaIndices) {
        if (arr(idx) < arr(globalMinIdx)) globalMinIdx = idx
      }

      val temp = arr(i)
      arr(i) = arr(globalMinIdx)
      arr(globalMinIdx) = temp
    }
    arr
  }
}