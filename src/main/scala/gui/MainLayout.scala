package gui

import core.com.algorithms.AlgorithmRegistry
import scalafx.scene.layout._
import scalafx.scene.control._
import scalafx.geometry.Insets

class MainLayout {
  val view = new BorderPane()

  // --- NAVBAR ---
  private val navbar = new HBox(10) {
    padding = Insets(10)
    style = "-fx-background-color: #2c3e50;"
    children = Seq(
      new Button("💾 Eksportuj") { onAction = _ => GuiController.handleExport() },
      new Button("🗑 Wyczyść") { onAction = _ => GuiController.allResults = List() }
    )
  }

  // --- CONTENT ---
  private val content = new VBox(15) {
    padding = Insets(20)

    val algoCombo: ComboBox[String] = new ComboBox(AlgorithmRegistry.getNames) {
      value = AlgorithmRegistry.getNames.headOption.getOrElse("")
      maxWidth = Double.MaxValue
    }

    val customSizeField: TextField = new TextField { promptText = "Rozmiar (np. 50000)" }

    children = Seq(
      new Label("1. Wybierz Algorytm:"),
      algoCombo,
      new Separator(),
      new Button("▶ Pakiet Pełny (Magisterka)") {
        maxWidth = Double.MaxValue
        onAction = _ => GuiController.handleRun(algoCombo.value.value, "FULL")
      },
      new Separator(),
      new Label("2. Test Niestandardowy:"),
      customSizeField,
      new Button("⚡ Uruchom Niestandardowy") {
        maxWidth = Double.MaxValue
        onAction = _ => GuiController.handleRun(algoCombo.value.value, "CUSTOM", customSizeField.text.value)
      }
    )
  }

  view.top = navbar
  view.center = content
}