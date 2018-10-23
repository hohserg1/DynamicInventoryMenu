package hohserg.inventorymenu

import hohserg.inventorymenu.example.Examples
import hohserg.inventorymenu.menu._
import org.bukkit.Bukkit
import org.bukkit.command.{Command, CommandSender}
import org.bukkit.entity.Player

object Main {

  def onEnable(): Unit = {
    Bukkit.getPluginManager.registerEvents(new MenuListener, plugin)
    Examples.onEnable()
  }

  def plugin: PluginPreloader = {
    PluginPreloader.instance
  }

  def onCommand(sender: CommandSender, command: Command, label: String, args: Array[String]): Boolean = {
    import hohserg.inventorymenu.example.Examples._
    sender match {
      case player: Player =>
        args.toList match {
          case _ :: Nil =>
            map += ("test" + i) -> 1
            i += 1
          case _ =>


            menu1(player).open()
        }
      case _ =>
    }

    true
  }

  def onDisable(): Unit = {
  }

}
