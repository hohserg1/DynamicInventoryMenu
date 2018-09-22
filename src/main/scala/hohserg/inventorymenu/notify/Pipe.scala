package hohserg.inventorymenu.notify

trait Pipe extends Notified with Observable {
  override def onUpdate(): Unit = notifyAllObjects()
}
