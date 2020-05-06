package br.com.totvs.raas.product.command.domain.command;

import lombok.EqualsAndHashCode;
import lombok.Generated;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.axonframework.modelling.command.TargetAggregateVersion;

@Getter
@SuperBuilder
@Generated
@EqualsAndHashCode
public class ChangeBrandCommand extends BrandCommand {

    @TargetAggregateVersion
    private Long version;

}

