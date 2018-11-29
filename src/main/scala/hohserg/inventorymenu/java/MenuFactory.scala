package hohserg.inventorymenu.java

import org.bukkit.entity.Player

abstract class MenuFactory[A] extends (Player => A) {

}
