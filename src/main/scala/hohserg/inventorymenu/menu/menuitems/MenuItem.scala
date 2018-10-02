package hohserg.inventorymenu.menu.menuitems

import hohserg.inventorymenu.menu.{DataSource, Menu}
import hohserg.inventorymenu.notify.Notified
import org.bukkit.inventory.ItemStack

trait MenuItem extends Notified {
  def menu: Menu
  def x: Int
  def y: Int
  def source: DataSource[ItemStack]

  source.addNotified(this)

  protected def setInventoryContents(): Unit =
    menu.inv.setItem(x + y * 9, source.getItem)


  def onUpdate(): Unit = setInventoryContents()
}
