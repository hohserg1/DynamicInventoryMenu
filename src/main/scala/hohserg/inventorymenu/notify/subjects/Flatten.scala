package hohserg.inventorymenu.notify.subjects

import hohserg.inventorymenu.notify.{Event, Observable, SubjectImpl}

import scala.language.higherKinds

class Flatten[F, A](implicit asObservable: F => Observable[A]) extends SubjectImpl[Either[F, A], A] {
  override val transform: Either[F, A] => A = {
    case Right(v) => v
  }

  override private def notify(event: Event[Either[F, A]]): Unit = {
    event.value match {
      case Left(o) => o map (Right(_)) subscribe this
      case Right(_) => super.notify(event)
    }
  }
}
