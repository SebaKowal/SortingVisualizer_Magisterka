package core.com.algorithms.BinarySearch

import scala.annotation.tailrec

class FunctionalBinarySearch {
  val name: String = "BinarySearch"
  val paradigm: String = "Functional"

  def search(data: Array[Int], target: Int): Int = {
    @tailrec
    def recursiveSearch(low: Int, high: Int): Int = {
      if (low > high) -1
      else {
        val mid = low + (high - low) / 2
        data(mid) match {
          case v if v == target => mid
          case v if v < target  => recursiveSearch(mid + 1, high)
          case _                => recursiveSearch(low, mid - 1)
        }
      }
    }

    recursiveSearch(0, data.length - 1)
  }
}