package br.com.totvs.raas.product.common.data;

import lombok.*;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@ToString
@Data
@Builder
public class BrandDTO {

    private String id;
    private String name;
    private Boolean activated;
    private Long version;

    public BrandDTO(String id) {
        this.id = id;
    }

}
