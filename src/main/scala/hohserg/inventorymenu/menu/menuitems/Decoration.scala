package hohserg.inventorymenu.menu.menuitems

import hohserg.inventorymenu.menu._
import org.bukkit.inventory.ItemStack

case class Decoration(menu: Menu, x: Int, y: Int, source: DataSource[ItemStack]) extends MenuItem

object Decoration {
  def apply(x: Int, y: Int, source: DataSource[ItemStack]): Menu => MenuItem = new Decoration(_, x, y, source)
}
