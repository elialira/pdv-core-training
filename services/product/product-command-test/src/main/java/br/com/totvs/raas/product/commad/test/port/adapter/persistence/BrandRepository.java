package br.com.totvs.raas.product.commad.test.port.adapter.persistence;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class BrandRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void deleteAll() {
        entityManager.createNativeQuery(BrandSQL.DELETE)
                .executeUpdate();
    }

    @Transactional
    public void save(String id, String name, String tenantId) {
        entityManager.createNativeQuery(BrandSQL.INSERT)
                .setParameter(BrandSQL.PARAM_ID, id)
                .setParameter(BrandSQL.PARAM_NAME, name)
                .setParameter(BrandSQL.PARAM_TENANTID, tenantId)
                .executeUpdate();
    }

}
