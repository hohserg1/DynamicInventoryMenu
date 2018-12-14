package hohserg.inventorymenu.java;

import hohserg.inventorymenu.java.notify.ListObservable;
import hohserg.inventorymenu.menu.menuitems.Button;
import hohserg.inventorymenu.menu.menuitems.Clickable;
import hohserg.inventorymenu.menu.menuitems.ImplicitUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import scala.Function1;
import scala.Function3;

import java.util.ArrayList;

import static hohserg.inventorymenu.menu.menuitems.ImplicitUtils.clickHandler2partialClickHandler;
import static hohserg.inventorymenu.menu.menuitems.ImplicitUtils.stack2source;

public class JavaTest {

    MenuFactory<Menu> menu1 = Menu.applyOrCreate(Menu.apply("Test", 5,
            Button.apply(0, 0,
                    stack2source(new ItemStack(Material.APPLE)),
                    clickHandler2partialClickHandler(clickEvent -> {
                        //click
                        return null;
                    })
            )));
    MenuFactory<ListView> menu2=Menu.applyOrCreate(ListView.apply("Test",5,
            new ListObservable<>(new ArrayList<String>()),
            null,
            null,
            new ItemStack(Material.APPLE)
            ,null));

    public JavaTest() {
        //menu1.apply().open();
    }
}
