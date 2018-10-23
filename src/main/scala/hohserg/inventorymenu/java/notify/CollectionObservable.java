package hohserg.inventorymenu.java.notify;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;

public class CollectionObservable<A>  extends JavaObservable implements Collection<A> {
    private Collection<A> collection;

    public CollectionObservable(Collection<A> collection) {
        this.collection = collection;
    }

    @Override
    public int size() {
        return collection.size();
    }

    @Override
    public boolean isEmpty() {
        return collection.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return collection.contains(o);
    }

    @Override
    public Iterator<A> iterator() {
        return new IteratorObservable<>(collection.iterator());
    }

    @Override
    public Object[] toArray() {
        return collection.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return collection.toArray(a);
    }

    @Override
    public boolean add(A a) {
        boolean add = collection.add(a);
        notifyAllObjects();
        return add;
    }

    @Override
    public boolean remove(Object o) {
        boolean remove = collection.remove(o);
        notifyAllObjects();
        return remove;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return collection.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends A> c) {
        canNotify_$eq(false);
        boolean b = collection.addAll(c);
        canNotify_$eq(true);
        notifyAllObjects();
        return b;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        canNotify_$eq(false);
        boolean b = collection.removeAll(c);
        canNotify_$eq(true);
        notifyAllObjects();

        return b;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        canNotify_$eq(false);
        boolean b = collection.retainAll(c);
        canNotify_$eq(true);
        notifyAllObjects();

        return b;
    }

    @Override
    public boolean removeIf(Predicate<? super A> filter) {
        canNotify_$eq(false);
        boolean b = collection.removeIf(filter);
        canNotify_$eq(true);
        notifyAllObjects();

        return b;
    }

    @Override
    public void clear() {
        canNotify_$eq(false);
        collection.clear();
        canNotify_$eq(true);
        notifyAllObjects();

    }
}
