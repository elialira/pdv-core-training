package br.com.totvs.raas.product.command.port.adapter.persistence;

import br.com.totvs.raas.product.command.AbstractTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EventStoreRepositoryTest extends AbstractTest {

    private EventStoreRepository eventStoreRepository;

    public EventStoreRepositoryTest() {
        this.eventStoreRepository = new EventStoreRepository();
    }

    @Test
    public void shouldGenereteId() {
        String id = eventStoreRepository.next();

        assertNotNull(id);
    }

}