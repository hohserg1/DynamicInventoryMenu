package hohserg.inventorymenu.notify

trait Pipe extends AbleNotify with Observable {
  override def onNotified(): Unit = notifyAllObjects()
}
