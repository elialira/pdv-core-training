package br.com.totvs.hamcrest.ext;

import java.text.MessageFormat;


public class Violation {

    private static final String DEFAULT_PATTERN = "{0}={1}";

    private final Object name;
    private final Object value;

    public Violation(Object name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String toString(String pattern) {
        return MessageFormat.format(pattern, name, value);
    }

    @Override
    public String toString() {
        return toString(DEFAULT_PATTERN);
    }
}
