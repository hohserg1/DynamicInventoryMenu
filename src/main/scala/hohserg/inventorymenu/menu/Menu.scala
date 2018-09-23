package hohserg.inventorymenu.menu

import hohserg.inventorymenu.menu.Menu.ClickHandler
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

  private val noAction: ClickHandler = _ => ()

  def addButton(item: DataSource[ItemStack], x: Int, y: Int, clickHandler: ClickHandler): this.type = {
    buttons += Button(this, x, y, item, clickHandler)
    this
  }

  def addButton(item: ItemStack, x: Int, y: Int, clickHandler: ClickHandler): this.type =
    addButton(ConstSource(item), x, y,clickHandler)


  def addButtons(buttons:Seq[(DataSource[ItemStack],Int, Int,ClickHandler)]): this.type = {
    buttons.foreach(i=>addButton(i._1,i._2,i._3,i._4))
    this
  }

  def addDecoration(item: DataSource[ItemStack], x: Int, y: Int): this.type = {
    decorations += Decoration(this, x, y, item)
    this
  }

  def addDecoration(item: ItemStack, x: Int, y: Int): this.type =
    addDecoration(ConstSource(item), x, y)

  def addDecorations(decorations:Seq[(DataSource[ItemStack],Int, Int)]): this.type = {
    decorations.foreach(i=>addDecoration(i._1,i._2,i._3))
    this
  }


  def open() = {
    player openInventory inv
  }

  def onClick(player: Player, clicked: ItemStack): Unit = {
    clickHandlersMap.get(clicked).orElse(clickHandlersList.find(_._1.getItem == clicked).map(_._2)).foreach(_ (player))
  }

  val clickHandlersList = new mutable.ListBuffer[(DataSource[ItemStack], ClickHandler)]()

  val clickHandlersMap = new mutable.OpenHashMap[ItemStack, ClickHandler]()

  def registerHandler(item: DataSource[ItemStack], clickHandler: ClickHandler): Unit =
    clickHandlersList += item -> clickHandler

  def registerHandler(item: ItemStack, clickHandler: ClickHandler): Unit =
    clickHandlersMap += item -> clickHandler


}

object Menu {
  type ClickHandler = Player => Any

  def applyOrCreate(player: Player, name: String, create: Player => Menu): Menu = apply(name, player).getOrElse(create(player))

  def apply(name: String, player: Player): Option[Menu] = map.get((name, player))

  val map = new mutable.OpenHashMap[(String, Player), Menu]

  def register(menu: Menu, player: Player): Unit = {
    map += (menu.name, player) -> menu
  }

}
