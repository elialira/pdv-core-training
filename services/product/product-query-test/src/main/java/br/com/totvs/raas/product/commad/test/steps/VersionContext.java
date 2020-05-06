package br.com.totvs.raas.product.commad.test.steps;

import br.com.totvs.raas.core.test.context.Cleaned;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

@Component
@Getter
public class VersionContext implements Cleaned {

    private static final Long PLUS_ONE = 1L;
    private static final Long COUNT_START = 0L;

    private Map<String, Long> values = new HashMap<>();

    public Long get(String id) {
        return version(id);
    }

    public Long getAndNext(String id) {
        Long version = version(id);
        Long nextValue = next(version);
        put(id, nextValue);
        return version;
    }

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

    }
}
