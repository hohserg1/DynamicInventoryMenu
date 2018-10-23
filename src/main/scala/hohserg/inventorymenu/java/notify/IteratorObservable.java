package hohserg.inventorymenu.java.notify;

import java.util.Iterator;

public class IteratorObservable<A> extends JavaObservable implements Iterator<A> {
    private Iterator<A> iterator;

    public IteratorObservable(Iterator<A> iterator) {
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
    public void remove() {
        iterator.remove();
        notifyAllObjects();
    }
}
