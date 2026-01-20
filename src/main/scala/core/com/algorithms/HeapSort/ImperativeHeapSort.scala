package core.com.algorithms.HeapSort

import core.Sorter

class ImperativeHeapSort extends Sorter {
  override val name: String = "HeapSort"
  override val paradigm: String = "Imperative"

  override def sort(data: Array[Int]): Array[Int] = {
    val sortedArray = data.clone()
    val count = sortedArray.length

    def sift(start: Int, count: Int): Unit = {
      var root = start

      while (root * 2 + 1 < count) {
        var child = root * 2 + 1 // Lewy syn

        if (child < count - 1 && sortedArray(child) < sortedArray(child + 1)) {
          child += 1
        }

        if (sortedArray(root) < sortedArray(child)) {
          val t = sortedArray(root)
          sortedArray(root) = sortedArray(child)
          sortedArray(child) = t
          root = child // Kontynuuj przesiewanie w dół
        } else return // Kopiec naprawiony
      }
    }

    var start = count / 2 - 1
    while (start >= 0) {
      sift(start, count)
      start -= 1
    }

    var end = count - 1
    while (end > 0) {
      val t = sortedArray(end)
      sortedArray(end) = sortedArray(0)
      sortedArray(0) = t
      sift(0, end)
      end -= 1
    }

    sortedArray
  }
}