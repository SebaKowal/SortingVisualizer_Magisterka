package core.com.algorithms.SelectionSort

import core.Sorter

class ImperativeSelectionSort extends Sorter {
  override val name: String = "SelectionSort"
  override val paradigm: String = "Imperative"

  override def sort(data: Array[Int]): Array[Int] = {
    val arr = data.clone()
    val n = arr.length


    for (i <- 0 until n - 1) {
      var minIdx = i

      for (j <- i + 1 until n) {
        if (arr(j) < arr(minIdx)) {
          minIdx = j
        }
      }

      if (minIdx != i) {
        val temp = arr(i)
        arr(i) = arr(minIdx)
        arr(minIdx) = temp
      }
    }
    arr
  }
}