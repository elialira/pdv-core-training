package br.com.totvs.hamcrest.ext.matcher;

import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;
import org.hamcrest.Matcher;

import java.util.Arrays;
import java.util.Collection;


public class AllOf<T> extends DiagnosingMatcher<T> {

    private final Collection<Matcher<? extends T>> matchers;

    public AllOf(Collection<Matcher<? extends T>> matchers) {
        this.matchers = matchers;
    }

    @Override
    public boolean matches(Object o, Description mismatch) {
        boolean flag = true;
        for (Matcher<? extends T> matcher : matchers) {
            if (!matcher.matches(o)) {
                mismatch.appendDescriptionOf(matcher);
                matcher.describeMismatch(o, mismatch);
                flag = false;
            }
        }
        return flag;
    }

    @Override
    public void describeTo(Description description) {
        description.appendList("(", " " + "and" + " ", ")", matchers);
    }

    public static <T> Matcher<? super T> allOf(Collection<Matcher<? extends T>> matchers) {
        return new AllOf<T>(matchers);
    }

    public static <T> Matcher<? super T> allOf(Matcher<? super T>... matchers) {
        return allOf(Arrays.asList(matchers));
    }


}
