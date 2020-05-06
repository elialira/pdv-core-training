package br.com.totvs.raas.product.commad.test.steps.event;

import br.com.totvs.raas.core.test.context.Cleaned;
import br.com.totvs.raas.product.commad.test.port.adapter.shared.Wait;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class EventMessageContext implements Cleaned {

    @Autowired
    private AggregateContext context;

    @Getter
    private List<EventMessage> events;

    public EventMessageContext() {
        this.events = new ArrayList<>();
    }

    public void add(EventMessage event) {
        events.add(event);
    }

    public Map<String, Object> findBy(String aggregateId,
                                      String type) {
        Long version = context.getNotified().next(aggregateId);

        return Wait.notNull(() -> events.stream()
                .filter(event -> event.equals(aggregateId, type, version))
                .map(EventMessage::getPayload)
                .findFirst()
                .orElse(null));
    }

    @Override
    public void clear() {
        events.clear();
    }

}
