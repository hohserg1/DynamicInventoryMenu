package hohserg.inventorymenu.notify.subjects

import hohserg.inventorymenu.notify.Event

class Filter[A](p: A => Boolean) extends Identity[A] {

  override private def notify(event: Event[A]): Unit = {
    if (p(event.value)) {
      super.notify(event)
    }
  }
}
