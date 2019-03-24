package hohserg.inventorymenu.notify

import scala.collection.mutable.ListBuffer

class ZipStream[B, C] extends StreamImpl[Either[B, C], (B, C)] {
  override def transform: Either[B, C] => (B, C) = ???

  val lastB = new ListBuffer[B]
  val lastC = new ListBuffer[C]

  override def notify(sender: Observable[Either[B, C]], event: Event[Either[B, C]]): Unit = {
    event.value match {
      case Left(b) =>
        lastB += b
      case Right(c) =>
        lastC += c
    }
    lastB.headOption.flatMap(b => lastC.headOption.map((b, _))).foreach { i =>
      lastB.remove(0)
      lastC.remove(0)
      val newEvent = event.copy(value = i)
      stream += newEvent
      sendToAll(newEvent)
    }
  }
}
