package hohserg.inventorymenu.notify

trait AbleNotify[-A] {
  private[inventorymenu] def notify(sender: Observable[A], event: Event[A]): Unit
}

