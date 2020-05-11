package br.com.totvs.raas.product.common.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor
@SuperBuilder
public class BrandEvent {

    private String id;
    private String name;
    private boolean activated;
    private String tenantId;
    private Long version;

}
