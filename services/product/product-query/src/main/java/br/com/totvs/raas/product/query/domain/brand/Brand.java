package br.com.totvs.raas.product.query.domain.brand;

import br.com.totvs.raas.product.query.domain.tenant.Tenant;
import lombok.*;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Version;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Generated
@ToString
@Builder
@EqualsAndHashCode
public class Brand {

    @EmbeddedId
    private BrandId id;
    private String name;
    private boolean activated;
    @Embedded
    private Tenant tenant;
    @Version
    private Long version;

}
