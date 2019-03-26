package hohserg.inventorymenu.menu

import hohserg.inventorymenu.menu.ListView.Area
import hohserg.inventorymenu.menu.menuitems.Clickable.ClickEvent
import ImplicitUtils._
import hohserg.inventorymenu.menu.menuitems.{Button, Decoration, MenuItem}
import hohserg.inventorymenu.notify.{Observable, Variable}
import hohserg.inventorymenu.utils.ItemUtils._
import org.bukkit.block.banner.PatternType
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.{DyeColor, Material}

class ListView[A](id: String, player: Player, name: String,
                  height: Int,
                  collection: TraversableOnce[A] with Observable[TraversableOnce[A]],
                  visualize: A => ItemStack,
                  area: Area,
                  borderFiller: ItemStack = new ItemStack(Material.STAINED_GLASS_PANE),
                  buttonFactory: (Int, Int, Observable[ItemStack]) => Menu => MenuItem = Decoration.apply) extends Menu(id, player, name, height) {

  private val page = Variable[Int](0)

  private val pageSize = area.square

  private val source = collection zip page map { case (coll: TraversableOnce[A], page: Int) =>
    val pageStart = page * pageSize
    coll.toList.slice(pageStart, pageStart + pageSize).map(visualize)
  }


  addBorder(borderFiller)

  for {
    (x, y) <- area
  }
    this += buttonFactory(x, y, SelectedSource(source, x - area.x1 + (y - area.y1) * (area.x2 - area.x1 + 1), visualize))

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

  def pageCount: Int = math.ceil(collection.size.toDouble / pageSize).toInt

  private def listingPage(direction: Int): Observable[ClickEvent] => Unit = {
    _ =>
      val newPage = page.get + direction
      if (newPage >= 0 && newPage < pageCount)
        page.set(newPage)
  }

  def addScrollButton(direction: Int, x: Int, y: Int, color: DyeColor, text: String): this.type =
    addScrollButton(direction, x, y, getIconFor(direction, color), text)

  def addScrollButton(direction: Int, x: Int, y: Int, item: ItemStack, text: String): this.type =
    this += Button(x, y, lorize(item, text), listingPage(direction))

  def addPageIndicator(x: Int, y: Int, item: ItemStack, text: String): this.type =
    this += Decoration(x, y, page.map(i => lorize(item, text.format(i + 1, pageCount))))

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
               collection: TraversableOnce[A] with Observable[TraversableOnce[A]], visualize: A => ItemStack,
               area: Area,
               borderFiller: ItemStack = new ItemStack(Material.STAINED_GLASS_PANE),
               buttonFactory: (Int, Int, Observable[ItemStack]) => Menu => MenuItem = Decoration.apply): (String, Player) => ListView[A] = {
    new ListView[A](_, _, name, height, collection, visualize, area, borderFiller, buttonFactory)
  }

}
