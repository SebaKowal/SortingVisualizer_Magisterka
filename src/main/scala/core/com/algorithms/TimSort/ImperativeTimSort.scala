package core.com.algorithms.TimSort

import core.Sorter

class ImperativeTimSort extends Sorter {
  override val name: String = "TimSort"
  override val paradigm: String = "Imperative"
  private val RUN = 32

  override def sort(data: Array[Int]): Array[Int] = {
    val arr = data.clone()
    val n = arr.length

    // 1. Sortowanie małych fragmentów (RUNs)
    for (i <- 0 until n by RUN) {
      val end = Math.min(i + RUN - 1, n - 1)
      insertionSort(arr, i, end)
    }

    // 2. Scalanie fragmentów (Merge)
    var size = RUN
    while (size < n) {
      for (left <- 0 until n by 2 * size) {
        val mid = left + size - 1
        val right = Math.min(left + 2 * size - 1, n - 1)

        if (mid < right) {
          merge(arr, left, mid, right)
        }
      }
      size *= 2
    }
    arr
  }

  // Poprawiona pętla insertionSort - styl czysto imperatywny
  private def insertionSort(arr: Array[Int], left: Int, right: Int): Unit = {
    for (i <- (left + 1) to right) {
      val temp = arr(i)
      var j = i - 1
      while (j >= left && arr(j) > temp) {
        arr(j + 1) = arr(j)
        j -= 1
      }
      arr(j + 1) = temp
    }
  }

  private def merge(arr: Array[Int], l: Int, m: Int, r: Int): Unit = {
    val len1 = m - l + 1
    val len2 = r - m
    val left = new Array[Int](len1)
    val right = new Array[Int](len2)

    // Kopiowanie danych do tablic pomocniczych
    var x = 0
    while (x < len1) {
      left(x) = arr(l + x)
      x += 1
    }
    var y = 0
    while (y < len2) {
      right(y) = arr(m + 1 + y)
      y += 1
    }

    var i = 0
    var j = 0
    var k = l

    while (i < len1 && j < len2) {
      if (left(i) <= right(j)) {
        arr(k) = left(i)
        i += 1
      } else {
        arr(k) = right(j)
        j += 1
      }
      k += 1
    }

    while (i < len1) {
      arr(k) = left(i)
      i += 1
      k += 1
    }

    while (j < len2) {
      arr(k) = right(j)
      j += 1
      k += 1
    }
  }
}