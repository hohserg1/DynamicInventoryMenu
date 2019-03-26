package hohserg.inventorymenu.menu

import hohserg.inventorymenu.notify.{Observable, SubjectImpl}
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

case class SelectedSource[A](collection: Observable[TraversableOnce[A]],
                             index: Int, visualize: A => ItemStack) extends SubjectImpl[TraversableOnce[A], ItemStack] {
  override def transform: TraversableOnce[A] => ItemStack =
    i => Option(i.toList)
      .filter(_.size > index)
      .map(i => visualize(i(index)))
      .getOrElse(new ItemStack(Material.AIR))
}

case class ConstSource(itemStack: ItemStack) extends SubjectImpl[Nothing, ItemStack] {
  override def isHot: Boolean = false

  override def transform: Unit => ItemStack = _ => itemStack
}
