package hohserg.inventorymenu.java;

import hohserg.inventorymenu.java.notify.CollectionObservable;
import hohserg.inventorymenu.java.notify.MapObservable;
import hohserg.inventorymenu.menu.DataSource;
import hohserg.inventorymenu.menu.Menu;
import hohserg.inventorymenu.menu.menuitems.Decoration;
import hohserg.inventorymenu.menu.menuitems.MenuItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Tuple2;
import scala.collection.TraversableOnce;

public class ListView<A> extends hohserg.inventorymenu.menu.ListView {
    public ListView(String id, Player player, String name, int height,
                    CollectionObservable<A> collection,
                    Function1<A, ItemStack> visualize,
                    Area area,
                    ItemStack borderFiller,
                    Function3<Integer, Integer, DataSource<ItemStack>, Function1<Menu, MenuItem>> buttonFactory) {
        super(id, player, name, height, toScala(collection), visualize, area, borderFiller, buttonFactory);
    }

    public <K, V> ListView(String id, Player player, String name, int height,
                           MapObservable<K, V> collection,
                           Function1<A, ItemStack> visualize,
                           Area area,
                           ItemStack borderFiller,
                           Function3<Integer, Integer, DataSource<ItemStack>, Function1<Menu, MenuItem>> buttonFactory) {
        super(id, player, name, height, toScala(collection), visualize, area, borderFiller, buttonFactory);
    }

    private static <K, V> CollectionWithTraitConversions.JMapWrapper<K, V> toScala(MapObservable<K, V> collection) {
        return CollectionWithTraitConversions.convert(collection);
    }

    private static <A> CollectionWithTraitConversions.JTraversableOnceWrapper<A> toScala(CollectionObservable<A> collection) {
        return CollectionWithTraitConversions.convert(collection);
    }

    public static <A> Function2<String, Player, ListView> apply(String title,
                                                                int height,
                                                                CollectionObservable<A> collection,
                                                                Function1<A, ItemStack> visualize,
                                                                Area area,
                                                                ItemStack borderFiller,
                                                                Function3<Integer, Integer, DataSource<ItemStack>, Function1<Menu, MenuItem>> buttonFactory,
                                                                Function1<hohserg.inventorymenu.menu.Menu, MenuItem>... menuItems) {
        return (String id, Player player) -> {
            ListView<A> r = new ListView<>(id, player, title, height, collection,
                    visualize,
                    area,
                    borderFiller,
                    buttonFactory);
            r.addAll(menuItems);
            return r;
        };
    }

    public static <K, V> Function2<String, Player, ListView> apply(String title,
                                                                   int height,
                                                                   MapObservable<K, V> collection,
                                                                   Function1<Tuple2<K, V>, ItemStack> visualize,
                                                                   Area area,
                                                                   ItemStack borderFiller,
                                                                   Function3<Integer, Integer, DataSource<ItemStack>, Function1<Menu, MenuItem>> buttonFactory,
                                                                   Function1<hohserg.inventorymenu.menu.Menu, MenuItem>... menuItems) {
        return (String id, Player player) -> {
            ListView<Tuple2<K, V>> r = new ListView<>(id, player, title, height, collection,
                    visualize,
                    area,
                    borderFiller,
                    buttonFactory);
            r.addAll(menuItems);
            return r;
        };
    }
}
