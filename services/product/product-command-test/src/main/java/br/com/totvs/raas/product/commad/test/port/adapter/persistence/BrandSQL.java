package br.com.totvs.raas.product.commad.test.port.adapter.persistence;

public interface BrandSQL {

    String INSERT = "INSERT INTO BRAND (id, name, tenantid) "
            + " VALUES (:ID, :NAME, :TENANTID)";

    String DELETE = "delete from brand";

    String PARAM_ID = "ID";
    String PARAM_NAME = "NAME";
    String PARAM_TENANTID = "TENANTID";

}
