package core.com.algorithms.TimSort

import core.Sorter
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class ParallelTimSort extends Sorter {
  override val name: String = "TimSort"
  override val paradigm: String = "Parallel"
  private val RUN = 32

  override def sort(data: Array[Int]): Array[Int] = {
    if (data.length <= RUN) return java.util.Arrays.copyOf(data, data.length).sorted

    val n = data.length

    // 1. Równoległe sortowanie RUNów
    val initialRunsFutures = (0 until n by RUN).map { start =>
      Future {
        val end = Math.min(start + RUN, n)
        val chunk = data.slice(start, end)
        java.util.Arrays.sort(chunk) // Używamy natywnego sortowania dla wydajności runu
        chunk
      }
    }

    val sortedRuns = Await.result(Future.sequence(initialRunsFutures), Duration.Inf).toList

    // 2. Równoległe scalanie par (Merge Tree)
    def parallelMerge(runs: List[Array[Int]]): Future[Array[Int]] = runs match {
      case Nil => Future.successful(Array.empty[Int])
      case single :: Nil => Future.successful(single)
      case list =>
        val pairs = list.grouped(2).map {
          case a :: b :: Nil => Future { mergeArrays(a, b) }
          case last :: Nil => Future.successful(last)
          case _ => Future.successful(Array.empty[Int])
        }.toList

        Future.sequence(pairs).flatMap(nextLevel => parallelMerge(nextLevel))
    }

    Await.result(parallelMerge(sortedRuns), Duration.Inf)
  }

  private def mergeArrays(left: Array[Int], right: Array[Int]): Array[Int] = {
    val res = new Array[Int](left.length + right.length)
    var i, j, k = 0
    while (i < left.length && j < right.length) {
      if (left(i) <= right(j)) { res(k) = left(i); i += 1 }
      else { res(k) = right(j); j += 1 }
      k += 1
    }
    while (i < left.length) { res(k) = left(i); i += 1; k += 1 }
    while (j < right.length) { res(k) = right(j); j += 1; k += 1 }
    res
  }
}