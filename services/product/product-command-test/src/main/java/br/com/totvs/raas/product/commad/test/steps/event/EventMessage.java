package br.com.totvs.raas.product.commad.test.steps.event;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Builder
@RequiredArgsConstructor
@Getter
public class EventMessage {

    private final String aggregateId;
    private final String type;
    private final Long version;
    private final Map<String, Object> payload;

    public boolean equals(String aggregateId, String type, Long version) {
        return isAggregateId(aggregateId)
                && isType(type)
                && isVersion(version);
    }

    private boolean isAggregateId(String aggregateId) {
        return this.aggregateId.equalsIgnoreCase(aggregateId);
    }

    private boolean isType(String type) {
        return this.type.equalsIgnoreCase(type);
    }

    private boolean isVersion(Long version) {
        return (this.version == null && version == null)
                || (this.version != null && this.version.equals(version));
    }



}
