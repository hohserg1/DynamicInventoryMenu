# DynamicInventoryMenu
Powerful api for create bukkit ui

###Development is contious


##Usage
*add library to you project

*create menu fabric(partial function)
val menu: Player => Menu = new Menu(_, "Title", size=45)

*add buttons and decorations
val menu: Player => Menu = new Menu(_, "Title", size=45)
  .addDecoration(new ItemStack(Material.APPLE), 1, 1)
  
*when you need to open menu
menu(player).open()

###todo...



