package core.com.algorithms

import core.Sorter
import org.reflections.Reflections
import scala.jdk.CollectionConverters._

object AlgorithmRegistry {
  // Skanuje Twoją strukturę pakietów widoczną na zdjęciu
  private val reflections = new Reflections("core.com.algorithms")

  // Automatycznie znajduje wszystkie klasy implementujące Sorter
  val all: Map[String, Sorter] = {
    val subTypes = reflections.getSubTypesOf(classOf[Sorter]).asScala
    subTypes.flatMap { clazz =>
      try {
        // Tworzy instancję (np. new FunctionalQuickSort())
        val instance = clazz.getDeclaredConstructor().newInstance()
        Some(instance.name -> instance)
      } catch {
        case e: Exception =>
          println(s"Pominięto ${clazz.getSimpleName}: wymaga bezargumentowego konstruktora")
          None
      }
    }.toMap
  }

  def getNames: Seq[String] = all.keys.toSeq.sorted
}