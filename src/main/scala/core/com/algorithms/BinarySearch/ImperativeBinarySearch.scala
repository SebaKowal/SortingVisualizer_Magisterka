package core.com.algorithms.BinarySearch

class ImperativeBinarySearch {
  val name: String = "BinarySearch"
  val paradigm: String = "Imperative"

  def search(data: Array[Int], target: Int): Int = {
    var low = 0
    var high = data.length - 1

    while (low <= high) {
      val mid = low + (high - low) / 2
      if (data(mid) == target) {
        return mid
      } else if (data(mid) < target) {
        low = mid + 1
      } else {
        high = mid - 1
      }
    }
    -1
  }
}