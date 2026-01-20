package engine
import java.lang.management.ManagementFactory
import scala.jdk.CollectionConverters._

object DeepMetrics {
  def getGCMetrics: (Long, Long) = {
    val gcBeans = ManagementFactory.getGarbageCollectorMXBeans.asScala
    val count = gcBeans.map(_.getCollectionCount).sum
    val time = gcBeans.map(_.getCollectionTime).sum
    (count, time)
  }
}