package core.com.algorithms.HeapSort

import core.Sorter
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class ParallelHeapSort extends Sorter {
  override val name: String = "HeapSort"
  override val paradigm: String = "Parallel"

  override def sort(data: Array[Int]): Array[Int] = {
    val sortedArray = data.clone()
    val count = sortedArray.length
    if (count <= 1) return sortedArray

    def sift(start: Int, endLimit: Int): Unit = {
      var root = start
      while (root * 2 + 1 < endLimit) {
        var child = root * 2 + 1
        if (child < endLimit - 1 && sortedArray(child) < sortedArray(child + 1)) {
          child += 1
        }
        if (sortedArray(root) < sortedArray(child)) {
          val t = sortedArray(root)
          sortedArray(root) = sortedArray(child)
          sortedArray(child) = t
          root = child
        } else return
      }
    }

    val mid = count / 2 - 1
    val numThreads = Runtime.getRuntime.availableProcessors()
    val step = if (mid > 0) Math.ceil(mid.toDouble / numThreads).toInt else 1
    val chunks = (0 until numThreads).map { i =>
      val from = mid - (i * step)
      val untilLimit = Math.max(-1, mid - ((i + 1) * step))

      Future {
        if (from >= 0) {
          for (j <- from to (untilLimit + 1) by -1) {
            sift(j, count)
          }
        }
      }
    }

    Await.result(Future.sequence(chunks), Duration.Inf)

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