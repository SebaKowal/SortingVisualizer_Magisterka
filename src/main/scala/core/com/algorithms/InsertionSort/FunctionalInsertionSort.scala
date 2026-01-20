package core.com.algorithms.InsertionSort

import core.Sorter
import scala.annotation.tailrec

class FunctionalInsertionSort extends Sorter {
  override val name: String = "InsertionSort"
  override val paradigm: String = "Functional"

  override def sort(data: Array[Int]): Array[Int] = {
    val list = data.toList

    // Funkcja pomocnicza: wstawia element 'x' do JUŻ POSORTOWANEJ listy
    def insert(x: Int, sortedList: List[Int]): List[Int] = sortedList match {
      case Nil => List(x)
      case head :: tail =>
        if (x <= head) x :: sortedList
        else head :: insert(x, tail)
    }

    // Główna pętla rekurencyjna: przechodzi przez nieposortowane dane
    @tailrec
    def sortRecursive(remaining: List[Int], acc: List[Int]): List[Int] = {
      remaining match {
        case Nil => acc
        case head :: tail =>
          sortRecursive(tail, insert(head, acc))
      }
    }

    sortRecursive(list, Nil).toArray
  }
}