package hohserg.inventorymenu.notify

import scala.collection.mutable

trait OpenObservable[A, B] extends mutable.OpenHashMap[A, B] with ObservableImpl[mutable.OpenHashMap[A, B]] {
  override def ++=(xs: TraversableOnce[(A, B)]): OpenObservable.this.type = {
    canNotify = false
    val r = super.++=(xs)
    canNotify = true
    sendToAll(this)
    r
  }

  override def --=(xs: TraversableOnce[A]): OpenObservable.this.type = {
    canNotify = false
    val r = super.--=(xs)
    canNotify = true
    sendToAll(this)
    r
  }

  override def put(key: A, value: B): Option[B] = {
    val r = super.put(key, value)
    sendToAll(this)
    r
  }

  override def remove(key: A): Option[B] = {
    val r = super.remove(key)
    sendToAll(this)
    r
  }

}
