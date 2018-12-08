package hohserg.inventorymenu.menu.menuitems

import hohserg.inventorymenu.menu._
import hohserg.inventorymenu.menu.menuitems.Clickable.{ClickHandler, ClickHandler_without_click_type}
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

case class Button(menu: Menu, x: Int, y: Int, source: DataSource[ItemStack], clickHandler: ClickHandler) extends Clickable

object Button {
  def apply(x: Int, y: Int, source: DataSource[ItemStack], clickHandler: ClickHandler): Menu => MenuItem =
    new Button(_, x, y, source, clickHandler)

  def apply(x: Int, y: Int, source: ItemStack, clickHandler: ClickHandler): Menu => MenuItem =
    new Button(_, x, y, ConstSource(source), clickHandler)

  def apply(x: Int, y: Int, source: ItemStack, clickHandler: ClickHandler_without_click_type): Menu => MenuItem =
    new Button(_, x, y, ConstSource(source), (player: Player, c: Clickable) => {
      case _ => clickHandler(player, c)
    })
}
