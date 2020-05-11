package br.com.totvs.raas.product.command.port.adapter.persistence;

import br.com.totvs.raas.product.command.domain.model.brand.Brand;
import br.com.totvs.raas.product.command.domain.model.tenant.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, String> {

    Optional<Brand> findByNameAndTenant(String name, Tenant tenant);
}
