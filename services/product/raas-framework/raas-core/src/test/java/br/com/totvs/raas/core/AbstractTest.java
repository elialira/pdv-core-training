package br.com.totvs.raas.core;

import java.util.Locale;

public abstract class AbstractTest {

    public AbstractTest() {
        Locale.setDefault(Locale.US);
    }
}
