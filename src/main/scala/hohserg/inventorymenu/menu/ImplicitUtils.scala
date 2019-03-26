package hohserg.inventorymenu.menu

import hohserg.inventorymenu.notify.{AbleNotify, Event, Observable}
import org.bukkit.inventory.ItemStack

object ImplicitUtils {
  implicit def stack2source(itemStack: ItemStack): Observable[ItemStack] = Observable.just(itemStack)

  implicit def function2AbleNotify[A](f: A => Unit): AbleNotify[A] = (event: Event[A]) => f(event.value)

}
