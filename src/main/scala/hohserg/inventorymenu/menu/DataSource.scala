package hohserg.inventorymenu.menu

import hohserg.inventorymenu.notify.{CollectionObservable, Observable, Pipe}
import org.bukkit.inventory.ItemStack

import scala.collection.mutable.ArrayBuffer

trait DataSource[A] extends Observable with Pipe {
  def getItem: A
}

case class SelectedSource[A](collection: TraversableOnce[A] with Observable, index: Int, visualize: A => ItemStack, alternative: ItemStack = null) extends DataSource[ItemStack] {
  collection.addNotified(this)

  override def getItem: ItemStack =
    Option(collection.toList)
      .filter(_.size > index)
      .map(i => visualize(i(index)))
      .getOrElse(alternative)
}

case class ConstSource(itemStack: ItemStack) extends DataSource[ItemStack] {
  override def getItem: ItemStack = itemStack
}

case class VariableSource[A](variable: A with Observable, visualize: A => ItemStack) extends DataSource[ItemStack] {
  variable.addNotified(this)

  override def getItem: ItemStack = visualize(variable)
}

case class ListedSource[A](collection: TraversableOnce[A] with Observable, pageSize: Int, visualize: A => ItemStack) extends DataSource[ArrayBuffer[A] with Observable] {
  private val _pageMap = new ArrayBuffer[A] with CollectionObservable[A]

  def pageCount: Int = math.ceil(collection.size.toDouble / pageSize).toInt

  private[this] var _page = 0

  def page: Int = _page

  def page_=(value: Int): Unit = {
    _page = value
    updatedPageContent()
  }


  private def updatedPageContent(): Unit = {
    val pageStart = page * pageSize
    println(_pageMap)
    _pageMap.clear()
    _pageMap ++= collection.toList.slice(pageStart, pageStart + pageSize)
  }

  override def getItem: ArrayBuffer[A] with Observable = _pageMap

  collection.addNotified(this)

  override def onUpdate(): Unit = {
    super.onUpdate()
    updatedPageContent()
  }
}
