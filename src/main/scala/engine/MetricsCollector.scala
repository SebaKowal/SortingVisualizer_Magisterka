package engine

import java.lang.management.ManagementFactory
import scala.jdk.CollectionConverters._
import core.Sorter
import core.data.FullReportRow

object MetricsCollector {
  // Dodajemy dataType jako trzeci parametr, żeby Main.scala nie sypał błędami
  def measure(sorter: core.Sorter, data: Array[Int], dataType: String = "Random"): FullReportRow = {
    val gcBeans = ManagementFactory.getGarbageCollectorMXBeans.asScala
    val runtime = Runtime.getRuntime

    // 1. Pomiary przed
    System.gc()
    Thread.sleep(50)
    val startGcCount = gcBeans.map(_.getCollectionCount).sum
    val startGcTime = gcBeans.map(_.getCollectionTime).sum
    val memBefore = runtime.totalMemory() - runtime.freeMemory()
    val startTime = System.nanoTime()

    // 2. WYKONANIE - JEDEN nawias, bez acc i bez onUpdate!
    // To naprawia błąd "Required: Int"
    sorter.sort(data.clone())

    // 3. Pomiary po
    val endTime = System.nanoTime()
    val endGcCount = gcBeans.map(_.getCollectionCount).sum
    val endGcTime = gcBeans.map(_.getCollectionTime).sum
    val memAfter = runtime.totalMemory() - runtime.freeMemory()

    // 4. Zwracasz FullReportRow
    FullReportRow(
      algoName     = sorter.name,
      paradigm     = sorter.paradigm,
      dataSize     = data.length,
      dataType     = dataType,
      comparisons  = 0, // Twoje algorytmy nie obsługują licznika, więc zwracamy 0
      swaps        = 0,
      timeNs       = endTime - startTime,
      memoryUsedMB = Math.max(0.0, (memAfter - memBefore).toDouble / (1024 * 1024)),
      gcCycles     = endGcCount - startGcCount,
      gcTimeMs     = endGcTime - startGcTime,
      threadCount  = ManagementFactory.getThreadMXBean.getThreadCount,
      osName       = System.getProperty("os.name"),
      cpuCores     = runtime.availableProcessors(),
      jvmVersion   = System.getProperty("java.version")
    )
  }
}