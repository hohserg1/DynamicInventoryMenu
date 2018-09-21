package hohserg.inventorymenu.menu

import org.bukkit.entity.Player

private[menu] case class Button(menu: Menu, x: Int, y: Int, source: DataSource, clickHandler: Player => Any) extends MenuItem
