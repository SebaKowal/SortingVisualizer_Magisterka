package core.com.algorithms.BubbleSort

import core.Sorter
import scala.annotation.tailrec

class FunctionalBubbleSort extends Sorter {
  override val name: String = "BubbleSort"
  override val paradigm: String = "Functional"

  override def sort(data: Array[Int]): Array[Int] = {
    val list = data.toList

    @tailrec
    def bubbleLoop(l: List[Int], iterations: Int): List[Int] = {
      if (iterations == 0) l
      else {
        val (nextList, swapped) = bubblePass(l)
        if (!swapped) nextList
        else bubbleLoop(nextList, iterations - 1)
      }
    }

    def bubblePass(l: List[Int]): (List[Int], Boolean) = {
      l match {
        case h1 :: h2 :: tail =>
          if (h1 > h2) {
            val (subList, _) = bubblePass(h1 :: tail)
            (h2 :: subList, true)
          } else {
            val (subList, swapped) = bubblePass(h2 :: tail)
            (h1 :: subList, swapped)
          }
        case _ => (l, false)
      }
    }

    bubbleLoop(list, list.length).toArray
  }
}