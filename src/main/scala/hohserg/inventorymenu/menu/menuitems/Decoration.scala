package hohserg.inventorymenu.menu.menuitems

import hohserg.inventorymenu.menu._
import hohserg.inventorymenu.menu.utils.{F, Flag, T}
import org.bukkit.inventory.ItemStack

case class Decoration(menu: Menu, x: Int, y: Int, source: DataSource[ItemStack]) extends MenuItem

object Decoration {

  private case class Builder[Source <: Flag](x: Int, y: Int, source1: DataSource[ItemStack]) {
    def source(source2: DataSource[ItemStack]): Builder[T] = copy(source1 = source2)

    def source(source2: ItemStack): Builder[T] = copy(source1 = ConstSource(source2))

    def build(implicit ev1: Source =:= T): Menu => MenuItem = new Decoration(_, x, y, source1)
  }

  def apply(x: Int, y: Int): Builder[F] =
    Builder(x, y, null)
}
