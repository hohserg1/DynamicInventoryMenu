package hohserg.inventorymenu.menu.menuitems

import hohserg.inventorymenu.menu.Menu
import hohserg.inventorymenu.menu.menuitems.Clickable.ClickEvent
import hohserg.inventorymenu.notify.{Observable, Variable}
import org.bukkit.inventory.ItemStack

case class CheckBox(menu: Menu, x: Int, y: Int, on: Observable[ItemStack], off: Observable[ItemStack], onChangeState: Observable[ClickEvent] => Unit, defaultState: Boolean) extends Clickable {
  val state = Variable[Boolean](defaultState)

  def switchState(): Any = state.set(!state.get)

  override def source: Observable[ItemStack] = on.zip(off).zip(state).map { case ((on, off), state) => if (state) on else off }

  override def subscribeOnClickEvents: Observable[ClickEvent] => Unit = _.subscribe(_ => switchState())
}

object CheckBox {
  def apply(x: Int, y: Int, on: Observable[ItemStack], off: Observable[ItemStack], onChangeState: Observable[ClickEvent] => Unit, defaultState: Boolean = true): Menu => MenuItem =
    new CheckBox(_, x, y, on, off, onChangeState, defaultState)

}
