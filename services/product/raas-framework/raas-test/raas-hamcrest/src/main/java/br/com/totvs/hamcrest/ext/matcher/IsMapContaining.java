package br.com.totvs.hamcrest.ext.matcher;


import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Map;

import static org.hamcrest.core.IsEqual.equalTo;


public class IsMapContaining <K,V> extends TypeSafeMatcher<Map<? super K, ? super V>> {
    private final Matcher<? super K> keyMatcher;
    private final Matcher<? super V> valueMatcher;

    public IsMapContaining(Matcher<? super K> keyMatcher, Matcher<? super V> valueMatcher) {
        this.keyMatcher = keyMatcher;
        this.valueMatcher = valueMatcher;
    }

    @Override
    public boolean matchesSafely(Map<? super K, ? super V> map) {
        for (Map.Entry<? super K, ? super V> entry : map.entrySet()) {
            if (keyMatcher.matches(entry.getKey()) && valueMatcher.matches(entry.getValue())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void describeMismatchSafely(Map<? super K, ? super V> map, Description mismatchDescription) {
        String actual = map.entrySet().stream()
                .filter(entry -> keyMatcher.matches(entry.getKey()) && !valueMatcher.matches(entry.getValue()))
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .findFirst()
                .orElse("no property " + keyMatcher.toString());

        mismatchDescription.appendText(actual);
    }

    @Override
    public void describeTo(Description description) {
        description.appendDescriptionOf(keyMatcher)
                .appendText("=")
                .appendDescriptionOf(valueMatcher);
    }

    public static <K,V> Matcher<Map<? super K,? super V>> hasEntry(Matcher<? super K> keyMatcher, Matcher<? super V> valueMatcher) {
        return new IsMapContaining<K,V>(keyMatcher, valueMatcher);
    }

    public static <K,V> Matcher<Map<? super K,? super V>> hasEntry(K key, V value) {
        return new IsMapContaining<K,V>(equalTo(key), equalTo(value));
    }

    public static <K,V> Matcher<Map<? super K,? super V>> hasEntry(K key, Matcher<? super V> valueMatcher) {
        return new IsMapContaining<K,V>(equalTo(key), valueMatcher);
    }

}
