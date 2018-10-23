package hohserg.inventorymenu.java.notify;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;

public class SetObservable<A> extends CollectionObservable<A> implements Set<A> {
    public SetObservable(Set<A> set) {
        super(set);
    }
}
