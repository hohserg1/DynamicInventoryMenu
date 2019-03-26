package hohserg.inventorymenu.menu.menuitems

import hohserg.inventorymenu.menu._
import hohserg.inventorymenu.notify.Observable
import org.bukkit.inventory.ItemStack

case class Decoration(menu: Menu, x: Int, y: Int, source: Observable[ItemStack]) extends MenuItem

object Decoration {
  def apply(x: Int, y: Int, source: Observable[ItemStack]): Menu => MenuItem = new Decoration(_, x, y, source)
}
