package core.com.algorithms.CountingSort

import core.Sorter

class ImperativeCountingSort extends Sorter {
  override val name: String = "CountingSort"
  override val paradigm: String = "Imperative"

  override def sort(data: Array[Int]): Array[Int] = {
    if (data.length == 0) return data
    val arr = data.clone()

    // 1. Znalezienie zakresu (min i max)
    var min = arr(0)
    var max = arr(0)
    for (i <- 1 until arr.length) {
      if (arr(i) < min) min = arr(i)
      if (arr(i) > max) max = arr(i)
    }

    val range = max - min + 1
    val count = new Array[Int](range)
    val output = new Array[Int](arr.length)

    // 2. Zliczanie wystąpień
    for (i <- 0 until arr.length) {
      count(arr(i) - min) += 1
    }

    // 3. Modyfikacja tablicy count (kumulacja indeksów)
    for (i <- 1 until count.length) {
      count(i) += count(i - 1)
    }

    // 4. Budowanie tablicy wyjściowej (od tyłu dla stabilności)
    var j = arr.length - 1
    while (j >= 0) {
      output(count(arr(j) - min) - 1) = arr(j)
      count(arr(j) - min) -= 1
      j -= 1
    }

    output
  }
}