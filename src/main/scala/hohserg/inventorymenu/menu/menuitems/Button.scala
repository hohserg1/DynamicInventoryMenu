package hohserg.inventorymenu.menu.menuitems

import hohserg.inventorymenu.menu._
import hohserg.inventorymenu.menu.menuitems.Clickable.ClickEvent
import hohserg.inventorymenu.notify.Observable
import org.bukkit.inventory.ItemStack

case class Button(menu: Menu, x: Int, y: Int, source: Observable[ItemStack], subscribeOnClickEvents: Observable[ClickEvent] => Unit) extends Clickable

object Button {
  def apply(x: Int, y: Int, source: Observable[ItemStack], subscribeOnClickEvents: Observable[ClickEvent] => Unit): Menu => MenuItem =
    new Button(_, x, y, source, subscribeOnClickEvents)
}
