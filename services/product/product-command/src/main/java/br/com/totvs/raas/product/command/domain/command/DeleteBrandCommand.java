package br.com.totvs.raas.product.command.domain.command;

import br.com.totvs.raas.product.command.domain.model.brand.BrandId;
import br.com.totvs.raas.product.command.domain.model.tenant.Tenant;
import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import static java.util.Objects.nonNull;

@Getter
@Generated
@EqualsAndHashCode
@SuperBuilder
public class DeleteBrandCommand {

    @TargetAggregateIdentifier
    private BrandId id;
    private Tenant tenant;

    public String getTenantId() {
        if (nonNull(tenant)) {
            return tenant.getId();
        }
        return null;
    }

}
