package hohserg.inventorymenu.menu

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class Menu(val player: Player, val name: String, val size: Int) {
  Menu.register(this, player)
  private[menu] val inv = Bukkit.createInventory(null, size, name)
  val buttons = new ListBuffer[Button]()
  val decorations = new ListBuffer[Decoration]()

  private val noAction: Player => Unit = _ => ()

  def addDecoration(item: ItemStack, x: Int, y: Int): this.type =
    addDecoration(ConstSource(item), x, y)

  def addButton(item: ItemStack, x: Int, y: Int, clickHandler: Player => Unit): this.type = {
    buttons += Button(this, x, y, ConstSource(item), clickHandler)
    if (clickHandler != noAction)
      registerHandler(item, clickHandler)
    this
  }

  def addDecoration(item: DataSource, x: Int, y: Int): this.type = {
    decorations += Decoration(this, x, y, item)
    this
  }

  def addButton(item: DataSource, x: Int, y: Int, clickHandler: Player => Unit): this.type = {
    buttons += Button(this, x, y, item, clickHandler)
    if (clickHandler != noAction)
      registerHandler(item, clickHandler)
    this
  }

  def open() = {
    player openInventory inv
  }

  def onClick(player: Player, clicked: ItemStack): Unit = {
    clickHandlersMap.get(clicked).orElse(clickHandlersList.find(_._1.getItem == clicked).map(_._2)).foreach(_ (player))
  }

  val clickHandlersList = new mutable.ListBuffer[(DataSource, Player => Unit)]()

  val clickHandlersMap = new mutable.OpenHashMap[ItemStack, Player => Unit]()

  def registerHandler(item: DataSource, clickHandler: Player => Unit): Unit =
    clickHandlersList += item -> clickHandler

  def registerHandler(item: ItemStack, clickHandler: Player => Unit): Unit =
    clickHandlersMap += item -> clickHandler


}

object Menu {
  def apply(name: String, player: Player): Option[Menu] = map.get((name, player))

  val map = new mutable.OpenHashMap[(String, Player), Menu]

  def register(menu: Menu, player: Player): Unit = {
    map += (menu.name, player) -> menu
  }

}
