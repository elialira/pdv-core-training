package br.com.totvs.raas.product.query.port.adapter.service;

import br.com.totvs.raas.product.common.data.BrandDTO;
import br.com.totvs.raas.product.query.ProductQueryConstants.BrandConstants;
import br.com.totvs.raas.product.query.ProductQueryConstants.TenantConstants;
import org.axonframework.messaging.responsetypes.ResponseType;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(value = BrandController.class)
@AutoConfigureMockMvc(addFilters = false)
class BrandControllerTest {

    @Autowired
    private MockMvc mvcMock;

    @MockBean
    private QueryGateway queryGatewayMock;

    @Nested
    class WhenSearch {

        @Test
        public void shouldSuccessfullySearch() throws Exception {
            mvcMock.perform(get("/api/brands/{id}", BrandConstants.ID)
                    .header("tenant", TenantConstants.ID)
                    .contentType(MediaType.APPLICATION_JSON_UTF8));

            FindABrandQuery expectedQuery = FindABrandQuery.builder()
                    .id(BrandConstants.ID)
                    .tenantId(TenantConstants.ID)
                    .build();

            ArgumentMatcher<ResponseType> expectedResponseTypes =
                    BrandControllerTest.this.new ResponseTypeMatcher(
                            ResponseTypes.instanceOf(BrandDTO.class));

            verify(queryGatewayMock).query(eq(expectedQuery),
                    argThat(expectedResponseTypes));
        }

    }

    @Nested
    class WhenSearchAll {

        @Test
        public void shouldSuccessfullySearch() throws Exception {
            mvcMock.perform(get("/api/brands")
                    .header("tenant", TenantConstants.ID)
                    .contentType(MediaType.APPLICATION_JSON_UTF8));

            FindAllBrandsQuery expectedQuery = FindAllBrandsQuery.builder()
                    .tenantId(TenantConstants.ID)
                    .build();

            ArgumentMatcher<ResponseType> expectedResponseTypes =
                    BrandControllerTest.this.new ResponseTypeMatcher(
                            ResponseTypes.multipleInstancesOf(BrandDTO.class));

            verify(queryGatewayMock).query(eq(expectedQuery),
                    argThat(expectedResponseTypes));
        }

    }

    class ResponseTypeMatcher<T> implements ArgumentMatcher<ResponseType<T>> {

        private ResponseType<T> responseType;

        public ResponseTypeMatcher(ResponseType<T>responseType) {
            this.responseType = responseType;
        }

        @Override
        public boolean matches(ResponseType<T> responseType) {
            return this.responseType != null && responseType != null
                    && this.responseType.getClass().isInstance(responseType)
                    && this.responseType.getExpectedResponseType() != null
                    && this.responseType.getExpectedResponseType()
                            .isAssignableFrom(responseType.getExpectedResponseType());
        }

    }

}