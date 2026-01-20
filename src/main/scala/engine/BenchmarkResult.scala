package engine

case class BenchmarkResult(
                            algo: String,
                            paradigm: String,
                            size: Int,
                            timeMs: Long,
                            ramMb: Long,
                            gcCount: Long
                          )
