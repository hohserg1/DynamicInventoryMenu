package hohserg.inventorymenu.menu

import java.util
import java.util.UUID

import hohserg.inventorymenu.java.MenuFactory
import hohserg.inventorymenu.menu.ListView.Area
import hohserg.inventorymenu.menu.menuitems.Clickable.{ClickEvent, PartialClickHandler, PartialPartialClickHandler}
import hohserg.inventorymenu.menu.menuitems._
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
      this += Decoration(x, y).source(borderFiller).build
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
            clickHandlersList.remove(clickHandlersList.indexOf((source, clickable -> handler)))
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
        val handler: PartialClickHandler = clickable.clickHandler
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

  def open(closingHandler: Menu => Unit) = {
    this.closingHandler = Some(closingHandler)
    player openInventory inv
  }

  def onClick(player: Player, clicked: ItemStack, clickType: ClickType): Unit = {
    val clickRequest = clickHandlersMap
      .get(clicked)
      .orElse(
        clickHandlersList
          .find(_._1.getItem == clicked)
          .map(_._2))
      .map { case (clickable, clickHandler) => (clickable, ClickEvent(player, clickable, clickType), clickHandler) }
      .filter { case (_, clickEvent, clickHandler) => clickHandler isDefinedAt clickEvent }

    _lastClicked = clickRequest.map(_._1)
    clickRequest.foreach(i => i._3(i._2))
  }

  private var _lastClicked: Option[Clickable] = None

  def lastClicked: Option[Clickable] = _lastClicked

  def onClose(): Unit = {
    closingHandler.foreach(_ (this))
    closingHandler = None
  }

  private var closingHandler: Option[Menu => Unit] = None

  private val clickHandlersList = new mutable.ListBuffer[(DataSource[ItemStack], (Clickable, PartialClickHandler))]()

  private val clickHandlersMap = new mutable.OpenHashMap[ItemStack, (Clickable, PartialClickHandler)]()

  def registerHandler(item: DataSource[ItemStack], clickHandler: PartialClickHandler, menuItem: Clickable): Unit =
    clickHandlersList += item -> (menuItem -> clickHandler)

  def registerHandler(item: ItemStack, clickHandler: PartialClickHandler, menuItem: Clickable): Unit =
    clickHandlersMap += item -> (menuItem -> clickHandler)

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
