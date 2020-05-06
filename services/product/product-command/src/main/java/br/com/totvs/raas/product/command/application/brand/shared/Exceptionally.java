package br.com.totvs.raas.product.command.application.brand.shared;

import br.com.totvs.raas.core.exception.BusinessException;
import org.axonframework.modelling.command.AggregateNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Exceptionally<T> implements Function<Throwable, T> {

    private final List<Consumer<Throwable>> consumers;

    protected Exceptionally(List<Consumer<Throwable>> consumers) {
        this.consumers = consumers;
    }

    @Override
    public T apply(Throwable cause) {
        consumers.stream()
                .forEach(consumer -> consumer.accept(cause));

        if (cause instanceof RuntimeException) {
            throw (RuntimeException) cause;
        }

        throw new RuntimeException(cause);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final List<Consumer<Throwable>> consumers;

        private Builder() {
            this.consumers = new ArrayList<>();
        }

        public Builder notFound(Supplier<BusinessException> exception) {
            consumers.add(cause -> {
                if (cause instanceof AggregateNotFoundException) {
                    throw exception.get();
                }
            });
            return this;
        }

        public Exceptionally build() {
            return new Exceptionally(consumers);
        }
    }

}
