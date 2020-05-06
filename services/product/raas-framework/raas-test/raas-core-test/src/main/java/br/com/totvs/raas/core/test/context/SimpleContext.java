package br.com.totvs.raas.core.test.context;

public class SimpleContext<E> implements Cleaned {

    private ThreadLocal<E> value;

    public SimpleContext() {
        this.value = new ThreadLocal<E>();
    }

    public E get() {
        return this.value.get();
    }

    public void set(E value) {
        if (value != null) {
            this.value.set(value);
        }
    }

    public void clear() {
        this.value.remove();
    }

}
