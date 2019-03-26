package hohserg.inventorymenu.notify

trait AbleNotify[-I] {
  def notify(event: Event[I]): Unit

}