package br.com.totvs.raas.product.query.domain.brand;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Generated
@ToString
@Getter
@Embeddable
@EqualsAndHashCode
public class BrandId implements Serializable {

    private String id;

}
