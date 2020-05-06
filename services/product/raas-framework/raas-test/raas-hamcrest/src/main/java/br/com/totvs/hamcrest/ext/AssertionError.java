package br.com.totvs.hamcrest.ext;

import java.util.List;

public class AssertionError extends java.lang.AssertionError {

    private final static String EXPECTED = "Expected: {";
    private final static String START_BUT = "} but: {";
    private final static String END_BUT = "}";

    private List<Violation> violations;
    private String actual;
    private String expected;

    public AssertionError(String expected, String actual) {
        this.expected = expected;
        this.actual = actual;
    }

    @Override
    public String getMessage() {
        return EXPECTED + expected + START_BUT
                + actual + END_BUT;
    }



}
