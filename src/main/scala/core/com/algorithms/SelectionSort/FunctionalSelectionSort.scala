package core.com.algorithms.SelectionSort

import core.Sorter
import scala.annotation.tailrec

class FunctionalSelectionSort extends Sorter {
  override val name: String = "SelectionSort"
  override val paradigm: String = "Functional"

  override def sort(data: Array[Int]): Array[Int] = {
    val list = data.toList

    @tailrec
    def selectLoop(remaining: List[Int], acc: List[Int]): List[Int] = {
      remaining match {
        case Nil => acc
        case _ =>
          // Znajdujemy minimum w obecnej liście
          val minVal = remaining.min
          // Usuwamy TYLKO JEDNO wystąpienie minVal z listy
          val (before, after) = remaining.span(_ != minVal)
          val newRemaining = before ::: after.tail
          // Idziemy dalej z mniejszą listą, dodając minimum do akumulatora
          selectLoop(newRemaining, acc :+ minVal)
      }
    }

    selectLoop(list, Nil).toArray
  }
}