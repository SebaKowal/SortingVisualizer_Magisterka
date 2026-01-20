package `export`

import core.data.FullReportRow
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileOutputStream

object ExcelExporter {

  def exportResults(results: List[FullReportRow], fileName: String): Unit = {
    val workbook = new XSSFWorkbook()
    val sheet = workbook.createSheet("Wyniki Analizy")

    // 1. Nagłówki - teraz masz ich znacznie więcej do magisterki
    val header = sheet.createRow(0)
    val columns = Seq(
      "Algorytm", "Paradygmat", "Rozmiar", "Typ danych",
      "Porównania", "Zamiany", "Czas (ms)", "RAM (MB)",
      "Cykle GC", "Czas GC (ms)", "Wątki", "OS", "Rdzenie CPU"
    )

    columns.zipWithIndex.foreach { case (name, i) =>
      header.createCell(i).setCellValue(name)
    }

    // 2. Wypełnianie danymi
    results.zipWithIndex.foreach { case (res, i) =>
      val row = sheet.createRow(i + 1)
      row.createCell(0).setCellValue(res.algoName)
      row.createCell(1).setCellValue(res.paradigm)
      row.createCell(2).setCellValue(res.dataSize.toDouble)
      row.createCell(3).setCellValue(res.dataType)
      row.createCell(4).setCellValue(res.comparisons.toDouble)
      row.createCell(5).setCellValue(res.swaps.toDouble)
      row.createCell(6).setCellValue(res.timeNs.toDouble / 1000000.0) // Konwersja na ms
      row.createCell(7).setCellValue(res.memoryUsedMB)
      row.createCell(8).setCellValue(res.gcCycles.toDouble)
      row.createCell(9).setCellValue(res.gcTimeMs.toDouble)
      row.createCell(10).setCellValue(res.threadCount.toDouble)
      row.createCell(11).setCellValue(res.osName)
      row.createCell(12).setCellValue(res.cpuCores.toDouble)
    }

    // Auto-size kolumn, żeby ładnie wyglądało
    (0 to 12).foreach(sheet.autoSizeColumn)

    // 3. Zapis do pliku
    val out = new FileOutputStream(fileName)
    workbook.write(out)
    out.close()
    workbook.close()
  }
}