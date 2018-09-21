package hohserg.inventorymenu.menu

import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.{EventHandler, Listener}

class MenuListener extends Listener {
  @EventHandler
  def onClick(e: InventoryClickEvent): Unit = {
    val player = e.getWhoClicked.asInstanceOf[Player]
    val clicked = e.getCurrentItem
    val inventory = e.getInventory
    Menu(inventory.getName, player).foreach{ menu=>
      menu.onClick(player, clicked)
      e.setCancelled(true)
    }
  }
}