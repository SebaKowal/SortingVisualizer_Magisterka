package gui

import core.com.algorithms.AlgorithmRegistry
import engine.BenchmarkRunner
import `export`.ExcelExporter

object GuiController {
  var allResults: List[core.data.FullReportRow] = List()

  def handleRun(algoName: String, mode: String, customSize: String = ""): Unit = {
    AlgorithmRegistry.all.get(algoName).foreach { sorter =>
      mode match {
        case "FULL" =>
          allResults = allResults ++ BenchmarkRunner.runFullSuite(sorter)
        case "QUICK" =>
          val data = core.data.DataGenerator.randomArray(10000)
          allResults = allResults :+ engine.MetricsCollector.measure(sorter, data, "Quick_10k")
        case "CUSTOM" =>
          val size = customSize.toIntOption.getOrElse(1000)
          val data = core.data.DataGenerator.randomArray(size)
          allResults = allResults :+ engine.MetricsCollector.measure(sorter, data, s"Custom_$size")
      }
    }
  }

  def handleExport(): Unit = {
    if (allResults.nonEmpty) {
      ExcelExporter.exportResults(allResults, s"raport_${System.currentTimeMillis()}.xlsx")
    }
  }
}