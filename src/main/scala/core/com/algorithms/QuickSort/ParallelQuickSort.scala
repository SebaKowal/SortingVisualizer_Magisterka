package core.com.algorithms.QuickSort

import core.Sorter
import scala.concurrent.{Await, Future, ExecutionContext}
import scala.concurrent.duration._

class ParallelQuickSort(implicit ec: ExecutionContext) extends Sorter {
  override val name: String = "QuickSort"
  override val paradigm: String = "Parallel"

  override def sort(data: Array[Int]): Array[Int] = {
    def quickSort(list: List[Int]): Future[List[Int]] = list match {
      case Nil => Future.successful(Nil)
      case pivot :: tail =>
        val (less, greater) = tail.partition(_ < pivot)

        // Uruchamiamy sortowanie podlist w osobnych wątkach
        val leftFuture = quickSort(less)
        val rightFuture = quickSort(greater)

        for {
          left <- leftFuture
          right <- rightFuture
        } yield left ::: pivot :: right
    }

    // Czekamy na wynik (w GUI będziesz to robił asynchronicznie)
    val resultList = Await.result(quickSort(data.toList), 10.seconds)
    resultList.toArray
  }
}