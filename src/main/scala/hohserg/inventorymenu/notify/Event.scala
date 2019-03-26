package hohserg.inventorymenu.notify

import org.bukkit.Bukkit

case class Event[+A](value: A, timemark: Long = Bukkit.getWorlds.get(0).getFullTime)