package br.com.totvs.raas.product.command.domain.model.brand;

import br.com.totvs.raas.core.validator.ValidatorBuilder;
import br.com.totvs.raas.product.command.domain.command.BrandCommand;
import br.com.totvs.raas.product.command.domain.command.ChangeBrandCommand;
import br.com.totvs.raas.product.command.domain.command.CreateBrandCommand;
import br.com.totvs.raas.product.command.domain.command.DeleteBrandCommand;
import br.com.totvs.raas.product.command.domain.model.tenant.Tenant;
import br.com.totvs.raas.product.common.event.BrandChangedEvent;
import br.com.totvs.raas.product.common.event.BrandCreatedEvent;
import br.com.totvs.raas.product.common.event.BrandDeletedEvent;
import br.com.totvs.raas.product.common.event.BrandEvent;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;

import static java.util.Objects.nonNull;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import static org.axonframework.modelling.command.AggregateLifecycle.markDeleted;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Aggregate
public class Brand {

    interface Constants {
        String ID_NOT_EMPTY = "brand.id.not_empty";
        String NAME_NOT_EMPTY = "brand.name.not_empty";
        String ACTIVATED_NOT_NULL = "brand.activated.not_null";
        String TENANT_NOT_EMPTY = "brand.tenant.not_empty";
    }

    @Id
    @AggregateIdentifier
    private String id;
    private String name;
    @JsonUnwrapped
    @Embedded
    private Tenant tenant;

    @CommandHandler
    public Brand(CreateBrandCommand command) {
        validate(command);

        apply(BrandCreatedEvent.builder()
                .id(command.getId())
                .name(command.getName())
                .activated(command.getActivated())
                .tenantId(command.getTenantId())
                .build());
    }

    @CommandHandler
    public void handle(ChangeBrandCommand command) {
        validate(command);

        apply(BrandChangedEvent.builder()
                .id(command.getId())
                .name(command.getName())
                .activated(command.getActivated())
                .tenantId(command.getTenantId())
                .version(command.getVersion())
                .build());
    }

    private void validate(BrandCommand command) {
        ValidatorBuilder.builder()
                .hasLength(Constants.ID_NOT_EMPTY, command.getId())
                .hasLength(Constants.NAME_NOT_EMPTY, command.getName())
                .notNull(Constants.ACTIVATED_NOT_NULL, command.getActivated())
                .hasLength(Constants.TENANT_NOT_EMPTY, command.getTenantId())
                .build()
                .validate(BrandViolationException::new);
    }

    @CommandHandler
    public void handle(DeleteBrandCommand command) {
        ValidatorBuilder.builder()
                .hasLength(Constants.ID_NOT_EMPTY, command.getId())
                .hasLength(Constants.TENANT_NOT_EMPTY, command.getTenantId())
                .build()
                .validate(BrandViolationException::new);

        apply(BrandDeletedEvent.builder()
                .id(command.getId())
                .tenantId(command.getTenant().getId())
                .build());
    }

    @EventSourcingHandler
    public void on(BrandEvent event) {
        this.id = event.getId();
        this.name = event.getName();
        this.tenant = new Tenant(event.getTenantId());
    }

    @EventSourcingHandler
    public void on(BrandDeletedEvent event) {
        markDeleted();
    }

}
