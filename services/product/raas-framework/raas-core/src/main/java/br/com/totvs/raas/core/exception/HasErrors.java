package br.com.totvs.raas.core.exception;

import java.util.Collection;

public interface HasErrors<T> {

    Collection<T> getErrors();

}
