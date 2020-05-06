package br.com.totvs.raas.product.command.domain.model.tenant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
@Getter
@Embeddable
public class Tenant implements Serializable {

    @JsonProperty("tenantid")
    @Column(name = "tenantid")
    private String id;

}
