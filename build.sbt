ThisBuild / scalaVersion := "3.3.1"

lazy val root = (project in file("."))
  .settings(
    name := "SortingVisualizer",
    libraryDependencies ++= Seq(
      "org.scalafx"   %% "scalafx"   % "21.0.0-R32",
      "org.apache.poi" % "poi-ooxml" % "5.2.3",
      "com.typesafe"   % "config"    % "1.4.2",

      // DODANE: Refleksja do automatycznego znajdowania algorytmów
      "org.reflections" % "reflections" % "0.10.2",

      // DODANE: Logger, żeby wyciszyć błędy log4j/slf4j w konsoli
      "org.slf4j" % "slf4j-simple" % "2.0.7"
    ),

    // Wymuszamy biblioteki JavaFX 21 specjalnie pod Windows
    libraryDependencies ++= Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
      .map(m => "org.openjfx" % s"javafx-$m" % "21" classifier "win")
  )
  .enablePlugins(JavaAppPackaging, WindowsPlugin)