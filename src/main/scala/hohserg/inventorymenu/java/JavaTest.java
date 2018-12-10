package hohserg.inventorymenu.java;

import hohserg.inventorymenu.menu.menuitems.Button;
import hohserg.inventorymenu.menu.menuitems.Clickable;
import hohserg.inventorymenu.menu.menuitems.ImplicitUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import scala.Function1;
import scala.Function3;

public class JavaTest {

    MenuFactory<Menu> menu1 = Menu.applyOrCreate(Menu.apply("Test", 5,
            Button.apply(0, 0,
                    ImplicitUtils.stack2source(new ItemStack(Material.APPLE)),
                    ImplicitUtils.clickHandler2partialClickHandler(clickEvent -> {
                        //click
                        return null;
                    })
            )));
    //MenuFactory<ListView> menu2=Menu.applyOrCreate("Test",ListView.apply(5));

    public JavaTest() {
        //menu1.apply().open();
    }
}
