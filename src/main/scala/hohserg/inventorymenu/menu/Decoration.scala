package hohserg.inventorymenu.menu

import org.bukkit.inventory.ItemStack

case class Decoration(menu: Menu, x: Int, y: Int, source: DataSource[ItemStack]) extends MenuItem
