package hohserg.inventorymenu.notify

import hohserg.inventorymenu.notify.subjects.Identity

case class Variable[A](private var value: A) extends Identity[A] {
  notify(Event(value))
  override val size: Int = 1

  def get: A = value

  def set(v: A): Unit = {
    value = v
    notify(Event(value))
  }
}
