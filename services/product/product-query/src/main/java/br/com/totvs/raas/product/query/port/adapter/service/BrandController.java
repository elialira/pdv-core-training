package br.com.totvs.raas.product.query.port.adapter.service;

import br.com.totvs.raas.product.common.data.BrandDTO;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/brands")
public class BrandController {

    private QueryGateway queryGateway;

    @Autowired
    public BrandController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/{id}")
    public CompletableFuture<BrandDTO> get(@RequestHeader String tenant,
                                            @PathVariable String id) {
        FindABrandQuery query = FindABrandQuery.builder()
                .id(id)
                .tenantId(tenant)
                .build();

        return queryGateway
                .query(query, ResponseTypes.instanceOf(BrandDTO.class));
    }

    @GetMapping
    public CompletableFuture<List<BrandDTO>> getAll(@RequestHeader String tenant) {
        return queryGateway
                .query(FindAllBrandsQuery.builder()
                                .tenantId(tenant)
                                .build(),
                        ResponseTypes.multipleInstancesOf(BrandDTO.class));
    }

}
