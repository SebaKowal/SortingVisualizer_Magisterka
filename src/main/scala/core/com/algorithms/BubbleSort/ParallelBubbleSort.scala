package core.com.algorithms.BubbleSort

import core.Sorter
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class ParallelBubbleSort extends Sorter {
  override val name: String = "BubbleSort"
  override val paradigm: String = "Parallel"

  override def sort(data: Array[Int]): Array[Int] = {
    val arr = data.clone()
    val n = arr.length
    var sorted = false
    val numThreads = Runtime.getRuntime.availableProcessors()
    val chunkSize = Math.max(1, n / numThreads)

    while (!sorted) {
      sorted = true
      
      if (executePhase(arr, 1, n, chunkSize)) sorted = false
      
      if (executePhase(arr, 0, n, chunkSize)) sorted = false
    }
    arr
  }

  private def executePhase(arr: Array[Int], startIdx: Int, n: Int, chunkSize: Int): Boolean = {
    val futures = for (i <- startIdx until n - 1 by chunkSize) yield Future {
      var changed = false
      val end = Math.min(i + chunkSize, n - 1)
      for (j <- i until end by 2) {
        if (arr(j) > arr(j + 1)) {
          val temp = arr(j)
          arr(j) = arr(j + 1)
          arr(j + 1) = temp
          changed = true
        }
      }
      changed
    }
    
    val results = Await.result(Future.sequence(futures), Duration.Inf)
    results.exists(identity)
  }
}