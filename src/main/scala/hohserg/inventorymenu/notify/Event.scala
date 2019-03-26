package hohserg.inventorymenu.notify

case class Event[+A](value: A, timemark: Long)