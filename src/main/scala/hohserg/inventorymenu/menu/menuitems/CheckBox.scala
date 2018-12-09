package hohserg.inventorymenu.menu.menuitems

import hohserg.inventorymenu.menu.menuitems.Clickable.{ClickHandler, PartialClickHandler}
import hohserg.inventorymenu.menu.utils.{F, Flag, T}
import hohserg.inventorymenu.menu.{ConstSource, DataSource, Menu, VariableSource}
import hohserg.inventorymenu.notify.Observable
import org.bukkit.inventory.ItemStack

case class CheckBox(menu: Menu, x: Int, y: Int, on: DataSource[ItemStack], off: DataSource[ItemStack], onChangeState: PartialClickHandler, defaultState: Boolean = true) extends Clickable {
  private[this] var _state: Boolean = defaultState

  private val observable = new Object with Observable

  def state: Boolean = _state

  private def state_=(value: Boolean): Unit = {
    _state = value
    observable.notifyAllObjects()
  }

  def switchState(): Any = state = !state

  val clickHandler: PartialClickHandler = {
    case ce =>
      switchState()
      onChangeState(ce)
  }

  override val source: DataSource[ItemStack] = VariableSource[Object](observable, _ => if (state) on.getItem else off.getItem)
  source.addNotified(this)
}

object CheckBox {

  private case class Builder[Source1 <: Flag, Source2 <: Flag, CH <: Flag](x: Int, y: Int, on1: DataSource[ItemStack], off1: DataSource[ItemStack], clickHandler1: PartialClickHandler) {
    def on(source2: DataSource[ItemStack]): Builder[T, Source2, CH] = copy(on1 = source2)

    def on(source2: ItemStack): Builder[T, Source2, CH] = copy(on1 = ConstSource(source2))

    def off(source2: DataSource[ItemStack]): Builder[Source1, T, CH] = copy(off1 = source2)

    def off(source2: ItemStack): Builder[Source1, T, CH] = copy(off1 = ConstSource(source2))

    def clickHandler(clickHandler2: PartialClickHandler): Builder[Source1, Source2, T] = copy(clickHandler1 = clickHandler2)

    def clickHandler(clickHandler2: ClickHandler): Builder[Source1, Source2, T] = copy(clickHandler1 = {
      case ce => clickHandler2(ce)
    })

    def build(implicit ev1: Source1 =:= T, ev2: Source2 =:= T, ev3: CH =:= T): Menu => MenuItem = new CheckBox(_, x, y, on1, off1, clickHandler1)
  }

  def apply(x: Int, y: Int): Builder[F, F, F] =
    Builder(x, y, null, null, null)

}
