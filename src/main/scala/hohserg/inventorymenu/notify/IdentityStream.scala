package hohserg.inventorymenu.notify

class IdentityStream[A] extends StreamImpl[A, A] {
  override val transform: A => A = identity
}
