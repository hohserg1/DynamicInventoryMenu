package hohserg.inventorymenu.menu.menuitems

import Clickable.ClickHandler
import hohserg.inventorymenu.menu._
import org.bukkit.inventory.ItemStack

case class Button(menu: Menu, x: Int, y: Int, source: DataSource[ItemStack], clickHandler: ClickHandler) extends Clickable

object Button {
  def apply(x: Int, y: Int, source: DataSource[ItemStack], clickHandler: ClickHandler): Menu => MenuItem = new Button(_, x, y, source, clickHandler)

  def apply(x: Int, y: Int, source: ItemStack, clickHandler: ClickHandler): Menu => MenuItem = new Button(_, x, y, ConstSource(source), clickHandler)
}
