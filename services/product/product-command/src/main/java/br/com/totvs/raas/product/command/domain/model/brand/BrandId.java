package br.com.totvs.raas.product.command.domain.model.brand;

import com.google.common.base.MoreObjects;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BrandId {

    private String id;

    @Override
    public String toString() {
        return id;
    }
}
