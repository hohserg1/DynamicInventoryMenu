package hohserg.inventorymenu.notify

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class EventStream[B](maxSize: Int) extends mutable.Buffer[Event[B]] {
  private val impl = new ListBuffer[Event[B]]

  override def apply(n: Int): Event[B] = impl(n)

  override def update(n: Int, newelem: Event[B]): Unit = impl(n) = newelem

  override def length: Int = impl.length

  override def +=(elem: Event[B]): this.type = {
    if (maxSize == size)
      impl.remove(0)
    this += (elem, impl.size)
  }


  def +=(elem: Event[B], pos: Int): this.type = {
    if (pos >= 0 && pos <= impl.size && impl(pos - 1).timemark < elem.timemark) {
      impl.insert(pos, elem)
      this
    } else
      this += (elem, pos - 1)
  }

  override def clear(): Unit = impl.clear()

  override def +=:(elem: Event[B]): this.type = throw new UnsupportedOperationException

  override def insertAll(n: Int, elems: Traversable[Event[B]]): Unit = impl.insertAll(n, elems)

  override def remove(n: Int): Event[B] = impl.remove(n)

  override def iterator: Iterator[Event[B]] = impl.iterator
}