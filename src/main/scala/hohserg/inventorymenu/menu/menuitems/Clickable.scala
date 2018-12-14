package hohserg.inventorymenu.menu.menuitems

import hohserg.inventorymenu.menu.menuitems.Clickable.PartialClickHandler
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

trait Clickable extends MenuItem {
  def clickHandler: PartialClickHandler

}

object Clickable {
  /**
    * return value always ignored
    */
  type ClickHandler = ClickEvent => Any

  type PartialClickHandler = PartialFunction[ClickEvent, Any]

  case class ClickEvent(player: Player, clickable: Clickable, clickType: ClickType)

}
