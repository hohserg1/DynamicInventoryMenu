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
import scala.collection.JavaConverters;
import scala.collection.Seq;
import scala.collection.TraversableOnce;

import java.util.Arrays;

public class ListView<A> extends hohserg.inventorymenu.menu.ListView {
    public ListView(Player player, String name, int height,
                    CollectionObservable<A> collection,
                    Function1<A, ItemStack> visualize,
                    Area area,
                    ItemStack borderFiller,
                    Function3<Object, Object, DataSource<ItemStack>, Function1<Menu, Decoration>> buttonFactory) {
        super(player, name, height, toScala(collection), visualize, area, borderFiller, buttonFactory);
    }

    public <K, V> ListView(Player player, String name, int height,
                           MapObservable<K, V> collection,
                           Function1<A, ItemStack> visualize,
                           Area area,
                           ItemStack borderFiller,
                           Function3<Object, Object, DataSource<ItemStack>, Function1<Menu, Decoration>> buttonFactory) {
        super(player, name, height, toScala(collection), visualize, area, borderFiller, buttonFactory);
    }

    private static <K, V> TraversableOnce<Tuple2<K, V>> toScala(MapObservable<K, V> collection) {
        return CollectionWithTraitConversions.convert(collection);
    }

    private static <A> TraversableOnce<A> toScala(CollectionObservable<A> collection) {
        return CollectionWithTraitConversions.convert(collection);
    }

    public static <A> Function2<Player, String, ListView> apply(int height,
                                                                CollectionObservable<A> collection,
                                                                Function1<A, ItemStack> visualize,
                                                                Area area,
                                                                ItemStack borderFiller,
                                                                Function3<Object, Object, DataSource<ItemStack>, Function1<Menu, Decoration>> buttonFactory,
                                                                Function1<hohserg.inventorymenu.menu.Menu, MenuItem>... menuItems) {
        return (Player player, String title) -> {
            ListView<A> r = new ListView<>(player, title, height, collection,
                    visualize,
                    area,
                    borderFiller,
                    buttonFactory);
            r.$plus$plus$eq(JavaConverters.asScalaBuffer(Arrays.asList(menuItems)).toSeq());
            return r;
        };
    }

    public static <K, V> Function2<Player, String, ListView> apply(int height,
                                                                   MapObservable<K, V> collection,
                                                                   Function1<Tuple2<K, V>, ItemStack> visualize,
                                                                   Area area,
                                                                   ItemStack borderFiller,
                                                                   Function3<Object, Object, DataSource<ItemStack>, Function1<Menu, Decoration>> buttonFactory,
                                                                   Function1<hohserg.inventorymenu.menu.Menu, MenuItem>... menuItems) {
        return (Player player, String title) -> {
            ListView<Tuple2<K, V>> r = new ListView<>(player, title, height, collection,
                    visualize,
                    area,
                    borderFiller,
                    buttonFactory);
            r.$plus$plus$eq(JavaConverters.asScalaBuffer(Arrays.asList(menuItems)).toSeq());
            return r;
        };
    }

    public Menu add(Function1<Menu, MenuItem> button) {
        return super.$plus$eq(button);
    }

    public Menu addAll(Seq<Function1<Menu, MenuItem>> button) {
        return super.$plus$plus$eq(button);
    }
}
