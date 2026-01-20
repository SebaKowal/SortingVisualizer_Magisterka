package core.com.algorithms.QuickSort

import core.Sorter

class ImperativeQuickSort extends Sorter {
  override val name: String = "QuickSort"
  override val paradigm: String = "Imperative"

  override def sort(data: Array[Int]): Array[Int] = {
    val result = data.clone() // Kopiujemy, żeby nie niszczyć danych wejściowych

    def swap(i: Int, j: Int): Unit = {
      val temp = result(i)
      result(i) = result(j)
      result(j) = temp
    }

    def quickSort(low: Int, high: Int): Unit = {
      if (low < high) {
        val pivot = result(high)
        var i = low - 1
        for (j <- low until high) {
          if (result(j) <= pivot) {
            i += 1
            swap(i, j)
          }
        }
        swap(i + 1, high)
        val p = i + 1

        quickSort(low, p - 1)
        quickSort(p + 1, high)
      }
    }

    quickSort(0, result.length - 1)
    result
  }
}