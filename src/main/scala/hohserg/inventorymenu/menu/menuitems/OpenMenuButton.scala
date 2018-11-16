package hohserg.inventorymenu.menu.menuitems

import Clickable.ClickHandler
import hohserg.inventorymenu.menu._
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

case class OpenMenuButton(menu: Menu, x: Int, y: Int, source: DataSource[ItemStack], openMenuOnClick: Player => Menu) extends Clickable {
  override def clickHandler: ClickHandler = (p, _) => openMenuOnClick(p).open()
}

object OpenMenuButton {
  def apply(x: Int, y: Int, source: DataSource[ItemStack], openMenuOnClick: Player => Menu): Menu => MenuItem = new OpenMenuButton(_, x, y, source, openMenuOnClick)

  def apply(x: Int, y: Int, source: ItemStack, openMenuOnClick: Player => Menu): Menu => MenuItem = new OpenMenuButton(_, x, y, ConstSource(source), openMenuOnClick)
}
