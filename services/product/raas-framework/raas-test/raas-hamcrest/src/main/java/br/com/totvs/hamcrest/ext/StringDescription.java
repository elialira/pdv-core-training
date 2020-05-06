package br.com.totvs.hamcrest.ext;

import java.io.IOException;

public class StringDescription extends BaseDescription {

    private final Appendable out;

    public StringDescription() {
        this(new StringBuilder());
    }

    public StringDescription(Appendable out) {
        this.out = out;
    }

    public boolean hasValue() {
        return !out.toString()
                .isEmpty();
    }

    @Override
    protected void append(String value) {
        try {
            out.append(value);
        } catch (IOException cause) {
            throw new RuntimeException("Could not write description", cause);
        }
    }

    @Override
    protected void append(char value) {
        try {
            out.append(value);
        } catch (IOException cause) {
            throw new RuntimeException("Could not write description", cause);
        }
    }

    @Override
    public String toString() {
        return out.toString();
    }
}
