package hohserg.inventorymenu.menu.menuitems

import hohserg.inventorymenu.menu._
import hohserg.inventorymenu.menu.menuitems.Clickable.PartialClickHandler
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

case class OpenMenuButton(menu: Menu, x: Int, y: Int, source: DataSource[ItemStack], openMenuOnClick: Player => Menu) extends Clickable {
  override def clickHandler: PartialClickHandler = {case ce => openMenuOnClick(ce.player).open()}
}

object OpenMenuButton {
  def apply(x: Int, y: Int, source: DataSource[ItemStack], openMenuOnClick: Player => Menu): Menu => MenuItem =
    new OpenMenuButton(_, x, y, source, openMenuOnClick)
}
