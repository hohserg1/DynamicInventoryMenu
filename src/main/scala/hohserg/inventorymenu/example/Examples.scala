package hohserg.inventorymenu.example

import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.atomic.AtomicInteger

import hohserg.inventorymenu.Main.plugin
import hohserg.inventorymenu.menu.ListView.Area
import hohserg.inventorymenu.menu.{DataSource, ListView, Menu, VariableSource}
import hohserg.inventorymenu.menu.menuitems.{Button, Decoration}
import hohserg.inventorymenu.notify.{CollectionObservable, Observable, OpenObservable}
import org.bukkit.{Bukkit, DyeColor, Location, Material}
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta

import scala.collection.mutable

object Examples {
  val worldTime = new AtomicInteger with Observable

  def onEnable(): Unit = {
    Bukkit.getScheduler.scheduleSyncRepeatingTask(plugin, new Runnable {
      override def run(): Unit = {
        if (Bukkit.getWorld("world").getTime.toInt % 750 == 0) {
          worldTime.set(Bukkit.getWorld("world").getTime.toInt)
          worldTime.notifyAllObjects()
        }
      }
    }, 10, 1)
  }

  val map = new mutable.OpenHashMap[String, Int] with OpenObservable[String, Int]
  for (i <- 0 to 100) map += ("test" + i) -> 1

  val tupleToStack: ((String, Int)) => ItemStack = {
    case (name: String, i: Int) =>
      val r = new ItemStack(Material.APPLE, i)
      val meta = r.getItemMeta
      meta.setDisplayName(name)
      r.setItemMeta(meta)
      r
  }
  val clickSource = new DataSource[ItemStack] {
    override def getItem: ItemStack = new ItemStack(Material.APPLE, Bukkit.getWorld("world").getTime.toInt / 100)
  }
  val menu = Menu.applyOrCreate("Hello World!",
    new Menu(_, _, 3)
      += Decoration(4, 1, VariableSource(worldTime, (time: AtomicInteger) => new ItemStack(Material.APPLE, time.get() / 750)))
  )

  def getPlayerHead(player: Player): ItemStack = {
    val skull = new ItemStack(Material.SKULL_ITEM, 1, 3.toByte)

    val meta = skull.getItemMeta.asInstanceOf[SkullMeta]
    meta.setOwner(player.getName)
    skull.setItemMeta(meta)

    skull
  }

  val marks = new mutable.ArrayBuffer[(Location, Date)] with CollectionObservable[(Location, Date)]

  import hohserg.inventorymenu.utils.ItemUtils._

  val df = new SimpleDateFormat("EEEEE dd MMMMM yyyy")

  val menu1 = Menu.applyOrCreate("My marks",
    new ListView[(Location, Date)](_, _, 5,
      marks,
      { case (loc: Location, date: Date) => lorize(new ItemStack(Material.EMERALD), "" + loc.getBlockX + " " + loc.getBlockY + " " + loc.getBlockZ + " " + df.format(date)) },
      Area(1, 1, 7, 3))
      .addScroll(0, 2, DyeColor.RED, ("Вверх", "Страница %d/%d", "Вниз"))
      += Button(8, 2, lorize(new ItemStack(Material.NETHER_STAR), "Добавить метку"), (player: Player) => marks += player.getLocation -> new Date())
  )

  val menu2 = Menu.applyOrCreate("TestListView",
    new ListView(_, _, 5, map, tupleToStack, Area(1, 1, 7, 3))
      .addScroll(0, 2, DyeColor.CYAN, ("Вверх", "Страница %d из %d", "Вниз"))
  )
  var i = 0

}
