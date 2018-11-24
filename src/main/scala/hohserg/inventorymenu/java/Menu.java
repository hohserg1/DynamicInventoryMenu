package hohserg.inventorymenu.java;

import hohserg.inventorymenu.menu.menuitems.MenuItem;
import org.bukkit.entity.Player;
import scala.Function1;
import scala.Function2;
import scala.collection.JavaConverters;
import scala.collection.Seq;

import java.util.Arrays;

public class Menu extends hohserg.inventorymenu.menu.Menu {
    public Menu(String id, Player player, String name, int height) {
        super(id, player, name, height);
    }

    public static Function2<String, Player, Menu> apply(String title, int height, Function1<hohserg.inventorymenu.menu.Menu, MenuItem>... menuItems) {
        return (id, player) -> {
            Menu r = new Menu(id, player, title, height);
            r.addAll(menuItems);
            return r;
        };
    }
}
