package hohserg.inventorymenu.menu.menuitems

import hohserg.inventorymenu.menu._
import hohserg.inventorymenu.menu.menuitems.Clickable.PartialClickHandler
import org.bukkit.inventory.ItemStack

case class Button(menu: Menu, x: Int, y: Int, source: DataSource[ItemStack], clickHandler: PartialClickHandler) extends Clickable

object Button {
  def apply(x: Int, y: Int, source: DataSource[ItemStack], clickHandler: PartialClickHandler): Menu => MenuItem =
    new Button(_, x, y, source, clickHandler)
}
