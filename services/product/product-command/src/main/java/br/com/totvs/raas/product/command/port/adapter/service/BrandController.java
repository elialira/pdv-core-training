package br.com.totvs.raas.product.command.port.adapter.service;

import br.com.totvs.raas.product.command.application.brand.BrandService;
import br.com.totvs.raas.product.common.data.BrandDTO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/brands")
@Api(value = "/api/brands")
public class BrandController {

    private BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<BrandDTO> post(@RequestHeader String tenant,
                                            @RequestBody BrandDTO brand) {
        return brandService.create(tenant, brand);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CompletableFuture<Object> put(@RequestHeader String tenant,
                                         @PathVariable String id,
                                         @RequestBody BrandDTO brand) {
        return brandService.change(tenant, id, brand);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CompletableFuture<Object> delete(@RequestHeader String tenant,
                                            @PathVariable String id) {
        return brandService.delete(tenant, id);
    }

}
