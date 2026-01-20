package core.com.algorithms.CountingSort

import core.Sorter
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class ParallelCountingSort extends Sorter {
  override val name: String = "CountingSort"
  override val paradigm: String = "Parallel"

  override def sort(data: Array[Int]): Array[Int] = {
    if (data.isEmpty) return data

    val min = data.min
    val max = data.max
    val range = max - min + 1
    val numThreads = Runtime.getRuntime.availableProcessors()
    val chunkSize = Math.max(1, data.length / numThreads)

    // 1. Równoległe zliczanie w fragmentach
    val futures = (0 until numThreads).map { t =>
      Future {
        val start = t * chunkSize
        val end = if (t == numThreads - 1) data.length else (t + 1) * chunkSize
        val localCount = new Array[Int](range)
        for (i <- start until end) {
          localCount(data(i) - min) += 1
        }
        localCount
      }
    }

    // 2. Agregacja (sumowanie) tablic liczników
    val partialCounts = Await.result(Future.sequence(futures), Duration.Inf)
    val totalCount = new Array[Int](range)

    for (pc <- partialCounts; i <- 0 until range) {
      totalCount(i) += pc(i)
    }

    // 3. Budowanie wyniku (sekwencyjne odtwarzanie tablicy)
    val output = new Array[Int](data.length)
    var cursor = 0
    for (valueIndex <- 0 until range) {
      val actualValue = valueIndex + min
      val frequency = totalCount(valueIndex)
      for (_ <- 0 until frequency) {
        output(cursor) = actualValue
        cursor += 1
      }
    }
    output
  }
}