package hohserg.inventorymenu.java;

import hohserg.inventorymenu.menu.menuitems.MenuItem;
import org.bukkit.entity.Player;
import scala.Function1;
import scala.Function2;
import scala.collection.JavaConverters;
import scala.collection.Seq;

import java.util.Arrays;

public class Menu extends hohserg.inventorymenu.menu.Menu {
    public Menu(Player player, String name, int height) {
        super(player, name, height);
    }

    public static Function2<Player, String, Menu> apply(int height, Function1<hohserg.inventorymenu.menu.Menu, MenuItem>... menuItems) {
        return (player, title) -> {
            Menu r = new Menu(player, title, height);
            r.$plus$plus$eq(JavaConverters.asScalaBuffer(Arrays.asList(menuItems)).toSeq());
            return r;
        };
    }

    public hohserg.inventorymenu.menu.Menu add(Function1<hohserg.inventorymenu.menu.Menu, MenuItem> button) {
        return super.$plus$eq(button);
    }

    public hohserg.inventorymenu.menu.Menu addAll(Seq<Function1<hohserg.inventorymenu.menu.Menu, MenuItem>> button) {
        return super.$plus$plus$eq(button);
    }
}