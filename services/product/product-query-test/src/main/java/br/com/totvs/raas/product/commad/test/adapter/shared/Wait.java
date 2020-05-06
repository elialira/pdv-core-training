package br.com.totvs.raas.product.commad.test.adapter.shared;

import java.util.function.Supplier;

import static java.util.Objects.nonNull;

public class Wait {

    public static <R> R notNull(Supplier<R> result) {
        long before = System.currentTimeMillis();
        while (true) {
            R value = result.get();
            if (nonNull(value)) {
                return value;
            }
            if ((System.currentTimeMillis() - before) > 3 * 1000) {
                return null;
            }
        }
    }

}
