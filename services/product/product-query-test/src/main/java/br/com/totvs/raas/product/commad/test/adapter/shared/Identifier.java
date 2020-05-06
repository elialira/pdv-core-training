package br.com.totvs.raas.product.commad.test.adapter.shared;

import java.util.UUID;

public class Identifier {

    public static String next() {
        return UUID.randomUUID().toString();
    }

}
