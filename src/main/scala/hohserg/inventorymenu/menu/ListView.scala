package hohserg.inventorymenu.menu

import hohserg.inventorymenu.menu.ListView.Area
import hohserg.inventorymenu.menu.menuitems.Clickable.ClickHandler
import hohserg.inventorymenu.menu.menuitems.ImplicitUtils._
import hohserg.inventorymenu.menu.menuitems.{Button, Decoration, MenuItem}
import hohserg.inventorymenu.notify.Observable
import hohserg.inventorymenu.utils.ItemUtils._
import org.bukkit.block.banner.PatternType
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.{DyeColor, Material}

import scala.collection.mutable.ArrayBuffer

class ListView[A](id: String, player: Player, name: String,
                  height: Int,
                  collection: TraversableOnce[A] with Observable,
                  visualize: A => ItemStack,
                  area: Area,
                  borderFiller: ItemStack = new ItemStack(Material.STAINED_GLASS_PANE),
                  buttonFactory: (Int, Int, DataSource[ItemStack]) => Menu => MenuItem = Decoration.apply) extends Menu(id, player, name, height) {

  val source = ListedSource(collection, area.square, visualize)
  val page = source.getItem

  addBorder(borderFiller)

  for {
    (x, y) <- area
  }
    this += buttonFactory(x, y, SelectedSource(page, x - area.x1 + (y - area.y1) * (area.x2 - area.x1 + 1), visualize))

  def getIconFor(direction: Int, color: DyeColor): ItemStack = {
    if (direction < 0)
      banner(PatternType.TRIANGLE_BOTTOM, color)
    else
      banner(PatternType.TRIANGLE_TOP, color)
  }

  def addScroll(x: Int, y: Int, color: DyeColor, text: (String, String, String)): this.type =
    addScrollButton(-1, x, y - 1, color, text._1)
      .addPageIndicator(x, y, color, text._2)
      .addScrollButton(1, x, y + 1, color, text._3)

  private def listingPage(direction: Int): ClickHandler = {
    _ =>
      val newPage = source.page + direction
      if (newPage >= 0 && newPage < source.pageCount)
        source.page = newPage
  }

  def addScrollButton(direction: Int, x: Int, y: Int, color: DyeColor, text: String): this.type =
    addScrollButton(direction, x, y, getIconFor(direction, color), text)

  def addScrollButton(direction: Int, x: Int, y: Int, item: ItemStack, text: String): this.type =
    this += Button(x, y, lorize(item, text), listingPage(direction))

  def addPageIndicator(x: Int, y: Int, item: ItemStack, text: String): this.type =
    this += Decoration(x, y, VariableSource[ArrayBuffer[A] with Observable](page, _ => lorize(item, text.format(source.page + 1, source.pageCount))))

  def addPageIndicator(x: Int, y: Int, color: DyeColor, text: String): this.type =
    addPageIndicator(x, y, banner(PatternType.BASE, color), text)


}

object ListView {

  case class Area(x1: Int, y1: Int, x2: Int, y2: Int) extends Iterable[(Int, Int)] {
    def contains(x: Int, y: Int): Boolean = x >= x1 && x <= x2 && y >= y1 && y <= y2

    def square: Int = (x2 - x1 + 1) * (y2 - y1 + 1)

    override def iterator: Iterator[(Int, Int)] =
      (for {
        x <- x1 to x2
        y <- y1 to y2
      } yield (x, y)).iterator
  }

  //java_support
  def apply[A](name: String,
               height: Int,
               collection: TraversableOnce[A] with Observable, visualize: A => ItemStack,
               area: Area,
               borderFiller: ItemStack = new ItemStack(Material.STAINED_GLASS_PANE),
               buttonFactory: (Int, Int, DataSource[ItemStack]) => Menu => MenuItem = Decoration.apply): (String, Player) => ListView[A] = {
    new ListView[A](_, _, name, height, collection, visualize, area, borderFiller, buttonFactory)
  }

}
