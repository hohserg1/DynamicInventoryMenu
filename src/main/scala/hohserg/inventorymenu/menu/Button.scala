package hohserg.inventorymenu.menu

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

private[menu] case class Button(menu: Menu, x: Int, y: Int, source: DataSource[ItemStack], clickHandler: Player => Any) extends MenuItem
