package hohserg.inventorymenu.notify

import scala.collection.mutable.ListBuffer

trait Observable {
  protected val notifiedObjects = new ListBuffer[Notified]()

  def addNotified(e: Notified): Unit = {
    notifiedObjects += e
    e.onUpdate()
  }

  protected var canNotify = true

  def notifyAllObjects(): Unit = if (canNotify) notifiedObjects.foreach(_.onUpdate())
}
