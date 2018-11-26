package hohserg.inventorymenu.menu.menuitems

import Clickable.ClickHandler
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

trait Clickable extends MenuItem {
  def clickHandler: ClickHandler

}

object Clickable {
  /**
    * return value always ignored
    */
  type ClickHandler = (Player, Clickable,ClickType) => Any
}
