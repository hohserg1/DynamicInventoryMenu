package hohserg.inventorymenu.menu

import org.bukkit.inventory.{Inventory, InventoryHolder}

class MenuHolder(menu: Menu,val id:String) extends InventoryHolder {

  override def getInventory: Inventory = menu.inv
}
