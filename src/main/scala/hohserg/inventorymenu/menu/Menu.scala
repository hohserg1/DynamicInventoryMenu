package hohserg.inventorymenu.menu

import java.util
import java.util.UUID

import hohserg.inventorymenu.java.MenuFactory
import hohserg.inventorymenu.menu.ListView.Area
import hohserg.inventorymenu.menu.menuitems.Clickable.ClickEvent
import hohserg.inventorymenu.menu.menuitems._
import hohserg.inventorymenu.notify.{Event, Observable}
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.ItemStack

import scala.collection.{Iterable, mutable}

class Menu(id: String, val player: Player, val name: String, val height: Int) {
  val width = 9
  val size: Int = height * width

  private[menu] val holder = new MenuHolder(this, id)

  private[menu] val inv = Bukkit.createInventory(holder, size, name)

  val items = new Array[MenuItem](size)

  def ++=(button: Iterable[Menu => MenuItem]): this.type = {
    button foreach +=
    this
  }

  def addBorder(borderFiller: Observable[ItemStack]): this.type = {
    for {
      (x, y) <- Area(0, 0, width - 1, height - 1)
      if !Area(1, 1, width - 2, height - 2).contains(x, y)
    }
      this += Decoration(x, y, borderFiller)
    this
  }

  def +=(button: Menu => MenuItem): this.type = {
    val btn = button(this)
    items(index(btn.x, btn.y)) = btn
    this
  }

  private def index(x: Int, y: Int) = x + y * 9


  def open() = {
    player openInventory inv
  }

  def onClick(player: Player, clickedSlot: Int, clickType: ClickType): Unit = {
    Option(clickedSlot)
      .filter(items.indices.contains)
      .map(items.apply)
      .collect { case clickable: Clickable => clickable }
      .foreach(clickable =>
        clickable.clickStream.notify(
          Event(
            ClickEvent(player, clickable, clickType)
          )
        )
      )
  }

  def onClose(): Unit = {}

  //java-support
  def add(button: Menu => MenuItem): Menu = this += button

  import collection.JavaConverters._

  def addAll(button: util.Collection[Menu => MenuItem]): Menu = this ++= button.asScala

  def addAll(button: Array[Menu => MenuItem]): Menu = this ++= button


}

object Menu {

  def applyOrCreate[A <: Menu](create: (String, Player) => A): MenuFactory[A] = applyOrCreate(_, UUID.randomUUID().toString, create(_, _))

  private def applyOrCreate[A <: Menu](player: Player, id: String, create: (String, Player) => A): A = apply(id, player).getOrElse(register(create(id, player), player))

  private[menu] def apply[A <: Menu](id: String, player: Player): Option[A] = map.get((id, player)).asInstanceOf[Option[A]]

  private val map = new mutable.OpenHashMap[(String, Player), Menu]

  private def register[A <: Menu](menu: A, player: Player): A = {
    map += (menu.holder.id, player) -> menu
    menu
  }

}
