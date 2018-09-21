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
  val map=new mutable.OpenHashMap[String,Int] with OpenObservable[String,Int]
  for(i<- 0 to 100)
    map+=("test"+i)->1

  val tupleToStack:((String,Int))=>ItemStack = {
    case (name: String, i: Int) =>
      val r = new ItemStack(Material.APPLE, i)
      val meta = r.getItemMeta
      meta.setDisplayName(name)
      r.setItemMeta(meta)
      r
  }

  def onCommand(sender: CommandSender, command: Command, label: String, args: Array[String]): Boolean = {
    sender match {
      case player: Player =>
        args.toList match {
          case nickname :: amount :: Nil =>
            val menu=new Menu(player,"Test",45)
              .addButton(SelectedSource[String,Int](map,0, tupleToStack),1,1,{_:Player=>map.clear();map+=nickname->amount.toInt;()})
            menu.open()
          case _ =>
            val menu2=new ListView(player,"TestListView",45,map,tupleToStack,Area(1,1,7,3))
              .addScroll(0,2,DyeColor.CYAN,("Вверх","Страница %d из %d","Вниз"))
            menu2.open()
        }
      case _ =>
    }
    /*
    val hhMenu=Menu("HeadHunter",
      Button(SelectedSource(autoRegistrated,0)),
      Button(SelectedSource(autoRegistrated,1)),
      Button(SelectedSource(autoRegistrated,2)),

      Button(FixedSource(name="Заказы",material=Material.CHEST),player=>requestsMenu.open(player)),
      Button(FixedSource(name="История",material=Material.ENDER_CHEST),player=>historyMenu.open(player)),
      Button(FixedSource(name=player.getDisplayName,lore=))

    )*/

    true
  }

  def onDisable(): Unit = {
  }

}
