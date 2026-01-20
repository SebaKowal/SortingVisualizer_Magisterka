package core.data

case class FullReportRow(
                          algoName: String, paradigm: String, dataSize: Int, dataType: String,
                          comparisons: Long, swaps: Long, timeNs: Long,
                          memoryUsedMB: Double, gcCycles: Long, gcTimeMs: Long,
                          threadCount: Int, osName: String, cpuCores: Int, jvmVersion: String
                        )
