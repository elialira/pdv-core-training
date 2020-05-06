package br.com.totvs.hamcrest.ext;

import org.hamcrest.Description;
import org.hamcrest.SelfDescribing;


public class AssertionDescription implements Description {

    private final static String SEPARATOR = ", ";
    private final static String EXPECTED = "Expected: {";
    private final static String START_BUT = "} but: {";
    private final static String END_BUT = "}";

    private final StringDescription expected;
    private final StringDescription actual;

    public AssertionDescription() {
        this(new StringDescription(), new StringDescription());
    }

    public AssertionDescription(StringDescription expected, StringDescription actual) {
        this.expected = expected;
        this.actual = actual;
    }

    @Override
    public Description appendText(String text) {
        actual.appendText(text);
        return this;
    }

    @Override
    public Description appendDescriptionOf(SelfDescribing value) {
        appendSeparator();
        value.describeTo(expected);
        return this;
    }

    public void appendSeparator() {
        if (expected.hasValue()) {
            expected.appendText(SEPARATOR);
            actual.appendText(SEPARATOR);
        }
    }

    @Override
    public Description appendValue(Object value) {
        actual.appendValue(value);
        return this;
    }

    @Override
    public <T> Description appendValueList(String start, String separator, String end, T[] values) {
        actual.appendValueList(start, separator, end, values);
        return this;
    }

    @Override
    public <T> Description appendValueList(String start, String separator, String end, Iterable<T> values) {
        actual.appendValueList(start, separator, end, values);
        return this;
    }

    @Override
    public Description appendList(String start, String separator, String end, Iterable<? extends SelfDescribing> values) {
        actual.appendValueList(start, separator, end, values);
        return this;
    }

    public String getExpected() {
        return expected.toString();
    }

    public String getActual() {
        return actual.toString();
    }

    @Override
    public String toString() {
        return EXPECTED + getExpected() + START_BUT
                + getActual() + END_BUT;
    }

}
