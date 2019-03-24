package hohserg.inventorymenu.notify

import scala.collection.generic.Shrinkable
import scala.collection.mutable

trait CollectionObservable[A] extends mutable.Builder[A, Iterable[A]] with Shrinkable[A] with ObservableImpl[CollectionObservable.this.type] {
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

  abstract override def +=(elem: A): CollectionObservable.this.type = {
    val r = super.+=(elem)
    sendToAll(this)
    r
  }

  abstract override def -=(elem: A): CollectionObservable.this.type = {
    val r = super.-=(elem)
    sendToAll(this)
    r
  }

}
