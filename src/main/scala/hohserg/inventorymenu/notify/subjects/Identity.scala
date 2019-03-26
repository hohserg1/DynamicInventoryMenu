package hohserg.inventorymenu.notify.subjects

import hohserg.inventorymenu.notify.SubjectImpl

trait Identity[A] extends SubjectImpl[A, A] {
  override val transform: A => A = identity
}
