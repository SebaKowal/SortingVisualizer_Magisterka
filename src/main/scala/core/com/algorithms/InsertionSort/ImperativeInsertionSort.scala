package core.com.algorithms.InsertionSort

import core.Sorter

class ImperativeInsertionSort extends Sorter {
  override val name: String = "InsertionSort"
  override val paradigm: String = "Imperative"

  override def sort(data: Array[Int]): Array[Int] = {
    val arr = data.clone()
    for (i <- 1 until arr.length) {
      val key = arr(i)
      var j = i - 1
      while (j >= 0 && arr(j) > key) {
        arr(j + 1) = arr(j)
        j -= 1
      }
      arr(j + 1) = key
    }
    arr
  }
}