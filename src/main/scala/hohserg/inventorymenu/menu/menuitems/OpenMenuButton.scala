package hohserg.inventorymenu.menu.menuitems

import hohserg.inventorymenu.menu.ImplicitUtils._
import hohserg.inventorymenu.menu._
import hohserg.inventorymenu.menu.menuitems.Clickable.ClickEvent
import hohserg.inventorymenu.notify.Observable
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

case class OpenMenuButton(menu: Menu, x: Int, y: Int, source: Observable[ItemStack], openMenuOnClick: Player => Menu) extends Clickable {
  override def subscribeOnClickEvents: Observable[Clickable.ClickEvent] => Unit = _.subscribe { e: ClickEvent => openMenuOnClick(e.player).open() }
}

object OpenMenuButton {
  def apply(x: Int, y: Int, source: Observable[ItemStack], openMenuOnClick: Player => Menu): Menu => MenuItem =
    new OpenMenuButton(_, x, y, source, openMenuOnClick)
}
