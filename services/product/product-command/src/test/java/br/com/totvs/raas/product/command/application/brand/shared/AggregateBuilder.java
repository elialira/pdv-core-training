package br.com.totvs.raas.product.command.application.brand.shared;

import br.com.totvs.raas.product.command.domain.model.brand.Brand;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class AggregateBuilder<T> {

    private Class<T> aggregateClass;
    private Map<String, Object> fields;

    private AggregateBuilder(Class<T> aggregateClass) {
        this.aggregateClass = aggregateClass;
        this.fields = new HashMap<>();
    }

    public AggregateBuilder<T> setField(String name, Object value) {
        fields.put(name, value);
        return this;
    }

    public T build() {
        try {
            Object aggregate = newInstance();
            setFields(aggregate);
            return (T) aggregate;
        } catch (Exception cause) {
            throw new IllegalArgumentException(cause);
        }
    }

    private void setFields(Object aggregate) {
        fields.entrySet().stream()
                .forEach(value -> {
                    Field field = ReflectionUtils.findField(aggregateClass, value.getKey());
                    ReflectionUtils.makeAccessible(field);
                    ReflectionUtils.setField(field, aggregate, value.getValue());
                });
    }

    @NotNull
    private Object newInstance() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<?> constructor = ReflectionUtils.accessibleConstructor(Brand.class);
        return constructor.newInstance();
    }

    public static <T> AggregateBuilder<T> builder(Class<T> aggregateClass) {
        return new AggregateBuilder<>(aggregateClass);
    }

}
