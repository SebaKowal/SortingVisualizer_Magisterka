import scalafx.application.JFXApp3
import scalafx.scene.Scene
import gui.MainLayout

object Main extends JFXApp3 {
  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      title = "Magisterka: Analiza Algorytmów"
      scene = new Scene(600, 450) {
        // Ładujemy layout z oddzielnej klasy
        root = new MainLayout().view
      }
    }
  }
}