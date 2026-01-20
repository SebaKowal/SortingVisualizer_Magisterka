package engine

import core.Sorter
import core.data.{DataGenerator, FullReportRow}
import scala.collection.mutable.ListBuffer

object BenchmarkRunner {

  def runFullSuite(sorter: Sorter): List[FullReportRow] = {
    val results = new ListBuffer[FullReportRow]()
    val sizes = Array(1000, 5000, 10000)

    for (size <- sizes) {
      // Dodajemy .toList na wszelki wypadek przy każdym dodawaniu, 
      // ale najważniejszy jest return na samym dole
      results += MetricsCollector.measure(sorter, DataGenerator.randomArray(size), "Random")
      results += MetricsCollector.measure(sorter, DataGenerator.sortedArray(size), "Sorted")
      results += MetricsCollector.measure(sorter, DataGenerator.reverseSortedArray(size), "Reverse")
      results += MetricsCollector.measure(sorter, DataGenerator.repeatedArray(size), "Repeated")
    }

    // TO JEST KLUCZOWE:
    results.toList
  }
}