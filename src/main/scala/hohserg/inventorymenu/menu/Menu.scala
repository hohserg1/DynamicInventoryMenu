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
  val items = new ListBuffer[MenuItem]()

  def ++=(button: Seq[Menu => MenuItem]): this.type = {
    button.foreach(+=)
    this
  }

  def +=(button: Menu => MenuItem): this.type = {
    val btn = button(this)
    items += btn
    btn match {
      case Button(_, _, _, ConstSource(itemStack), clickHandler) =>
        registerHandler(itemStack, clickHandler)
      case Button(_, _, _, source, clickHandler) =>
        registerHandler(source, clickHandler)
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

  private val clickHandlersList = new mutable.ListBuffer[(DataSource[ItemStack], ClickHandler)]()

  private val clickHandlersMap = new mutable.OpenHashMap[ItemStack, ClickHandler]()

  def registerHandler(item: DataSource[ItemStack], clickHandler: ClickHandler): Unit =
    clickHandlersList += item -> clickHandler

  def registerHandler(item: ItemStack, clickHandler: ClickHandler): Unit =
    clickHandlersMap += item -> clickHandler


}

object Menu {
  type ClickHandler = Player => Any

  def applyOrCreate(name: String, create: (Player, String)=> Menu)(player: Player): Menu = applyOrCreate(player,name,create(_,name))

  def applyOrCreate(player: Player, name: String, create: Player => Menu): Menu = apply(name, player).getOrElse(create(player))

  def apply(name: String, player: Player): Option[Menu] = map.get((name, player))

  val map = new mutable.OpenHashMap[(String, Player), Menu]

  def register(menu: Menu, player: Player): Unit = {
    map += (menu.name, player) -> menu
  }

}
