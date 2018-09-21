package hohserg.inventorymenu.menu

import hohserg.inventorymenu.notify.Notified

trait MenuItem extends Notified {
  def menu: Menu
  def x: Int
  def y: Int
  def source: DataSource

  source.addNotified(this)

  protected def setInventoryContents(): Unit =
    menu.inv.setItem(x + y * 9, source.getItem)


  def onUpdate(): Unit = setInventoryContents()
}
