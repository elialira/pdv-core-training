package br.com.totvs.raas.product.command.application.brand;

import br.com.totvs.raas.product.common.data.BrandDTO;

import java.util.concurrent.CompletableFuture;

public interface BrandService {

    CompletableFuture<BrandDTO> create(String tenantId, BrandDTO brand);

    CompletableFuture<Object> change(String tenantId, String id, BrandDTO brand);

    CompletableFuture<Object> delete(String tenantId, String id);

}
