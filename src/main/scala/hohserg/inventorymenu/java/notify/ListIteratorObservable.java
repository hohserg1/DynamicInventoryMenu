package hohserg.inventorymenu.java.notify;

import java.util.Iterator;
import java.util.ListIterator;

public class ListIteratorObservable<A> extends JavaObservable implements ListIterator<A> {
    private ListIterator<A> iterator;

    public ListIteratorObservable(ListIterator<A> iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public A next() {
        return iterator.next();
    }

    @Override
    public boolean hasPrevious() {
        return iterator.hasPrevious();
    }

    @Override
    public A previous() {
        return iterator.previous();
    }

    @Override
    public int nextIndex() {
        return iterator.nextIndex();
    }

    @Override
    public int previousIndex() {
        return iterator.previousIndex();
    }

    @Override
    public void remove() {
        iterator.remove();
        notifyAllObjects();
    }

    @Override
    public void set(A a) {
        iterator.set(a);
        notifyAllObjects();
    }

    @Override
    public void add(A a) {
        iterator.add(a);
        notifyAllObjects();
    }
}
