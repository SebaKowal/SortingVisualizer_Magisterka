package core.com.algorithms.MergeSort

import core.Sorter
import scala.annotation.tailrec

class FunctionalMergeSort extends Sorter {
  override val name: String = "MergeSort"
  override val paradigm: String = "Functional"

  override def sort(data: Array[Int]): Array[Int] = {
    // Konwersja na listę - w FP pracujemy na strukturach niemutowalnych
    val resultList = mergeSortList(data.toList)
    resultList.toArray
  }

  private def mergeSortList(list: List[Int]): List[Int] = {
    val n = list.length / 2
    if (n == 0) list // Lista ma 0 lub 1 element - jest już posortowana
    else {
      // splitAt to funkcyjne dzielenie listy na dwie podlisty
      val (left, right) = list.splitAt(n)
      merge(mergeSortList(left), mergeSortList(right))
    }
  }

  // Funkcyjne scalanie dwóch posortowanych list
  private def merge(left: List[Int], right: List[Int]): List[Int] = {
    (left, right) match {
      case (Nil, _) => right
      case (_, Nil) => left
      case (lHead :: lTail, rHead :: rTail) =>
        if (lHead <= rHead) lHead :: merge(lTail, right)
        else rHead :: merge(left, rTail)
    }
  }
}