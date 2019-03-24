package hohserg.inventorymenu.notify

import org.bukkit.Bukkit

import scala.collection.mutable

trait Observable[+B] {
  protected def currentTime(): Long = Bukkit.getWorlds.get(0).getFullTime

  private[notify] var canNotify = true

  def subscribe(consumer: AbleNotify[B]): Unit

  def zip[C](c: Observable[C]): Observable[(B, C)]

  def map[C](f: B => C): Observable[C]

  def merge[C <: A, A >: B](b: Observable[C]): Observable[A]
}

object Observable {
  def join[A](e: A*): Observable[A] = ???

}

trait ObservableImpl[B] extends Observable[B] {
  protected val listeners = new mutable.HashSet[AbleNotify[B]]

  protected def sendToAll(newEvent: Event[B]): Unit = {
    if (canNotify)
      listeners.foreach { consumer => consumer.notify(this, newEvent) }
  }

  protected def sendToAll(newEvent: B): Unit = {
    sendToAll(Event(newEvent, currentTime()))
  }

  override def subscribe(consumer: AbleNotify[B]): Unit = {
    listeners += consumer
  }

}
