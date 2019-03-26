package hohserg.inventorymenu.notify

trait Subject[-I, +O] extends Observable[O] {

  def transform: I => O

  def size: Int = 10

  def subscribe(subscriber: AbleNotify[O]): Unit

  private[notify] def subscribe(subscriber: Subject[O, _]): Unit

  private[inventorymenu] def notify(event: Event[I]): Unit

}

