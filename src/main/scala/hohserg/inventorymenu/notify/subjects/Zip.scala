package hohserg.inventorymenu.notify.subjects

import hohserg.inventorymenu.notify.SubjectImpl

class Zip[A, B] extends SubjectImpl[Either[A, B], (A, B)] {
  override def transform: Either[A, B] => (A, B) = ???

}
