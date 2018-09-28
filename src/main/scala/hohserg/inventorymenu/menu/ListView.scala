package hohserg.inventorymenu.menu

import hohserg.inventorymenu.menu.ListView.Area
import hohserg.inventorymenu.notify.Observable
import org.bukkit.block.banner.PatternType
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.{DyeColor, Material}
import hohserg.inventorymenu.utils.ItemUtils._

import scala.collection.mutable.ArrayBuffer

class ListView[A](player: Player, name: String, size: Int, collection: TraversableOnce[A] with Observable, visualize: A => ItemStack, area: Area, borderFiller: ItemStack = new ItemStack(Material.STAINED_GLASS_PANE)) extends Menu(player, name, size) {
  val source = ListedSource(collection, area.square, visualize)
  val page = source.getItem

  for {
    (x, y) <- Area(0, 0, 8, 4)
    if !area.contains(x, y)
  }
    this += Decoration(x, y, borderFiller)

  for {
    (x, y) <- area
  }
    this += Decoration(x, y, SelectedSource(page, x - area.x1 + (y - area.y1) * (area.x2 - area.x1 + 1), visualize))

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

  private def listingPage(direction: Int): Player => Unit =
    (_: Player) => {
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

}
