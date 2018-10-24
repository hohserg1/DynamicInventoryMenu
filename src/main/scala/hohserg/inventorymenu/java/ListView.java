package hohserg.inventorymenu.java;

import hohserg.inventorymenu.menu.DataSource;
import hohserg.inventorymenu.menu.Menu;
import hohserg.inventorymenu.menu.menuitems.Decoration;
import hohserg.inventorymenu.menu.menuitems.MenuItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.collection.JavaConverters;
import scala.collection.TraversableOnce;

import java.util.Arrays;

public class ListView extends hohserg.inventorymenu.menu.ListView{
    public ListView(Player player, String name, int height,
                    TraversableOnce collection,
                    Function1 visualize,
                    Area area,
                    ItemStack borderFiller,
                    Function3<Object, Object, DataSource<ItemStack>,Function1<Menu, Decoration>> buttonFactory) {
        super(player, name, height, collection, visualize, area, borderFiller, buttonFactory);
    }
    public static Function2<Player, String, ListView> apply(int height,
                                                            TraversableOnce collection,
                                                            Function1 visualize,
                                                            Area area,
                                                            ItemStack borderFiller,
                                                            Function3<Object, Object, DataSource<ItemStack>,Function1<Menu, Decoration>> buttonFactory,
                                                            Function1<hohserg.inventorymenu.menu.Menu, MenuItem>... menuItems){
        return (player,title)->{
            ListView r=new ListView(player,title,height,collection,
                    visualize,
                    area,
                    borderFiller,
                    buttonFactory);
            r.$plus$plus$eq(JavaConverters.asScalaBuffer(Arrays.asList(menuItems)).toSeq());
            return r;
        };
    }
}
