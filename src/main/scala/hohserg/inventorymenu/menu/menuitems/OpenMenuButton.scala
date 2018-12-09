package hohserg.inventorymenu.menu.menuitems

import hohserg.inventorymenu.menu._
import hohserg.inventorymenu.menu.menuitems.Clickable.PartialClickHandler
import hohserg.inventorymenu.menu.utils.{F, Flag, T}
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

case class OpenMenuButton(menu: Menu, x: Int, y: Int, source: DataSource[ItemStack], openMenuOnClick: Player => Menu) extends Clickable {
  override def clickHandler: PartialClickHandler = {case ce => openMenuOnClick(ce.player).open()}
}

object OpenMenuButton {

  private case class Builder[Source <: Flag](x: Int, y: Int, source1: DataSource[ItemStack], openMenuOnClick: Player => Menu) {
    def source(source2: DataSource[ItemStack]): Builder[T] = copy(source1 = source2)

    def source(source2: ItemStack): Builder[T] = copy(source1 = ConstSource(source2))

    def build(implicit ev1: Source =:= T): Menu => MenuItem = new OpenMenuButton(_, x, y, source1, openMenuOnClick)
  }

  def apply(x: Int, y: Int, openMenuOnClick: Player => Menu): Builder[F] =
    Builder(x, y, null, openMenuOnClick)
}
