package br.com.totvs.raas.web.interceptor.translator;

import java.util.Collection;

public interface ErrorTranslator {

    default boolean noError() {
        return false;
    }

    String toMessage();

    String toCode();

    default Collection<Object> getErrors() {
        return null;
    }

}
