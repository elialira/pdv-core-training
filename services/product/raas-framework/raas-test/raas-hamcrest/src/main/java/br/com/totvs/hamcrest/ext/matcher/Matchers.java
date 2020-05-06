package br.com.totvs.hamcrest.ext.matcher;

import org.hamcrest.Matcher;

import java.util.Map;


public class Matchers {

    public static <T> Matcher<? super T> allOf(Matcher<? super T>... matchers) {
        return AllOf.allOf(matchers);
    }

    public static <K,V> Matcher<Map<? super K, ? super V>> hasEntry(Matcher<? super K> keyMatcher, Matcher<? super V> valueMatcher) {
        return IsMapContaining.hasEntry(keyMatcher, valueMatcher);
    }

    public static <K,V> Matcher<Map<? super K,? super V>> hasEntry(K key, Matcher<? super V> valueMatcher) {
        return IsMapContaining.hasEntry(key, valueMatcher);
    }

    public static <K,V> Matcher<Map<? super K,? super V>> hasEntry(K key, V value) {
        return IsMapContaining.hasEntry(key, value);
    }

}
