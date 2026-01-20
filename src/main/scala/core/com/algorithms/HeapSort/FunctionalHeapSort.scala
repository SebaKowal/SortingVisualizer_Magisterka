package core.com.algorithms.HeapSort

import core.Sorter
import scala.annotation.tailrec

sealed trait Heap
case object Empty extends Heap
case class Node(value: Int, left: Heap, right: Heap) extends Heap

class FunctionalHeapSort extends Sorter {
  override val name: String = "HeapSort"
  override val paradigm: String = "Functional"

  override def sort(data: Array[Int]): Array[Int] = {
    // 1. Budujemy kopiec przez rekurencyjne wstawianie elementów
    val heap = data.foldLeft(Empty: Heap)((h, v) => insert(v, h))

    // 2. Wyciągamy elementy z kopca (zawsze korzeń) do listy i zamieniamy na tablicę
    extractAll(heap).toArray
  }

  // Funkcyjne wstawianie (zawsze zachowuje strukturę kopca)
  private def insert(v: Int, heap: Heap): Heap = heap match {
    case Empty => Node(v, Empty, Empty)
    case Node(root, l, r) =>
      // W paradygmacie funkcyjnym często zamieniamy poddrzewa miejscami,
      // aby utrzymać drzewo w miarę zrównoważone
      if (v > root) Node(v, insert(root, r), l)
      else Node(root, insert(v, r), l)
  }

  // Rekurencyjne wyciąganie maksimum (korzenia)
  private def extractAll(heap: Heap): List[Int] = {
    @tailrec
    def loop(h: Heap, acc: List[Int]): List[Int] = h match {
      case Empty => acc
      case Node(v, l, r) => loop(merge(l, r), v :: acc)
    }
    loop(heap, Nil).reverse
  }

  // Funkcyjne łączenie dwóch drzew (zastępuje 'sift' z wersji imperatywnej)
  private def merge(h1: Heap, h2: Heap): Heap = (h1, h2) match {
    case (Empty, h) => h
    case (h, Empty) => h
    case (Node(v1, l1, r1), Node(v2, l2, r2)) =>
      if (v1 > v2) Node(v1, merge(l1, r1), h2)
      else Node(v2, h1, merge(l2, r2))
  }
}