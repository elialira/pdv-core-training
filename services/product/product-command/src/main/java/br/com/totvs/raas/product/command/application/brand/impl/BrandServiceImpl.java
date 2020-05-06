package br.com.totvs.raas.product.command.application.brand.impl;

import br.com.totvs.raas.product.command.application.brand.BrandService;
import br.com.totvs.raas.product.command.application.brand.shared.Exceptionally;
import br.com.totvs.raas.product.command.domain.command.ChangeBrandCommand;
import br.com.totvs.raas.product.command.domain.command.CreateBrandCommand;
import br.com.totvs.raas.product.command.domain.command.DeleteBrandCommand;
import br.com.totvs.raas.product.command.domain.model.Identity;
import br.com.totvs.raas.product.command.domain.model.brand.BrandId;
import br.com.totvs.raas.product.command.domain.model.brand.BrandNotFoundException;
import br.com.totvs.raas.product.command.domain.model.tenant.Tenant;
import br.com.totvs.raas.product.common.data.BrandDTO;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class BrandServiceImpl implements BrandService {

    private CommandGateway commandGateway;
    private Identity identity;

    @Autowired
    public BrandServiceImpl(CommandGateway commandGateway, Identity identity) {
        this.commandGateway = commandGateway;
        this.identity = identity;
    }

    @Override
    public CompletableFuture<BrandDTO> create(String tenantId, BrandDTO brand) {
        BrandId brandId = new BrandId(identity.next());
        Tenant tenant = new Tenant(tenantId);

        return commandGateway.send(
                CreateBrandCommand.builder()
                        .id(brandId)
                        .name(brand.getName())
                        .activated(brand.getActivated())
                        .tenant(tenant)
                        .build())
                .thenApply(Object::toString)
                .thenApply(BrandDTO::new);
    }

    @Override
    public CompletableFuture<Object> change(String tenantId, String id, BrandDTO brand) {
        BrandId brandId = new BrandId(id);
        Tenant tenant = new Tenant(tenantId);

        return commandGateway.send(
                ChangeBrandCommand.builder()
                        .id(brandId)
                        .name(brand.getName())
                        .activated(brand.getActivated())
                        .tenant(tenant)
                        .version(brand.getVersion())
                        .build())
                .exceptionally(Exceptionally
                        .builder()
                        .notFound(BrandNotFoundException::new)
                        .build());
    }

    @Override
    public CompletableFuture<Object> delete(String tenantId, String id) {
        BrandId brandId = new BrandId(id);
        Tenant tenant = new Tenant(tenantId);

        return commandGateway.send(
                DeleteBrandCommand.builder()
                        .id(brandId)
                        .tenant(tenant)
                        .build())
                .exceptionally(Exceptionally
                        .builder()
                        .notFound(BrandNotFoundException::new)
                        .build());
    }

}
