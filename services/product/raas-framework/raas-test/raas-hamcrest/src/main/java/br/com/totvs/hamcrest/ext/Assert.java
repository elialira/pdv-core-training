package br.com.totvs.hamcrest.ext;

import org.hamcrest.Matcher;


public class Assert {

    public static <T> void assertThat(T actual, Matcher<? super T> matcher) {
        assertThat("", actual, matcher);
    }

    public static <T> void assertThat(String reason, T actual, Matcher<? super T> matcher) {
        if (!matcher.matches(actual)) {
            AssertionDescription description = new AssertionDescription();
            matcher.describeMismatch(actual, description);

            throw new AssertionError(description.getExpected(), description.getActual());
        }
    }

    public static void assertThat(String reason, boolean assertion) {
        if(!assertion) {
            throw new java.lang.AssertionError(reason);
        }
    }



}
