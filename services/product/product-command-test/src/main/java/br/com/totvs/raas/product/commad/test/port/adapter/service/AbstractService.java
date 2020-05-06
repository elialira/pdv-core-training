package br.com.totvs.raas.product.commad.test.port.adapter.service;

import br.com.totvs.raas.core.test.context.Fault;
import br.com.totvs.raas.core.test.context.Result;
import br.com.totvs.raas.core.test.context.Success;
import br.com.totvs.raas.product.commad.test.ProductCommandTestConstants.*;
import br.com.totvs.raas.product.commad.test.port.adapter.shared.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static java.util.Arrays.asList;

public abstract class AbstractService {

    @Value("${product.service.protocol}://${product.service.host}:${product.service.port}/")
    private String productUrl;

    @Value("${rac.service.protocol}://${rac.service.host}:${rac.service.port}/totvs.rac/connect/token")
    private String racUrl;

    private String resource;

    @Autowired
    private RestTemplate restTemplate;

    public AbstractService(String resource) {
        this.resource = resource;
    }

    public <P> Result post(P payload) {
        return exchange(getUri(), HttpMethod.POST, payload, Map.class);
    }

    public <P> Result put(String id, P payload) {
        return exchange(getUri(id), HttpMethod.PUT, payload, Object.class);
    }

    public <P> Result delete(String id) {
        return exchange(getUri(id), HttpMethod.DELETE, null, Object.class);
    }

    private <P, T> Result exchange(String url, HttpMethod method, P payload, Class<T> responseType) {
        HttpEntity<P> body = createHttpEntity(payload);

        try {
            ResponseEntity response = restTemplate.exchange(url, method, body, responseType);
            return new Success(response.getBody());
        } catch (HttpClientErrorException cause) {
            return new Fault(Mapper.to(cause.getResponseBodyAsByteArray()));
        }
    }

    private <P> HttpEntity<P> createHttpEntity(P payload) {
        return new HttpEntity<>(payload, createHeaders());
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAcceptLanguageAsLocales(asList(Locale.US));
        headers.set("tenant", TenantConstants.ID);
        return headers;
    }

    private String getUri(String pathParam) {
        return getUri() + "/" + pathParam;
    }

    private String getUri() {
        return this.productUrl + resource;
    }

}
