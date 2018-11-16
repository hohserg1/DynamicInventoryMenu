package hohserg.inventorymenu.menu.menuitems

import hohserg.inventorymenu.menu.{DataSource, Menu, VariableSource}
import hohserg.inventorymenu.notify.Observable
import org.bukkit.inventory.ItemStack

case class CheckBox(menu: Menu, x: Int, y: Int, on: DataSource[ItemStack], off: DataSource[ItemStack], defaultState: Boolean = true) extends Clickable {
  private[this] var _state: Boolean = defaultState

  private val observable = new Object with Observable

  private def state: Boolean = _state

  private def state_=(value: Boolean): Unit = {
    _state = value
    observable.notifyAllObjects()
  }

  def switchState(): Any = state = !state

  val clickHandler = (_, _) => switchState()

  override val source: DataSource[ItemStack] = VariableSource[Object](observable, _ => if (state) on.getItem else off.getItem)
  source.addNotified(this)
}
