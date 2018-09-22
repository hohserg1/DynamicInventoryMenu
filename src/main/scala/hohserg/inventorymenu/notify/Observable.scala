package hohserg.inventorymenu.notify

import scala.collection.mutable.ListBuffer
import scala.ref.WeakReference

trait Observable {
  protected val notifiedObjects = new ListBuffer[WeakReference[Notified]]()

  def addNotified(e: Notified): Unit = {
    notifiedObjects += WeakReference(e)
    e.onUpdate()
  }

  protected var canNotify = true

  def notifyAllObjects(): Unit = if (canNotify) notifiedObjects.foreach{n=>
    val o=n.get
    o.foreach(_.onUpdate())
    if(o.isEmpty)notifiedObjects-=n
  }
}
