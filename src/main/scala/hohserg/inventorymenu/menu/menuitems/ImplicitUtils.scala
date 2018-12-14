package hohserg.inventorymenu.menu.menuitems

import hohserg.inventorymenu.menu.menuitems.Clickable.{ClickHandler, PartialClickHandler}
import hohserg.inventorymenu.menu.{ConstSource, DataSource}
import org.bukkit.inventory.ItemStack

import scala.language.implicitConversions

object ImplicitUtils {
  implicit def stack2source(itemStack: ItemStack): DataSource[ItemStack] = ConstSource(itemStack)

  implicit def clickHandler2partialClickHandler(clickHandler: ClickHandler): PartialClickHandler = {
    case ce => clickHandler(ce)
  }

}
