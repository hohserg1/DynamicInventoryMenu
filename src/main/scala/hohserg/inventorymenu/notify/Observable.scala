package hohserg.inventorymenu.notify

trait Observable[+O] {
  def subscribe(subscriber: AbleNotify[O]): Unit

  private[notify] def subscribe(subscriber: Subject[O, _]): Unit

  def map[O2](f: O => O2): Observable[O2]

  def zip[C](b: Observable[C]): Observable[(O, C)]

  def merge[O2 <: C, C >: O](b: Observable[O2]): Observable[C]

  def flatMap[O2](f: O => Observable[O2]): Observable[O2]

  def flatten[O2](implicit asObservable: O => /*<:<!!!*/ Observable[O2]): Observable[O2]

  def buffer(bufferingInterval: Long): Observable[Seq[O]]

  def filter(p: O => Boolean): Observable[O]

}
