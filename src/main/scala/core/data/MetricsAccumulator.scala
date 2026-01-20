package core.data

class MetricsAccumulator {
  private var comparisons: Long = 0
  private var swaps: Long = 0

  def addComparison(): Unit = comparisons += 1
  def addSwap(): Unit = swaps += 1
  def getComparisons: Long = comparisons
  def getSwaps: Long = swaps
}