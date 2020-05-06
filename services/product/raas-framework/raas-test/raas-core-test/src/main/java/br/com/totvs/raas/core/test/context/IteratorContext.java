package br.com.totvs.raas.core.test.context;

import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Component
public class IteratorContext<E> implements Cleaned {

    private Iterator<E> items;

    public void setItems(List<E> items) {
        if (items != null) {
            this.items = items.iterator();
        }
    }

    public E next() {
        if (items != null && items.hasNext()) {
            return items.next();
        }
        return null;
    }

    @Override
    public void clear() {
        items = null;
    }
}
