package hohserg.inventorymenu.menu.menuitems

import hohserg.inventorymenu.menu.menuitems.Clickable.ClickEvent
import hohserg.inventorymenu.notify.Observable
import hohserg.inventorymenu.notify.subjects.Identity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType

trait Clickable extends MenuItem {
  def subscribeOnClickEvents: Observable[ClickEvent] => Unit

  private[inventorymenu] lazy val clickStream = new Identity[ClickEvent]
  subscribeOnClickEvents(clickStream)

}

object Clickable {

  case class ClickEvent(player: Player, clickable: Clickable, clickType: ClickType)

}
