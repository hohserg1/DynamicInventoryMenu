package hohserg.inventorymenu.menu.menuitems

import hohserg.inventorymenu.menu.menuitems.Clickable.ClickHandler
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

trait Clickable extends MenuItem {
  def clickHandler: ClickHandler

}

object Clickable {
  /**
    * return value always ignored
    */
  type ClickHandler = (Player, Clickable) => PartialFunction[ClickType, Any]
  type ClickHandler_without_click_type = (Player, Clickable) => Any
}
