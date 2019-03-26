package hohserg.inventorymenu.notify.collection

import hohserg.inventorymenu.notify.subjects.Identity

import scala.collection.generic.Shrinkable
import scala.collection.mutable

trait CollectionObservable[A] extends mutable.Builder[A, Iterable[A]] with Shrinkable[A] with Identity[TraversableOnce[A]] {
  this: TraversableOnce[A] =>
  override val size: Int = 0

  override def transform: TraversableOnce[A] => TraversableOnce[A] = identity

  private var canNotify = true

  override def ++=(xs: TraversableOnce[A]): CollectionObservable.this.type = {
    canNotify = false
    val r = super.++=(xs)
    canNotify = true
    sendToAll(this)
    r
  }

  override def --=(xs: TraversableOnce[A]): CollectionObservable.this.type = {
    canNotify = false
    val r = super.--=(xs)
    canNotify = true
    sendToAll(this)
    r
  }

  override def +=(elem: A): CollectionObservable.this.type = {
    val r = super.+=(elem)
    sendToAll(this)
    r
  }

  override def -=(elem: A): CollectionObservable.this.type = {
    val r = super.-=(elem)
    sendToAll(this)
    r
  }

  override def clear(): Unit = {
    canNotify = false
    super.clear()
    canNotify = true
    sendToAll(this)
  }

}
