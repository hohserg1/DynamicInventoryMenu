package hohserg.inventorymenu.notify

trait AbleNotify[-I] {
  private[notify] def notify(event: Event[I]): Unit

}