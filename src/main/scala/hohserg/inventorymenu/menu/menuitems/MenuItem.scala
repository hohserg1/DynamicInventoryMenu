package hohserg.inventorymenu.menu.menuitems

import hohserg.inventorymenu.menu.Menu
import hohserg.inventorymenu.notify.{AbleNotify, Event, Observable}
import org.bukkit.inventory.ItemStack

trait MenuItem extends AbleNotify[ItemStack] {
  def menu: Menu

  def x: Int

  def y: Int

  def source: Observable[ItemStack]

  source subscribe this

  override def notify(event: Event[ItemStack]): Unit = setInventoryContents(event.value)

  protected def setInventoryContents(itemStack: ItemStack): Unit =
    menu.inv.setItem(x + y * 9, itemStack)
}
