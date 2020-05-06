package br.com.totvs.raas.product.query.port.adapter.persistence;

import br.com.totvs.raas.product.query.domain.brand.Brand;
import br.com.totvs.raas.product.query.domain.brand.BrandId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, BrandId> {

    List<Brand> findByTenantId(String tenantId);

    Optional<Brand> findByIdAndTenantId(BrandId id, String tenantId);

}
