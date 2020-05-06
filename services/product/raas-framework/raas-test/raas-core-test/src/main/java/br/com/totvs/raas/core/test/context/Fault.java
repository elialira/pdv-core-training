package br.com.totvs.raas.core.test.context;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Fault<E> extends Result<E> {

    public Fault(E value) {
        super(Type.FAULT, value);
    }

    public List<String> getMessages() {
        if (hasErrors()) {
            return getDetailMessages();
        }
        return getMessage();
    }

    private boolean hasErrors() {
        return getMapOptional()
                .map(result -> result.get("errors"))
                .isPresent();
    }

    private List<String> getDetailMessages() {
        return (List<String>) getMapOptional().map(result -> result.get("errors"))
                    .filter(ArrayList.class::isInstance)
                    .map(ArrayList.class::cast)
                    .orElseGet(ArrayList::new)
                    .stream()
                    .filter(Map.class::isInstance)
                    .map(Map.class::cast)
                    .map(detail -> ((Map) detail).get("message"))
                    .map(Object::toString)
                    .collect(Collectors.toList());
    }

    private List<String> getMessage() {
        return getMapOptional()
                .map(result -> result.get("message"))
                .map(Object::toString)
                .map(Arrays::asList)
                .orElseGet(ArrayList::new);
    }

}
