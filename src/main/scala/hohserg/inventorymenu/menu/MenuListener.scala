package hohserg.inventorymenu.menu

import org.bukkit.entity.Player
import org.bukkit.event.inventory.{InventoryClickEvent, InventoryCloseEvent}
import org.bukkit.event.{EventHandler, Listener}

class MenuListener extends Listener {
  @EventHandler
  def onClick(e: InventoryClickEvent): Unit = {
    val player = e.getWhoClicked.asInstanceOf[Player]
    val clicked = e.getCurrentItem
    val inventory = e.getInventory
    Option(inventory.getHolder).collect { case mh: MenuHolder => mh }.map(_.id).foreach {
      Menu[Menu](_, player).foreach { menu =>
        e.setCancelled(true)
        menu.onClick(player, clicked,e.getClick)
      }
    }
  }
  @EventHandler
  def onClose(e: InventoryCloseEvent): Unit = {
    val player = e.getPlayer.asInstanceOf[Player]
    val inventory = e.getInventory
    Option(inventory.getHolder).collect { case mh: MenuHolder => mh }.map(_.id).foreach {
      Menu[Menu](_, player).foreach { menu =>
        menu.onClose()
      }
    }

  }
}
