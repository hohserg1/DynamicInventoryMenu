package hohserg.inventorymenu.notify

import hohserg.inventorymenu.notify.subjects._

import scala.collection.mutable

trait SubjectImpl[I, O] extends Subject[I, O] {

  private[notify] val stream = new EventStream[O](size)

  override def lastOption: Option[O] = stream.lastOption.map(_.value)

  override def startWith[O2 >: O](v: O2): Observable[O2] = {
    val r = new Identity[O2]
    r.notify(Event(v, 0))
    this subscribe r
    r
  }

  def map[O2](f: O => O2): Observable[O2] = {
    val r = new SubjectImpl[O, O2] {
      override val transform = f
    }
    this subscribe r
    r
  }

  override def zip[C](b: Observable[C]): Observable[(O, C)] = {
    val r = new Zip[O, C]
    this map (Left(_)) subscribe r
    b map (Right(_)) subscribe r
    r
  }

  override def merge[O2 <: C, C >: O](b: Observable[O2]): Observable[C] = {
    val r = new Identity[C]
    this subscribe r
    b subscribe r
    r
  }

  override def flatMap[O2](f: O => Observable[O2]): Observable[O2] =
    this.map(f).flatten


  def flatten[O2](implicit asObservable: O => Observable[O2]): Observable[O2] = {
    val r = new Flatten[O, O2]
    this map (Left(_)) subscribe r
    r
  }

  override def buffer(bufferingInterval: Long): Observable[Seq[O]] = {
    val r = new Buffer[O](bufferingInterval)
    this subscribe r
    r
  }

  override def filter(p: O => Boolean): Observable[O] = {
    val r = new Filter[O](p)
    this subscribe r
    r
  }

  def subscribe(subscriber: AbleNotify[O]): Unit = {
    listeners2 += subscriber
    if (!isHot)
      stream.foreach(subscriber.notify)
  }

  private[notify] def subscribe(subscriber: Subject[O, _]): Unit = {
    listeners += subscriber
    if (!isHot)
      stream.foreach(subscriber.notify)
  }

  private val listeners = new mutable.HashSet[Subject[O, _]]
  private val listeners2 = new mutable.HashSet[AbleNotify[O]]

  private[inventorymenu] def notify(event: Event[I]): Unit = {
    val newEvent = Event(transform(event.value), event.timemark)
    stream += newEvent
    sendToAll(newEvent)
  }


  protected def sendToAll(newEvent: Event[O]): Unit = {
    listeners.foreach { consumer => consumer.notify(newEvent) }
  }

  protected def sendToAll(newEvent: O): Unit = {
    sendToAll(Event(newEvent))
  }
}
