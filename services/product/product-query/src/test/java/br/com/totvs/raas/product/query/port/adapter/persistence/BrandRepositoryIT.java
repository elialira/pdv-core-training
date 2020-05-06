package br.com.totvs.raas.product.query.port.adapter.persistence;

import br.com.totvs.raas.product.query.ProductQueryConstants.BrandConstants;
import br.com.totvs.raas.product.query.ProductQueryConstants.TenantConstants;
import br.com.totvs.raas.product.query.domain.brand.BrandId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BrandRepositoryIT extends AbstractPersistenceTest {

    @Autowired
    private BrandRepository brandRepository;

    @BeforeEach
    public void setUp() {
        brandRepository.deleteAll();
    }

    @Nested
    class WhenFind {

        @Test
        public void should() {
            brandRepository.findByIdAndTenantId(createBrandId(BrandConstants.ID),
                                                TenantConstants.ID);
        }

        private BrandId createBrandId(String id) {
            return new BrandId(id);
        }

    }

    @AfterEach
    public void tearDown() {
        brandRepository.deleteAll();
    }

}