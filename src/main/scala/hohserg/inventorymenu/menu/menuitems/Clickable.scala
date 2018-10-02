package hohserg.inventorymenu.menu.menuitems

import hohserg.inventorymenu.menu.Menu.ClickHandler

trait Clickable extends MenuItem{
  def clickHandler: ClickHandler

}
