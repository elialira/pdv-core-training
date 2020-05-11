package br.com.totvs.raas.product.command.application.brand.impl;

import br.com.totvs.raas.product.command.application.brand.BrandService;
import br.com.totvs.raas.product.command.application.brand.shared.Exceptionally;
import br.com.totvs.raas.product.command.domain.command.ChangeBrandCommand;
import br.com.totvs.raas.product.command.domain.command.CreateBrandCommand;
import br.com.totvs.raas.product.command.domain.command.DeleteBrandCommand;
import br.com.totvs.raas.product.command.domain.model.Identity;
import br.com.totvs.raas.product.command.domain.model.brand.BrandNotFoundException;
import br.com.totvs.raas.product.command.domain.model.brand.UniqueBrandNameException;
import br.com.totvs.raas.product.command.domain.model.tenant.Tenant;
import br.com.totvs.raas.product.command.port.adapter.persistence.BrandRepository;
import br.com.totvs.raas.product.common.data.BrandDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class BrandServiceImpl implements BrandService {

    private BrandRepository brandRepository;
    private CommandGateway commandGateway;
    private Identity identity;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository, CommandGateway commandGateway, Identity identity) {
        this.brandRepository = brandRepository;
        this.commandGateway = commandGateway;
        this.identity = identity;
    }

    @Override
    public CompletableFuture<BrandDTO> create(String tenantId, BrandDTO brand) {
        String id = identity.next();
        Tenant tenant = new Tenant(tenantId);

        brandRepository
                .findByNameAndTenant(brand.getName(), tenant)
                .ifPresent(existingBrand -> {throw new UniqueBrandNameException(existingBrand.getName());});

        return commandGateway.send(
                CreateBrandCommand.builder()
                        .id(id)
                        .name(brand.getName())
                        .activated(brand.getActivated())
                        .tenant(tenant)
                        .build())
                .thenApply(Object::toString)
                .thenApply(BrandDTO::new);
    }

    @Override
    public CompletableFuture<Object> change(String tenantId, String id, BrandDTO brand) {
        Tenant tenant = new Tenant(tenantId);

        return commandGateway.send(
                ChangeBrandCommand.builder()
                        .id(id)
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
        Tenant tenant = new Tenant(tenantId);

        return commandGateway.send(
                DeleteBrandCommand.builder()
                        .id(id)
                        .tenant(tenant)
                        .build())
                .exceptionally(Exceptionally
                        .builder()
                        .notFound(BrandNotFoundException::new)
                        .build());
    }

}
