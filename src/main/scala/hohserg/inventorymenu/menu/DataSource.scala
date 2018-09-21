package hohserg.inventorymenu.menu

import hohserg.inventorymenu.notify.{Notified, Observable, OpenObservable}
import org.bukkit.inventory.ItemStack

import scala.collection.mutable

trait DataSource extends Observable {
  def getItem: ItemStack
}

case class SelectedSource[A, B](map: OpenObservable[A, B], index: Int, visualize: ((A, B)) => ItemStack) extends DataSource with Notified {
  map.addNotified(this)

  override def getItem: ItemStack =
    Option(map.toSeq)
      .filter(_.size > index)
      .map(i => visualize(i(index)))
      .orNull

  override def onUpdate(): Unit = notifyAllObjects()
}

case class ConstSource(itemStack: ItemStack) extends DataSource {
  override def getItem: ItemStack = itemStack
}

case class VariableSource[A](variable: A with Observable, visualize: A => ItemStack) extends DataSource with Notified {
  variable.addNotified(this)

  override def getItem: ItemStack = visualize(variable)

  override def onUpdate(): Unit = notifyAllObjects()
}

case class ListedSource[A, B](map: OpenObservable[A, B], pageSize: Int, visualize: ((A, B)) => ItemStack) extends Notified {
  def pageCount = math.ceil(map.size.toDouble / pageSize).toInt

  private[this] var _page = 0

  def page: Int = _page

  def page_=(value: Int): Unit = {
    _page = value
    updatePageContent()
  }

  val _pageMap = new mutable.OpenHashMap[A, B] with OpenObservable[A, B]
  updatePageContent()

  private def updatePageContent(): Unit = {
    _pageMap.clear()
    _pageMap ++= map.toList.slice(page * pageSize, page * pageSize + pageSize)
  }


  def pageMap: OpenObservable[A, B] = _pageMap

  override def onUpdate(): Unit = _pageMap.notifyAllObjects()
}
