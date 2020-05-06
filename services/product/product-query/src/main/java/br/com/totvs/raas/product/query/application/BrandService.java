package br.com.totvs.raas.product.query.application;

import br.com.totvs.raas.product.common.data.BrandDTO;
import br.com.totvs.raas.product.common.event.BrandChangedEvent;
import br.com.totvs.raas.product.common.event.BrandCreatedEvent;
import br.com.totvs.raas.product.common.event.BrandEvent;
import br.com.totvs.raas.product.query.domain.brand.Brand;
import br.com.totvs.raas.product.query.domain.brand.BrandId;
import br.com.totvs.raas.product.query.domain.brand.BrandNotFoundException;
import br.com.totvs.raas.product.query.domain.tenant.Tenant;
import br.com.totvs.raas.product.query.port.adapter.persistence.BrandRepository;
import br.com.totvs.raas.product.query.port.adapter.persistence.GenericRepository;
import br.com.totvs.raas.product.query.port.adapter.service.FindABrandQuery;
import br.com.totvs.raas.product.query.port.adapter.service.FindAllBrandsQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@ProcessingGroup("amqpEvents")
public class BrandService {

    private GenericRepository genericRepository;
    private BrandRepository brandRepository;

    @Autowired
    public BrandService(GenericRepository genericRepository, BrandRepository brandRepository) {
        this.genericRepository = genericRepository;
        this.brandRepository = brandRepository;
    }

    @EventHandler
    public void on(BrandCreatedEvent event) {
        genericRepository.save(toEntity(event));
    }

    @EventHandler
    public void on(BrandChangedEvent event) {
        genericRepository.update(toEntity(event));
    }

    private Brand toEntity(BrandEvent event) {
        return Brand.builder()
                .id(new BrandId(event.getId()))
                .name(event.getName())
                .activated(event.isActivated())
                .version(event.getVersion())
                .tenant(new Tenant(event.getTenantId()))
                .build();
    }

    @QueryHandler
    public List<BrandDTO> handle(FindAllBrandsQuery query) {
        return brandRepository
                .findByTenantId(query.getTenantId())
                .stream()
                .map(this::toDTO)
                .collect(toList());
    }

    @QueryHandler
    public BrandDTO handle(FindABrandQuery query) {
        BrandId id = new BrandId(query.getId());
        return brandRepository.findByIdAndTenantId(id, query.getTenantId())
                .map(this::toDTO)
                .orElseThrow(BrandNotFoundException::new);
    }

    private BrandDTO toDTO(Brand brand) {
        return BrandDTO.builder()
                .id(brand.getId().getId())
                .name(brand.getName())
                .activated(brand.isActivated())
                .version(brand.getVersion())
                .build();
    }

}
