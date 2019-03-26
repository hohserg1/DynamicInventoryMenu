package hohserg.inventorymenu.notify.subjects

import hohserg.inventorymenu.notify.{Event, SubjectImpl}

import scala.collection.mutable.ListBuffer

class Buffer[A](bufferingInterval: Long) extends SubjectImpl[A, Seq[A]] {
  override def transform: A => Seq[A] = ???

  private var buffer = new ListBuffer[Event[A]]
  val b = new ListBuffer[A]
  val c = b.result()

  override private[notify] def notify(event: Event[A]): Unit = {
    var needToClear = false
    if (buffer.nonEmpty) {
      if (event.timemark - buffer.last.timemark <= bufferingInterval)
        buffer += event
      else
        needToClear = true
    } else {
      buffer += event
    }

    val newEvent = Event(buffer.toList.map(_.value), buffer.last.timemark)
    stream += newEvent
    sendToAll(newEvent)

    if (needToClear) buffer.clear()
  }
}
