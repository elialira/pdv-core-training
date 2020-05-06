package br.com.totvs.raas.product.commad.test.steps.event;

import br.com.totvs.raas.core.test.context.Cleaned;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

@Component
@Getter
public class AggregateContext implements Cleaned {

    private Aggregate persisted = new Aggregate();
    private Aggregate notified = new Aggregate();

    public Long next(String id) {
        persisted.next(id);
        return notified.next(id);
    }

    @Override
    public void clear() {
        persisted.clear();
        notified.clear();
    }

    public static class Aggregate implements Cleaned {

        private static final Long PLUS_ONE = 1L;
        private static final Long COUNT_START = 0L;

        private Map<String, Long> values = new HashMap<>();

        public Long next(String id) {
            Long version = version(id);
            Long nextValue = next(version);
            put(id, nextValue);
            return nextValue;
        }

        private Long next(Long version) {
            if (nonNull(version)) {
                return version + PLUS_ONE;
            }
            return COUNT_START;
        }

        private void put(String id, Long version) {
            values.put(id, version);
        }

        private Long version(String id) {
            return values.get(id);
        }

        @Override
        public void clear() {
            values.clear();
        }
    }

}
