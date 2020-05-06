package br.com.totvs.raas.core.test.context;


import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Success<E> extends Result<E> {

    public Success(E value) {
        super(Type.SUCCESS, value);
    }

    public Map<String, Object> toMap() {
        return getMapOptional()
                .orElse(null);
    }

    public List<Map<String, Object>> toList() {
        return Optional.ofNullable(value)
                .filter(List.class::isInstance)
                .map(List.class::cast)
                .orElse(null);
    }

}
