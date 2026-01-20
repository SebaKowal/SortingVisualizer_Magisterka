package core.com.algorithms.MergeSort

import core.Sorter

class ImperativeMergeSort extends Sorter {
  override val name: String = "MergeSort"
  override val paradigm: String = "Imperative"

  override def sort(data: Array[Int]): Array[Int] = {
    // Klonujemy, aby pracować na kopii i nie psuć danych wejściowych dla innych testów
    val arr = data.clone()

    // Rozpoczynamy główny proces sortowania
    ms(arr, 0, arr.length - 1)
    arr
  }

  // Metoda dzieląca tablicę (rekurencyjna, ale operująca na mutowalnej strukturze)
  private def ms(array: Array[Int], low: Int, high: Int): Unit = {
    if (low < high) {
      val mid = low + (high - low) / 2
      ms(array, low, mid)
      ms(array, mid + 1, high)
      merge(array, low, mid, high)
    }
  }

  // Serce algorytmu imperatywnego - scalanie w miejscu przy użyciu pomocniczych kopii
  private def merge(array: Array[Int], low: Int, mid: Int, high: Int): Unit = {
    // Tworzenie kopii podtablic - to jest narzut pamięciowy typowy dla Merge Sort
    val left = array.slice(low, mid + 1)
    val right = array.slice(mid + 1, high + 1)

    var i = 0 // Indeks dla lewej kopii
    var j = 0 // Indeks dla prawej kopii
    var k = low // Indeks dla tablicy głównej

    while (k <= high) {
      if (i < left.length && (j >= right.length || left(i) <= right(j))) {
        array(k) = left(i)
        i += 1
      } else {
        array(k) = right(j)
        j += 1
      }
      k += 1
    }
  }
}