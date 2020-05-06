package br.com.totvs.raas.product.commad.test.adapter.persistence;

public interface BrandSQL {

    String INSERT = "INSERT INTO BRAND (id, activated, name, tenantid, version) "
            + " VALUES (:ID, :ACTIVATED, :NAME, :TENANTID, :VERSION)";
    String DELETE_ALL = "DELETE FROM BRAND";
    String FIND_BY_ID_AND_VERSION = "SELECT id, activated, name, tenantid, version "
            + " FROM Brand WHERE ID = :ID AND VERSION = :VERSION";

    String PARAM_ID = "ID";
    String PARAM_VERSION = "VERSION";
    String PARAM_ACTIVATED =  "ACTIVATED";
    String PARAM_NAME = "NAME";
    String PARAM_TENANT_ID = "TENANTID";

}
