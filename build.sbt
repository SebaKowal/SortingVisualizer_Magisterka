ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.6"

lazy val root = (project in file("."))
  .settings(
    name := "SortingVisualizer_Magisterka",
    libraryDependencies ++= Seq(
      "org.scalafx" %% "scalafx" % "24.0.2-R36"
    )
  )
