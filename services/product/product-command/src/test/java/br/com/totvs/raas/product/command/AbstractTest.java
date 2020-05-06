package br.com.totvs.raas.product.command;

import java.util.Locale;

public abstract class AbstractTest {

    public AbstractTest() {
        Locale.setDefault(Locale.US);
    }
}
