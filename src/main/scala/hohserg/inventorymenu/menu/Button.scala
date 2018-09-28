package hohserg.inventorymenu.menu
import hohserg.inventorymenu.menu.Menu.ClickHandler
import org.bukkit.inventory.ItemStack

private[menu] case class Button(menu: Menu, x: Int, y: Int, source: DataSource[ItemStack], clickHandler: ClickHandler) extends MenuItem

object Button {
  def apply(x: Int, y: Int, source: DataSource[ItemStack], clickHandler: ClickHandler): Menu=>Button = new Button(_, x, y, source, clickHandler)
  def apply(x: Int, y: Int, source: ItemStack, clickHandler: ClickHandler): Menu=>Button = new Button(_, x, y, ConstSource(source), clickHandler)
}
