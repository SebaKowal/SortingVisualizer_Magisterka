package core.com.algorithms.QuickSort

import core.Sorter

class FunctionalQuickSort extends Sorter {
  override val name: String = "QuickSort"
  override val paradigm: String = "Functional"

  override def sort(data: Array[Int]): Array[Int] = {
    def quickSort(list: List[Int]): List[Int] = list match {
      case Nil => Nil
      case pivot :: tail =>
        val (less, greater) = tail.partition(_ < pivot)
        quickSort(less) ::: pivot :: quickSort(greater)
    }

    // Konwersja dla zachowania spójności interfejsu
    quickSort(data.toList).toArray
  }
}