package core.com.algorithms.TimSort

import core.Sorter
import core.com.algorithms.InsertionSort.FunctionalInsertionSort
import scala.annotation.tailrec

class FunctionalTimSort extends Sorter {
  override val name: String = "TimSort"
  override val paradigm: String = "Functional"
  private val RUN = 32

  override def sort(data: Array[Int]): Array[Int] = {
    val list = data.toList
    val insSorter = new FunctionalInsertionSort()

    // 1. Dzielimy listę na RUN-y i każdy sortujemy funkcyjnie
    val runs = list.grouped(RUN).map(r => insSorter.sort(r.toArray).toList).toList

    // 2. Rekurencyjne scalanie list (Merge)
    @tailrec
    def mergeAll(activeRuns: List[List[Int]]): List[Int] = activeRuns match {
      case Nil => Nil
      case head :: Nil => head
      case _ =>
        val mergedPairs = activeRuns.grouped(2).map {
          case List(a, b) => merge(a, b)
          case List(a) => a
        }.toList
        mergeAll(mergedPairs)
    }

    def merge(l: List[Int], r: List[Int]): List[Int] = (l, r) match {
      case (Nil, _) => r
      case (_, Nil) => l
      case (lh :: lt, rh :: rt) =>
        if (lh <= rh) lh :: merge(lt, r) else rh :: merge(l, rt)
    }

    mergeAll(runs).toArray
  }
}