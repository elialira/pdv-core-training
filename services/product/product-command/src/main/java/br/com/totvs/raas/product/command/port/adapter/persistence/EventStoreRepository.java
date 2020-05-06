package br.com.totvs.raas.product.command.port.adapter.persistence;

import br.com.totvs.raas.product.command.domain.model.Identity;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class EventStoreRepository implements Identity {

    public String next() {
        return UUID.randomUUID().toString();
    }

}
