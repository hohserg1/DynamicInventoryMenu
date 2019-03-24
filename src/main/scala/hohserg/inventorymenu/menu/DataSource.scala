package hohserg.inventorymenu.menu

import hohserg.inventorymenu.notify.{CollectionObservable, FlatMapStream, Observable, StreamImpl}
import org.bukkit.inventory.ItemStack

import scala.collection.mutable.ArrayBuffer

case class SelectedSource[A](collection: TraversableOnce[A] with Observable[TraversableOnce[A]],
                             index: Int, visualize: A => ItemStack) extends FlatMapStream[TraversableOnce[A], ItemStack] {
  override def transform: TraversableOnce[A] => Observable[ItemStack] = i => Option(i.toList)
    .filter(_.size > index)
    .map(i => visualize(i(index)))
    .map(Observable.join(_))
    .getOrElse(Observable.join())
}

case class ConstSource(itemStack: ItemStack) extends StreamImpl[Unit, ItemStack] {
  override def transform: Unit => ItemStack = _ => itemStack
}

case class ListedSource[A](collection: TraversableOnce[A] with Observable[TraversableOnce[A]], pageSize: Int, visualize: A => ItemStack) extends DataSource[ArrayBuffer[A] with Observable] {
  private val _pageMap = new ArrayBuffer[A] with CollectionObservable[A]

  def pageCount: Int = math.ceil(collection.size.toDouble / pageSize).toInt

  private[this] var _page = 0

  @inline def page: Int = _page

  def page_=(value: Int): Unit = {
    _page = value
    updatedPageContent()
  }


  private def updatedPageContent(): Unit = {
    val pageStart = page * pageSize
    _pageMap.clear()
    _pageMap ++= collection.toList.slice(pageStart, pageStart + pageSize)
  }

  override def getItem: ArrayBuffer[A] with Observable = _pageMap

  collection.addListener(this)

  override def onNotified(): Unit = {
    super.onNotified()
    updatedPageContent()
  }
}
