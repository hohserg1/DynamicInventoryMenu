package hohserg.inventorymenu.java.notify;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

public class MapObservable<K, V> extends JavaObservable implements Map<K, V> {
    Map<K, V> map;

    public MapObservable(Map<K, V> map) {
        this.map = map;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        V put = map.put(key, value);
        notifyAllObjects();
        return put;
    }

    @Override
    public V remove(Object key) {
        V remove = map.remove(key);
        if(remove!=null)
            notifyAllObjects();
        return remove;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        canNotify_$eq(false);
        map.putAll(m);
        canNotify_$eq(true);
        notifyAllObjects();
    }

    @Override
    public void clear() {
        canNotify_$eq(false);
        map.clear();
        canNotify_$eq(true);
        notifyAllObjects();
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        canNotify_$eq(false);
        map.replaceAll(function);
        canNotify_$eq(true);
        notifyAllObjects();
    }
}
