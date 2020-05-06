package br.com.totvs.raas.product.query.port.adapter.persistence;

public interface GenericRepository {

    void save(Object entity);

    void update(Object entity);

}
