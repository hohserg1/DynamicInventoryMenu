package hohserg.inventorymenu

import hohserg.inventorymenu.menu.ListView.Area
import hohserg.inventorymenu.menu.{ListView, Menu, MenuListener, SelectedSource}
import hohserg.inventorymenu.notify.OpenObservable
import org.bukkit.command.{Command, CommandSender}
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.{Bukkit, DyeColor, Material}

import scala.collection.mutable

object Main {
  def onEnable(): Unit = {

    Bukkit.getPluginManager.registerEvents(new MenuListener, plugin)
  }

  def plugin: PluginPreloader = {
    PluginPreloader.instance
  }

  val map = new mutable.OpenHashMap[String, Int] with OpenObservable[String, Int]
  for (i <- 0 to 100)
    map += ("test" + i) -> 1

  val tupleToStack: ((String, Int)) => ItemStack = {
    case (name: String, i: Int) =>
      val r = new ItemStack(Material.APPLE, i)
      val meta = r.getItemMeta
      meta.setDisplayName(name)
      r.setItemMeta(meta)
      r
  }

  val menu: Player => Menu = new Menu(_, "Test", 45)
    .addDecoration(SelectedSource[String, Int](map, 0, tupleToStack), 1, 1)

  val menu2: Player => ListView[String, Int] = new ListView(_, "TestListView", 45, map, tupleToStack, Area(1, 1, 7, 3))
    .addScroll(0, 2, DyeColor.CYAN, ("Вверх", "Страница %d из %d", "Вниз"))

  def onCommand(sender: CommandSender, command: Command, label: String, args: Array[String]): Boolean = {
    sender match {
      case player: Player =>
        args.toList match {
          case _ :: Nil =>
            menu(player).open()
          case _ =>
            menu2(player).open()
        }
      case _ =>
    }

    true
  }

  def onDisable(): Unit = {
  }

}
