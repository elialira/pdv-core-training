package br.com.totvs.raas.product.command.port.adapter.service;

import br.com.totvs.raas.core.i18.Translator;
import br.com.totvs.raas.product.command.AbstractTest;
import br.com.totvs.raas.product.command.ProductCommandConstants.BrandConstants;
import br.com.totvs.raas.product.command.ProductCommandConstants.TenantConstants;
import br.com.totvs.raas.product.command.application.brand.BrandService;
import br.com.totvs.raas.product.common.data.BrandDTO;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(value = BrandController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BrandControllerTest extends AbstractTest {

    @Autowired
    private MockMvc mvcMock;

    @MockBean
    private BrandService brandServiceMock;

    @MockBean
    private Translator translator;

    @Nested
    class WhenCreate {

        @Test
        public void shouldSaveSuccessfully() throws Exception {
            String contet = "{\"name\":\"Sony\",\"activated\":true}";

            mvcMock.perform(post("/api/brands")
                    .header("tenant", TenantConstants.ID)
                    .content(contet)
                    .contentType(MediaType.APPLICATION_JSON_UTF8));

            BrandDTO brand = BrandDTO.builder()
                    .name(BrandConstants.NAME_IS_SONY)
                    .activated(BrandConstants.IS_ACTIVATED)
                    .build();

            verify(brandServiceMock).create(TenantConstants.ID, brand);
        }

    }

    @Nested
    class WhenChange {

        @Test
        public void shouldSuccessfullyChange() throws Exception {
            String contet = "{\"name\":\"Sony\",\"activated\":true}";

            mvcMock.perform(put("/api/brands/{id}", BrandConstants.ID)
                    .header("tenant", TenantConstants.ID)
                    .content(contet)
                    .contentType(MediaType.APPLICATION_JSON_UTF8));

            BrandDTO brand = BrandDTO.builder()
                    .name(BrandConstants.NAME_IS_SONY)
                    .activated(BrandConstants.IS_ACTIVATED)
                    .build();

            verify(brandServiceMock).change(TenantConstants.ID, BrandConstants.ID, brand);
        }

    }

    @Nested
    class WhenDelete {

        @Test
        public void shouldSuccessfullyDelete() throws Exception {
            mvcMock.perform(delete("/api/brands/{id}", BrandConstants.ID)
                    .header("tenant", TenantConstants.ID)
                    .contentType(MediaType.APPLICATION_JSON_UTF8));

            verify(brandServiceMock).delete(TenantConstants.ID, BrandConstants.ID);
        }

    }

}