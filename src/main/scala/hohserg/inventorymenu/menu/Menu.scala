package hohserg.inventorymenu.menu

import java.util

import hohserg.inventorymenu.menu.ListView.Area
import hohserg.inventorymenu.menu.menuitems.Clickable.ClickHandler
import hohserg.inventorymenu.menu.menuitems._
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

import scala.collection.{Iterable, mutable}

class Menu(val player: Player, val name: String, val height: Int) {
  val width = 9
  val size: Int = height * width

  Menu.register(this, player)
  private[menu] val inv = Bukkit.createInventory(null, size, name)
  val items = new Array[Array[MenuItem]](9).map(_ => new Array[MenuItem](height))

  def ++=(button: Iterable[Menu => MenuItem]): this.type = {
    button.foreach(+=)
    this
  }

  def addBorder(borderFiller: ItemStack): this.type = {
    for {
      (x, y) <- Area(0, 0, width - 1, height - 1)
      if !Area(1, 1, width - 2, height - 2).contains(x, y)
    }
      this += Decoration(x, y, borderFiller)
    this
  }

  def -=(x: Int, y: Int): this.type = {
    val menuItem = items(x)(y)
    menuItem match {
      case clickable: Clickable =>
        val handler = clickable.clickHandler
        clickable.source match {
          case ConstSource(itemStack) =>
            clickHandlersMap -= itemStack
          case source =>
            clickHandlersList.remove(clickHandlersList.indexOf((source, handler(_, clickable))))
        }
      case _ =>
    }
    this
  }

  def +=(button: Menu => MenuItem): this.type = {
    val btn = button(this)

    Option(items(btn.x)(btn.y)).foreach(_ => -=(btn.x, btn.y))

    items(btn.x)(btn.y) = btn
    btn match {
      case clickable: Clickable =>
        val handler = clickable.clickHandler
        clickable.source match {
          case ConstSource(itemStack) =>
            registerHandler(itemStack, handler, clickable)
          case source =>
            registerHandler(source, handler, clickable)

        }
      case _ =>
    }
    this
  }

  def open() = {
    player openInventory inv
  }

  def onClick(player: Player, clicked: ItemStack): Unit = {
    clickHandlersMap.get(clicked).orElse(clickHandlersList.find(_._1.getItem == clicked).map(_._2)).foreach(_ (player))
  }

  private val clickHandlersList = new mutable.ListBuffer[(DataSource[ItemStack], Player => Any)]()

  private val clickHandlersMap = new mutable.OpenHashMap[ItemStack, Player => Any]()

  def registerHandler(item: DataSource[ItemStack], clickHandler: ClickHandler, menuItem: Clickable): Unit =
    clickHandlersList += item -> (clickHandler(_, menuItem))

  def registerHandler(item: ItemStack, clickHandler: ClickHandler, menuItem: Clickable): Unit =
    clickHandlersMap += item -> (clickHandler(_, menuItem))

  //java-support
  def add(button: Menu => MenuItem): Menu = this += button

  import collection.JavaConverters._

  def addAll(button: util.Collection[Menu => MenuItem]): Menu = this ++= button.asScala

  def addAll(button: Array[Menu => MenuItem]): Menu = this ++= button


}

object Menu {

  def applyOrCreate[A <: Menu](name: String, create: (Player, String) => A): Player => A = applyOrCreate(_, name, create(_, name))

  def applyOrCreate[A <: Menu](player: Player, name: String, create: Player => A): A = apply(name, player).getOrElse(create(player))

  def apply[A <: Menu](name: String, player: Player): Option[A] = map.get((name, player)).asInstanceOf[Option[A]]

  val map = new mutable.OpenHashMap[(String, Player), Menu]

  def register(menu: Menu, player: Player): Unit = {
    map += (menu.name, player) -> menu
  }

}
