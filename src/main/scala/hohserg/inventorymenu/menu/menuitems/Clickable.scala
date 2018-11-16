package hohserg.inventorymenu.menu.menuitems

import Clickable.ClickHandler
import org.bukkit.entity.Player

trait Clickable extends MenuItem {
  def clickHandler: ClickHandler

}

object Clickable {
  type ClickHandler = (Player, Clickable) => Any
}
