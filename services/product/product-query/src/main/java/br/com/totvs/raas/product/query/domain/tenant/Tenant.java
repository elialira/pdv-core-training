package br.com.totvs.raas.product.query.domain.tenant;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Generated
@ToString
@Getter
@Embeddable
@EqualsAndHashCode
public class Tenant implements Serializable {

    @Column(name = "tenantid")
    private String id;

}
