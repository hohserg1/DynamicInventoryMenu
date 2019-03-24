package hohserg.inventorymenu.notify

import org.bukkit.Bukkit

trait Stream[-I, +O]{
  protected def currentTime(): Long = Bukkit.getWorlds.get(0).getFullTime

  private[notify] var canNotify = true

  def subscribe(consumer: AbleNotify[O]): Unit

  def zip[C](c: Observable[C]): Observable[(O, C)]

  def map[C](f: O => C): Observable[C]

  def merge[C <: A, A >: O](b: Observable[C]): Observable[A]

}

trait StreamImpl[I, O] extends AbleNotify[I] with ObservableImpl[O] {

  def transform: I => O

  def size: Int = 10

  val stream = new EventStream[O](size)

  override def zip[C](c: Observable[C]): Observable[(O, C)] = {
    val r = new ZipStream[O, C]
    this.map(Left(_)).subscribe(r)
    c.map(Right(_)).subscribe(r)
    r
  }

  override def map[C](f: O => C): Observable[C] = {
    val r = new StreamImpl[O, C] {
      override val transform = f
    }
    subscribe(r)
    r
  }

  override def merge[C <: A, A >: O](b: Observable[C]): Observable[A] = {
    val r = new IdentityStream[A] {
      override val size: Int = this.size + b.size

    }
    subscribe(r)
    b.subscribe(r)
    r
  }

  override private[inventorymenu] def notify(sender: Observable[I], event: Event[I]): Unit = {
    val newEvent = Event(transform(event.value), event.timeMark)
    stream += newEvent
    sendToAll(newEvent)
  }
}




