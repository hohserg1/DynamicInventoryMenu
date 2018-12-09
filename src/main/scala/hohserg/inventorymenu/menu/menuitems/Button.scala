package hohserg.inventorymenu.menu.menuitems

import hohserg.inventorymenu.menu._
import hohserg.inventorymenu.menu.menuitems.Clickable.{ClickHandler, PartialClickHandler}
import hohserg.inventorymenu.menu.utils._
import org.bukkit.inventory.ItemStack

case class Button(menu: Menu, x: Int, y: Int, source: DataSource[ItemStack], clickHandler: PartialClickHandler) extends Clickable

object Button {

  private case class Builder[Source <: Flag, CH <: Flag](x: Int, y: Int, source1: DataSource[ItemStack], clickHandler1: PartialClickHandler) {
    def source(source2: DataSource[ItemStack]): Builder[T, CH] = copy(source1 = source2)

    def source(source2: ItemStack): Builder[T, CH] = copy(source1 = ConstSource(source2))

    def clickHandler(clickHandler2: PartialClickHandler): Builder[Source, T] = copy(clickHandler1 = clickHandler2)

    def clickHandler(clickHandler2: ClickHandler): Builder[Source, T] = copy(clickHandler1 = {
      case ce => clickHandler2(ce)
    })

    def build(implicit ev1: Source =:= T, ev2: CH =:= T): Menu => MenuItem = new Button(_, x, y, source1, clickHandler1)
  }

  def apply(x: Int, y: Int): Builder[F, F] =
    Builder(x, y, null, null)
}
