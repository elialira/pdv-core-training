package br.com.totvs.raas.product.command.domain.command;

import br.com.totvs.raas.product.command.domain.model.brand.BrandId;
import br.com.totvs.raas.product.command.domain.model.tenant.Tenant;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import static java.util.Objects.nonNull;

@Getter
@SuperBuilder
public abstract class BrandCommand  {

    @TargetAggregateIdentifier
    private BrandId id;
    private String name;
    private Boolean activated;
    private Tenant tenant;

    public String getTenantId() {
        if (nonNull(tenant)) {
            return tenant.getId();
        }
        return null;
    }

}
