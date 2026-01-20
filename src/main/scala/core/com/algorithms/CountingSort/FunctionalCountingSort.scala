package core.com.algorithms.CountingSort

import core.Sorter

class FunctionalCountingSort extends Sorter {
  override val name: String = "CountingSort"
  override val paradigm: String = "Functional"

  override def sort(data: Array[Int]): Array[Int] = {
    if (data.isEmpty) return data

    val min = data.min
    val max = data.max

    // 1. Grupowanie elementów w Mapę (wartość -> lista wystąpień)
    val counts = data.toList.groupBy(identity)

    // 2. Generowanie posortowanej sekwencji na podstawie zakresu min-max
    val sortedList = (min to max).flatMap { value =>
      counts.getOrElse(value, Nil)
    }

    sortedList.toArray
  }
}