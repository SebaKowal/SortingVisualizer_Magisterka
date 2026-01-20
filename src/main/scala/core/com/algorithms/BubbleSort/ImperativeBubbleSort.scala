package core.com.algorithms.BubbleSort

import core.Sorter

class ImperativeBubbleSort extends Sorter {
  override val name: String = "BubbleSort"
  override val paradigm: String = "Imperative"

  override def sort(data: Array[Int]): Array[Int] = {
    val arr = data.clone()
    val n = arr.length
    var swapped = true
    var i = 0
    
    while (i < n - 1 && swapped) {
      swapped = false
      for (j <- 0 until n - i - 1) {
        if (arr(j) > arr(j + 1)) {
          // Zamiana (Swap)
          val temp = arr(j)
          arr(j) = arr(j + 1)
          arr(j + 1) = temp
          swapped = true
        }
      }
      i += 1
    }
    arr
  }
}