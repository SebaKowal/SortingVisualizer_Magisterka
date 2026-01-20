package core.data

import scala.util.Random

object DataGenerator {
  // 1. Dane całkowicie losowe
  def randomArray(size: Int): Array[Int] = {
    Array.fill(size)(Random.nextInt(size * 10))
  }

  // 2. Dane już posortowane (optymistyczne dla niektórych, zabójcze dla innych)
  def sortedArray(size: Int): Array[Int] = {
    Array.range(0, size)
  }

  // 3. Dane posortowane odwrotnie (najgorszy przypadek dla wielu algorytmów)
  def reverseSortedArray(size: Int): Array[Int] = {
    Array.range(0, size).reverse
  }

  // 4. Dane z wieloma duplikatami (tzw. "płaska" tablica)
  def repeatedArray(size: Int): Array[Int] = {
    Array.fill(size)(Random.nextInt(5)) // Tylko wartości 0-4
  }
}