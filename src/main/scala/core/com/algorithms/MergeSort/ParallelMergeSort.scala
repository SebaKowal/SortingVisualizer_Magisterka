package core.com.algorithms.MergeSort

import core.Sorter
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class ParallelMergeSort extends Sorter {
  override val name: String = "MergeSort"
  override val paradigm: String = "Parallel"

  override def sort(data: Array[Int]): Array[Int] = {
    val arr = data.clone()
    if (arr.length <= 1) return arr

    Await.result(parallelMs(arr, 0, arr.length - 1), Duration.Inf)
  }

  private def parallelMs(array: Array[Int], low: Int, high: Int): Future[Array[Int]] = {
    if (low < high) {
      val mid = low + (high - low) / 2
      val leftFuture = Future {
        msSequential(array, low, mid)
      }
      val rightFuture = Future {
        msSequential(array, mid + 1, high)
      }

      // Czekamy na obie strony i scalamy
      for {
        _ <- leftFuture
        _ <- rightFuture
      } yield {
        merge(array, low, mid, high)
        array
      }
    } else {
      Future.successful(array)
    }
  }

  // Klasyczny sekwencyjny Merge Sort dla gałęzi drzewa
  private def msSequential(array: Array[Int], low: Int, high: Int): Unit = {
    if (low < high) {
      val mid = low + (high - low) / 2
      msSequential(array, low, mid)
      msSequential(array, mid + 1, high)
      merge(array, low, mid, high)
    }
  }

  private def merge(array: Array[Int], low: Int, mid: Int, high: Int): Unit = {
    val left = array.slice(low, mid + 1)
    val right = array.slice(mid + 1, high + 1)

    var i = 0
    var j = 0
    var k = low

    while (k <= high) {
      if (i < left.length && (j >= right.length || left(i) <= right(j))) {
        array(k) = left(i)
        i += 1
      } else {
        array(k) = right(j)
        j += 1
      }
      k += 1
    }
  }
}