package hohserg.inventorymenu.menu

import java.util.UUID

import org.bukkit.inventory.{Inventory, InventoryHolder}

class MenuHolder(menu: Menu) extends InventoryHolder {
  def id: String = menu.name + UUID.randomUUID()

  override def getInventory: Inventory = menu.inv
}
