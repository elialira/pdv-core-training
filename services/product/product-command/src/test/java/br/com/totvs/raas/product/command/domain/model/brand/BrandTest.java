package br.com.totvs.raas.product.command.domain.model.brand;

import br.com.totvs.raas.core.exception.ViolationException;
import br.com.totvs.raas.product.command.AbstractTest;
import br.com.totvs.raas.product.command.ProductCommandConstants.BrandConstants;
import br.com.totvs.raas.product.command.ProductCommandConstants.TenantConstants;
import br.com.totvs.raas.product.command.domain.command.ChangeBrandCommand;
import br.com.totvs.raas.product.command.domain.command.CreateBrandCommand;
import br.com.totvs.raas.product.command.domain.command.DeleteBrandCommand;
import br.com.totvs.raas.product.command.domain.model.tenant.Tenant;
import br.com.totvs.raas.product.common.event.BrandChangedEvent;
import br.com.totvs.raas.product.common.event.BrandCreatedEvent;
import br.com.totvs.raas.product.common.event.BrandDeletedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class BrandTest extends AbstractTest {

    private static final Tenant TENANT_IS_NULL = null;
    private FixtureConfiguration<Brand> fixture;

    @BeforeEach
    public void setUp() {
        fixture = new AggregateTestFixture<>(Brand.class);
    }

    @Nested
    class WhenCreate {

        @Test
        public void givenIdIsNull_thenShouldThrowViolationException() {
            Tenant tenant = new Tenant(TenantConstants.ID);

            CreateBrandCommand command = CreateBrandCommand.builder()
                    .id(BrandConstants.ID_IS_NULL)
                    .name(BrandConstants.NAME_IS_SONY)
                    .activated(BrandConstants.IS_ACTIVATED)
                    .tenant(tenant)
                    .build();

            String expected = "Violation(code=brand.id.not_empty, value=null)";

            fixture.given((Object) null)
                    .when(command)
                    .expectException(ViolationException.class)
                    .expectExceptionMessage(expected);
        }

        @Test
        public void givenNameIsNull_thenShouldThrowViolationException() {
            Tenant tenant = new Tenant(TenantConstants.ID);

            CreateBrandCommand command = CreateBrandCommand.builder()
                    .id(BrandConstants.ID)
                    .name(BrandConstants.NAME_IS_NULL)
                    .activated(BrandConstants.IS_ACTIVATED)
                    .tenant(tenant)
                    .build();

            String expected = "Violation(code=brand.name.not_empty, value=null)";

            fixture.given((Object) null)
                    .when(command)
                    .expectException(ViolationException.class)
                    .expectExceptionMessage(expected);
        }

        @Test
        public void givenStatusIsNull_thenShouldThrowViolationException() {
            Tenant tenant = new Tenant(TenantConstants.ID);

            CreateBrandCommand command = CreateBrandCommand.builder()
                    .id(BrandConstants.ID)
                    .name(BrandConstants.NAME_IS_SONY)
                    .activated(BrandConstants.STATUS_IS_NULL)
                    .tenant(tenant)
                    .build();

            String expected = "Violation(code=brand.activated.not_null, value=null)";

            fixture.given((Object) null)
                    .when(command)
                    .expectException(ViolationException.class)
                    .expectExceptionMessage(expected);
        }

        @Test
        public void givenTenantIsNull_thenShouldThrowViolationException() {
            CreateBrandCommand command = CreateBrandCommand.builder()
                    .id(BrandConstants.ID)
                    .name(BrandConstants.NAME_IS_SONY)
                    .activated(BrandConstants.IS_ACTIVATED)
                    .tenant(TENANT_IS_NULL)
                    .build();

            String expected = "Violation(code=brand.tenant.not_empty, value=null)";

            fixture.given((Object) null)
                    .when(command)
                    .expectException(ViolationException.class)
                    .expectExceptionMessage(expected);
        }

        @Test
        public void shouldSaveSuccessfully() {
            Tenant tenant = new Tenant(TenantConstants.ID);

            CreateBrandCommand command = CreateBrandCommand.builder()
                    .id(BrandConstants.ID)
                    .name(BrandConstants.NAME_IS_SONY)
                    .activated(BrandConstants.IS_ACTIVATED)
                    .tenant(tenant)
                    .build();

            BrandCreatedEvent expected = BrandCreatedEvent.builder()
                    .id(BrandConstants.ID)
                    .name(BrandConstants.NAME_IS_SONY)
                    .activated(BrandConstants.IS_ACTIVATED)
                    .tenantId(TenantConstants.ID)
                    .build();

            fixture.given((Object) null)
                    .when(command)
                    .expectEvents(expected);
        }

    }

    @Nested
    class WhenChange {

        @Test
        public void givenNameIsNull_thenShouldThrowViolationException() {
            BrandCreatedEvent event = BrandCreatedEvent.builder()
                    .id(BrandConstants.ID)
                    .name(BrandConstants.NAME_IS_SONY)
                    .activated(BrandConstants.IS_ACTIVATED)
                    .tenantId(TenantConstants.ID)
                    .build();

            Tenant tenant = new Tenant(TenantConstants.ID);

            ChangeBrandCommand command = ChangeBrandCommand.builder()
                    .id(BrandConstants.ID)
                    .name(BrandConstants.NAME_IS_NULL)
                    .activated(BrandConstants.IS_DISABLED)
                    .tenant(tenant)
                    .build();

            String expected = "Violation(code=brand.name.not_empty, value=null)";

            fixture.given(event)
                    .when(command)
                    .expectException(ViolationException.class)
                    .expectExceptionMessage(expected);
        }

        @Test
        public void givenStatusIsNull_thenShouldThrowViolationException() {
            BrandCreatedEvent event = BrandCreatedEvent.builder()
                    .id(BrandConstants.ID)
                    .name(BrandConstants.NAME_IS_SONY)
                    .activated(BrandConstants.IS_ACTIVATED)
                    .tenantId(TenantConstants.ID)
                    .build();

            Tenant tenant = new Tenant(TenantConstants.ID);

            ChangeBrandCommand command = ChangeBrandCommand.builder()
                    .id(BrandConstants.ID)
                    .name(BrandConstants.NAME_IS_LG)
                    .activated(BrandConstants.STATUS_IS_NULL)
                    .tenant(tenant)
                    .build();

            String expected = "Violation(code=brand.activated.not_null, value=null)";

            fixture.given(event)
                    .when(command)
                    .expectException(ViolationException.class)
                    .expectExceptionMessage(expected);
        }

        @Test
        public void givenTenantIsNull_thenShouldThrowViolationException() {
            BrandCreatedEvent event = BrandCreatedEvent.builder()
                    .id(BrandConstants.ID)
                    .name(BrandConstants.NAME_IS_SONY)
                    .activated(BrandConstants.IS_ACTIVATED)
                    .tenantId(TenantConstants.ID)
                    .build();

            ChangeBrandCommand command = ChangeBrandCommand.builder()
                    .id(BrandConstants.ID)
                    .name(BrandConstants.NAME_IS_LG)
                    .activated(BrandConstants.IS_ACTIVATED)
                    .tenant(TENANT_IS_NULL)
                    .build();

            String expected = "Violation(code=brand.tenant.not_empty, value=null)";

            fixture.given(event)
                    .when(command)
                    .expectException(ViolationException.class)
                    .expectExceptionMessage(expected);
        }

        @Test
        public void shouldSuccessfullyChange() {
            Tenant tenant = new Tenant(TenantConstants.ID);

            BrandCreatedEvent event = BrandCreatedEvent.builder()
                    .id(BrandConstants.ID)
                    .name(BrandConstants.NAME_IS_SONY)
                    .activated(BrandConstants.IS_ACTIVATED)
                    .tenantId(TenantConstants.ID)
                    .build();

            ChangeBrandCommand command = ChangeBrandCommand.builder()
                    .id(BrandConstants.ID)
                    .name(BrandConstants.NAME_IS_LG)
                    .activated(BrandConstants.IS_DISABLED)
                    .tenant(tenant)
                    .build();

            BrandChangedEvent expected = BrandChangedEvent.builder()
                    .id(BrandConstants.ID)
                    .name(BrandConstants.NAME_IS_LG)
                    .activated(BrandConstants.IS_DISABLED)
                    .tenantId(TenantConstants.ID)
                    .build();

            fixture.given(event)
                    .when(command)
                    .expectEvents(expected);
        }

    }

    @Nested
    class WhenDelete {

        @Test
        public void givenTenatIsNull_thenShouldThrowViolationException() {
            BrandCreatedEvent event = BrandCreatedEvent.builder()
                    .id(BrandConstants.ID)
                    .name(BrandConstants.NAME_IS_SONY)
                    .activated(BrandConstants.IS_ACTIVATED)
                    .tenantId(TenantConstants.ID)
                    .build();

            DeleteBrandCommand command = DeleteBrandCommand.builder()
                    .id(BrandConstants.ID)
                    .tenant(TENANT_IS_NULL)
                    .build();

            String expected = "Violation(code=brand.tenant.not_empty, value=null)";

            fixture.given(event)
                    .when(command)
                    .expectException(ViolationException.class)
                    .expectExceptionMessage(expected);
        }

        @Test
        public void shouldSuccessfullyDelete() {
            Tenant tenant = new Tenant(TenantConstants.ID);

            BrandCreatedEvent event = BrandCreatedEvent.builder()
                    .id(BrandConstants.ID)
                    .name(BrandConstants.NAME_IS_SONY)
                    .activated(BrandConstants.IS_ACTIVATED)
                    .tenantId(TenantConstants.ID)
                    .build();

            DeleteBrandCommand command = DeleteBrandCommand.builder()
                    .id(BrandConstants.ID)
                    .tenant(tenant)
                    .build();

            BrandDeletedEvent expected = BrandDeletedEvent.builder()
                    .id(BrandConstants.ID)
                    .tenantId(TenantConstants.ID)
                    .build();

            fixture.given(event)
                    .when(command)
                    .expectEvents(expected);
        }

    }


}