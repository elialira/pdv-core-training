package br.com.totvs.raas.product.command.application.brand.impl;

import br.com.totvs.raas.product.command.AbstractTest;
import br.com.totvs.raas.product.command.ProductCommandConstants.BrandConstants;
import br.com.totvs.raas.product.command.ProductCommandConstants.TenantConstants;
import br.com.totvs.raas.product.command.application.brand.shared.AggregateBuilder;
import br.com.totvs.raas.product.command.domain.command.ChangeBrandCommand;
import br.com.totvs.raas.product.command.domain.command.CreateBrandCommand;
import br.com.totvs.raas.product.command.domain.command.DeleteBrandCommand;
import br.com.totvs.raas.product.command.domain.model.Identity;
import br.com.totvs.raas.product.command.domain.model.brand.Brand;
import br.com.totvs.raas.product.command.domain.model.brand.UniqueBrandNameException;
import br.com.totvs.raas.product.command.domain.model.tenant.Tenant;
import br.com.totvs.raas.product.command.port.adapter.persistence.BrandRepository;
import br.com.totvs.raas.product.common.data.BrandDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.command.AggregateNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BrandServiceImplTest extends AbstractTest {

    @Mock
    private BrandRepository brandRepositoryMock;

    @Mock
    private CommandGateway commandGatewayMock;

    @Mock
    private Identity identityMock;

    @InjectMocks
    private BrandServiceImpl brandService;

    @Nested
    class WhenCreate {

        @BeforeEach
        void context() {
            when(identityMock.next()).thenReturn(BrandConstants.ID);
        }

        @Test
        public void shouldGenereteidentity() {
            when(commandGatewayMock.send(any()))
                    .thenReturn(CompletableFuture.completedFuture(BrandConstants.ID));

            BrandDTO brand = BrandDTO.builder()
                    .name(BrandConstants.NAME_IS_SONY)
                    .activated(BrandConstants.IS_ACTIVATED)
                    .build();

            brandService.create(TenantConstants.ID, brand);

            verify(identityMock).next();
        }

        @Test
        public void shouldThrowUniqueBrandNameException() {
            when(brandRepositoryMock.findByNameAndTenant(any(), any()))
                    .thenReturn(createBrand(BrandConstants.NAME_IS_SONY));

            BrandDTO brand = BrandDTO.builder()
                    .name(BrandConstants.NAME_IS_SONY)
                    .activated(BrandConstants.IS_ACTIVATED)
                    .build();

            try {
                brandService.create(TenantConstants.ID, brand);
                fail();
            } catch (UniqueBrandNameException cause) {
                String expected = "BusinessException(code=exception.unique_product_name, arguments=[Sony])";
                assertEquals(expected, cause.toString());
            }
        }

        @Test
        public void shouldCreate() {
            when(commandGatewayMock.send(any()))
                    .thenReturn(CompletableFuture.completedFuture(BrandConstants.ID));

            BrandDTO brand = BrandDTO.builder()
                    .name(BrandConstants.NAME_IS_SONY)
                    .activated(BrandConstants.IS_ACTIVATED)
                    .build();

            brandService.create(TenantConstants.ID, brand);

            Tenant tenant = new Tenant(TenantConstants.ID);

            CreateBrandCommand expected = CreateBrandCommand.builder()
                    .id(BrandConstants.ID)
                    .name(BrandConstants.NAME_IS_SONY)
                    .activated(BrandConstants.IS_ACTIVATED)
                    .tenant(tenant)
                    .build();

            verify(commandGatewayMock).send(expected);
        }

        @Test
        public void shouldReturnABrand() {
            when(commandGatewayMock.send(any()))
                    .thenReturn(CompletableFuture.completedFuture(BrandConstants.ID));

            BrandDTO brand = BrandDTO.builder()
                    .name(BrandConstants.NAME_IS_SONY)
                    .activated(BrandConstants.IS_ACTIVATED)
                    .build();

            CompletableFuture<BrandDTO> actual = brandService.create(TenantConstants.ID, brand);

            String expected = "BrandDTO(id=23c0009a-a173-4466-9396-5506cf3e862f,"
                    + " name=null, activated=null, version=null)";

            assertEquals(expected, toString(actual));
        }

        private String toString(CompletableFuture<BrandDTO> completableFuture) {
            return Optional.ofNullable(completableFuture)
                    .map(CompletableFuture::join)
                    .map(BrandDTO::toString)
                    .orElse(null);
        }

        private Optional<Brand> createBrand(String name) {
            Brand brand = AggregateBuilder.builder(Brand.class)
                    .setField("name", name)
                    .build();
            return Optional.ofNullable(brand);
        }

    }

    @Nested
    class WhenChange {

        @BeforeEach
        void context() {
            when(commandGatewayMock.send(any()))
                    .thenReturn(CompletableFuture.completedFuture(null));
        }

        @Test
        public void shouldSuccessfullyChange() {
            BrandDTO brand = BrandDTO.builder()
                    .name(BrandConstants.NAME_IS_SONY)
                    .activated(BrandConstants.IS_ACTIVATED)
                    .build();

            brandService.change(TenantConstants.ID, BrandConstants.ID,  brand);

            Tenant tenant = new Tenant(TenantConstants.ID);

            ChangeBrandCommand expected = ChangeBrandCommand.builder()
                    .id(BrandConstants.ID)
                    .name(BrandConstants.NAME_IS_SONY)
                    .activated(BrandConstants.IS_ACTIVATED)
                    .tenant(tenant)
                    .build();

            verify(commandGatewayMock).send(expected);
        }

        @Test
        public void shouldThrowBrandNotFound() {
            when(commandGatewayMock.send(any()))
                    .thenReturn(CompletableFuture
                            .failedFuture(new AggregateNotFoundException(BrandConstants.ID, "Brand Not Found")));

            BrandDTO brand = BrandDTO
                    .builder()
                    .build();

            try {
                brandService.change(TenantConstants.ID, BrandConstants.ID, brand)
                        .join();
                fail();
            } catch (CompletionException cause) {
                String expected = "BusinessException(code=exception.brand_not_found, arguments=[])";

                assertEquals(expected, toString(cause));
            }
        }

        protected String toString(CompletionException cause) {
            return Optional.ofNullable(cause)
                    .map(CompletionException::getCause)
                    .map(Object::toString)
                    .orElse(null);
        }

    }


    @Nested
    class WhenDelete {

        @BeforeEach
        void context() {
            when(commandGatewayMock.send(any()))
                    .thenReturn(CompletableFuture.completedFuture(null));
        }

        @Test
        public void shouldSuccessfullyDelete() {
            brandService.delete(TenantConstants.ID, BrandConstants.ID);

            Tenant tenant = new Tenant(TenantConstants.ID);

            DeleteBrandCommand expected = DeleteBrandCommand.builder()
                                                            .id(BrandConstants.ID)
                                                            .tenant(tenant)
                                                            .build();

            verify(commandGatewayMock).send(expected);
        }

        @Test
        public void shouldThrowBrandNotFound() {
            when(commandGatewayMock.send(any()))
                    .thenReturn(CompletableFuture
                            .failedFuture(new AggregateNotFoundException(BrandConstants.ID, "Brand Not Found")));

            try {
                brandService.delete(TenantConstants.ID, BrandConstants.ID)
                        .join();
                fail();
            } catch (CompletionException cause) {
                String expected = "BusinessException(code=exception.brand_not_found, arguments=[])";

                assertEquals(expected, toString(cause));
            }
        }

        protected String toString(CompletionException cause) {
            return Optional.ofNullable(cause)
                    .map(CompletionException::getCause)
                    .map(Object::toString)
                    .orElse(null);
        }

    }

}