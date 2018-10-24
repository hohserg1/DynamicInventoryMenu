package hohserg.inventorymenu.java;

import hohserg.inventorymenu.menu.menuitems.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import scala.Function1;

public class JavaTest {
    Function1<Player, Menu> menu1=Menu.applyOrCreate("Test",Menu.apply(5, Button.apply(0,0,new ItemStack(Material.APPLE),
            (Player player)->{
                //click
                return null;
            }
    )));

    public JavaTest() {
        //menu1.apply().open();
    }
}
