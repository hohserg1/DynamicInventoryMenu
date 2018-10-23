package hohserg.inventorymenu.java.notify;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

public class ListObservable<A> extends CollectionObservable<A> implements List<A> {
    private List<A> list;

    public ListObservable(List<A> list) {
        super(list);
        this.list = list;
    }
    @Override
    public boolean addAll(int index, Collection<? extends A> c) {
        canNotify_$eq(false);
        boolean b = list.addAll(index, c);
        canNotify_$eq(true);
        notifyAllObjects();
        return b;
    }

    @Override
    public boolean equals(Object o) {
        return list.equals(o);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    @Override
    public A get(int index) {
        return list.get(index);
    }

    @Override
    public A set(int index, A element) {
        A set = list.set(index, element);
        notifyAllObjects();

        return set;
    }

    @Override
    public void add(int index, A element) {
        list.add(index, element);
        notifyAllObjects();

    }

    @Override
    public A remove(int index) {
        A remove = list.remove(index);
        notifyAllObjects();

        return remove;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<A> listIterator() {
        return new ListIteratorObservable<>(list.listIterator());
    }

    @Override
    public ListIterator<A> listIterator(int index) {
        return new ListIteratorObservable<>(list.listIterator(index));
    }

    @Override
    public List<A> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }
}
