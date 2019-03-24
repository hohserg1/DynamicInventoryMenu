package hohserg.inventorymenu.notify

case class Event[+A](value: A, timeMark: Long)

object Event {
  implicit def ordering[E]: Ordering[Event[E]] = (x: Event[E], y: Event[E]) =>
    implicitly[Ordering[Long]].compare(x.timeMark, y.timeMark)
}