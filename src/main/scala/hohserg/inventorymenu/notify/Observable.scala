package hohserg.inventorymenu.notify

import scala.collection.mutable.ListBuffer
import scala.ref.WeakReference

trait Observable {
  @transient protected val notifiedObjects = new ListBuffer[WeakReference[AbleNotify]]()

  def addNotified(e: AbleNotify): Unit = {
    notifiedObjects += WeakReference(e)
    e.onNotified()
  }

  @transient protected var canNotify = true

  def notifyAllObjects(): Unit = if (canNotify) notifiedObjects.foreach { n =>
    val o = n.get
    o.foreach(_.onNotified())
    if (o.isEmpty) notifiedObjects -= n
  }
}
