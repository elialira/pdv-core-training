package br.com.totvs.raas.product.query.application;

import br.com.totvs.raas.product.common.data.BrandDTO;
import br.com.totvs.raas.product.common.event.BrandChangedEvent;
import br.com.totvs.raas.product.common.event.BrandCreatedEvent;
import br.com.totvs.raas.product.query.ProductQueryConstants.BrandConstants;
import br.com.totvs.raas.product.query.ProductQueryConstants.TenantConstants;
import br.com.totvs.raas.product.query.domain.brand.Brand;
import br.com.totvs.raas.product.query.domain.brand.BrandId;
import br.com.totvs.raas.product.query.domain.brand.BrandNotFoundException;
import br.com.totvs.raas.product.query.domain.tenant.Tenant;
import br.com.totvs.raas.product.query.port.adapter.persistence.BrandRepository;
import br.com.totvs.raas.product.query.port.adapter.persistence.GenericRepository;
import br.com.totvs.raas.product.query.port.adapter.service.FindABrandQuery;
import br.com.totvs.raas.product.query.port.adapter.service.FindAllBrandsQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BrandServiceTest {

    @Mock
    private BrandRepository brandRepositoryMock;

    @Mock
    private GenericRepository genericRepositoryMock;

    @InjectMocks
    private BrandService brandService;

    @Nested
    class WhenSave {

        @Test
        public void givenABrandCreatedEvent_shouldSave() {
            BrandCreatedEvent brandEvent = BrandCreatedEvent
                    .builder()
                    .id(BrandConstants.ID)
                    .name(BrandConstants.NAME_IS_LG)
                    .activated(BrandConstants.IS_ACTIVATED)
                    .version(BrandConstants.VERSION_IS_ONE)
                    .tenantId(TenantConstants.ID)
                    .build();

            brandService.on(brandEvent);

            Brand expected = Brand
                    .builder()
                    .id(new BrandId(BrandConstants.ID))
                    .name(BrandConstants.NAME_IS_LG)
                    .activated(BrandConstants.IS_ACTIVATED)
                    .version(BrandConstants.VERSION_IS_ONE)
                    .tenant(new Tenant(TenantConstants.ID))
                    .build();

            verify(genericRepositoryMock)
                    .save(expected);
        }

    }

    @Nested
    class WhenChange {

        @Test
        public void givenABrandChangedEvent_shouldSave() {
            BrandChangedEvent brandEvent = BrandChangedEvent
                    .builder()
                    .id(BrandConstants.ID)
                    .name(BrandConstants.NAME_IS_SONY)
                    .activated(BrandConstants.IS_DISABLED)
                    .version(BrandConstants.VERSION_IS_TWO)
                    .tenantId(TenantConstants.ID)
                    .build();

            brandService.on(brandEvent);

            Brand expected = Brand
                    .builder()
                    .id(new BrandId(BrandConstants.ID))
                    .name(BrandConstants.NAME_IS_SONY)
                    .activated(BrandConstants.IS_DISABLED)
                    .version(BrandConstants.VERSION_IS_TWO)
                    .tenant(new Tenant(TenantConstants.ID))
                    .build();

            verify(genericRepositoryMock)
                    .update(expected);
        }

    }

    @Nested
    class WhenFind  {

        private final Optional<Brand> BRAND_IS_NULL = Optional.empty();

        @BeforeEach
        void context() {
            when(brandRepositoryMock
                    .findByIdAndTenantId(createBrandId(BrandConstants.ID), TenantConstants.ID))
                    .thenReturn(createBrand(BrandConstants.ID));
        }

        @Test
        public void shouldFind() {
            FindABrandQuery findABrandQuery = FindABrandQuery
                    .builder()
                    .id(BrandConstants.ID)
                    .tenantId(TenantConstants.ID)
                    .build();

            brandService.handle(findABrandQuery);

            verify(brandRepositoryMock)
                    .findByIdAndTenantId(createBrandId(BrandConstants.ID), TenantConstants.ID);
        }

        @Test
        public void shouldReturnBrand() {
            Optional<Brand> brand = Optional.ofNullable(
                    BrandServiceTest.this.createBrand(BrandConstants.ID,
                            BrandConstants.NAME_IS_LG,
                            BrandConstants.IS_ACTIVATED,
                            TenantConstants.ID,
                            BrandConstants.VERSION_IS_ONE));

            when(brandRepositoryMock
                    .findByIdAndTenantId(createBrandId(BrandConstants.ID), TenantConstants.ID))
                    .thenReturn(brand);

            FindABrandQuery findABrandQuery = FindABrandQuery
                    .builder()
                    .id(BrandConstants.ID)
                    .tenantId(TenantConstants.ID)
                    .build();

            BrandDTO actual = brandService.handle(findABrandQuery);

            String expetected = "BrandDTO(id=23c0009a-a173-4466-9396-5506cf3e862f, name=LG, activated=true, version=1)";

            assertEquals(expetected, BrandServiceTest.this.toString(actual));
        }

        @Test
        public void shouldThrowBrandNotFound() {
            when(brandRepositoryMock
                    .findByIdAndTenantId(createBrandId(BrandConstants.ID), TenantConstants.ID))
                    .thenReturn(BRAND_IS_NULL);

            FindABrandQuery findABrandQuery = FindABrandQuery
                    .builder()
                    .id(BrandConstants.ID)
                    .tenantId(TenantConstants.ID)
                    .build();
            try {
                brandService.handle(findABrandQuery);

                fail();
            } catch (BrandNotFoundException cause) {
                String expected = "BusinessException(code=exception.brand_not_found, arguments=[])";

                assertEquals(expected, cause.toString());
            }
        }

        private BrandId createBrandId(String id) {
            return new BrandId(id);
        }

        private Optional<Brand> createBrand(String id) {
            Brand brand = Brand.builder()
                    .id(new BrandId(id))
                    .build();
            return Optional.ofNullable(brand);
        }

    }

    @Nested
    class WhenFindAll {

        @Test
        public void shouldFind() {
            FindAllBrandsQuery findAllBrandsQuery = FindAllBrandsQuery
                    .builder()
                    .tenantId(TenantConstants.ID)
                    .build();

            brandService.handle(findAllBrandsQuery);

            verify(brandRepositoryMock)
                    .findByTenantId(TenantConstants.ID);
        }

        @Test
        public void shouldReturnBrands() {
            List<Brand> brands = createBrands(
                    createBrand(BrandConstants.ID,
                            BrandConstants.NAME_IS_LG,
                            BrandConstants.IS_ACTIVATED,
                            TenantConstants.ID,
                            BrandConstants.VERSION_IS_ONE));

            when(brandRepositoryMock
                    .findByTenantId(TenantConstants.ID))
                    .thenReturn(brands);

            FindAllBrandsQuery findAllBrandsQuery = FindAllBrandsQuery
                    .builder()
                    .tenantId(TenantConstants.ID)
                    .build();

            List<BrandDTO> actual = brandService.handle(findAllBrandsQuery);

            String expetected = "[BrandDTO(id=23c0009a-a173-4466-9396-5506cf3e862f, name=LG, activated=true, version=1)]";

            assertEquals(expetected, BrandServiceTest.this.toString(actual));
        }

        private List<Brand> createBrands(Brand... brands) {
            return Arrays.asList(brands);
        }

    }

    protected Brand createBrand(String id,
                                String name, boolean activated,
                                String tenantId, Long version) {
        return Brand.builder()
                .id(new BrandId(id))
                .name(name)
                .activated(activated)
                .tenant(new Tenant(tenantId))
                .version(version)
                .build();
    }

    protected String toString(Object value) {
        return Optional.ofNullable(value)
                .map(Objects::toString)
                .orElse(null);
    }

}