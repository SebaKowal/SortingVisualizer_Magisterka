package core

trait Sorter {
  def name: String
  def paradigm: String
  def sort(data: Array[Int]): Array[Int]
}