package br.com.totvs.raas.core.test.context;

import lombok.ToString;

import java.util.Map;
import java.util.Optional;

@ToString
public abstract class Result<E> {

    enum Type {SUCCESS, FAULT}

    private Type type;
    protected E value;

    public Result(Type type, E value) {
        this.type = type;
        this.value = value;
    }

    public boolean isSuccessful() {
        return Type.SUCCESS.equals(type);
    }

    public boolean hasValue() {
        return value != null;
    }

    public E getValue() {
        return value;
    }

    public String getId() {
        return getMapOptional()
                .map(value -> value.get("id"))
                .map(String.class::cast)
                .get();
    }

    protected Optional<Map> getMapOptional() {
        return Optional.ofNullable(value)
                .filter(Map.class::isInstance)
                .map(Map.class::cast);
    }

}
