package br.com.totvs.raas.product.commad.test.adapter.persistence;

import br.com.totvs.raas.product.commad.test.ProductQueryTestConstants.*;
import br.com.totvs.raas.product.commad.test.adapter.shared.Wait;
import br.com.totvs.raas.product.commad.test.steps.VersionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Repository
public class BrandRepository  {

    @Autowired
    private VersionContext versionContext;

    @PersistenceContext
    private EntityManager entityManager;

    public void deleteAll() {
        entityManager.createNativeQuery(BrandSQL.DELETE_ALL)
                .executeUpdate();
    }

    public String save(String name, boolean activated) {
        String id = UUID.randomUUID().toString();
        Long version = versionContext.next(id);
        entityManager.createNativeQuery(BrandSQL.INSERT)
                .setParameter(BrandSQL.PARAM_ID, id)
                .setParameter(BrandSQL.PARAM_ACTIVATED, activated)
                .setParameter(BrandSQL.PARAM_NAME, name)
                .setParameter(BrandSQL.PARAM_TENANT_ID, TenantConstants.ID)
                .setParameter(BrandSQL.PARAM_VERSION, version)
                .executeUpdate();
        return id;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> findBy(String id) {
        return Wait.notNull(() -> {
            try {
                return find(id, versionContext.get(id));
            } catch (NoResultException cause) {
                return null;
            }
        });
    }

    private Map<String, Object> find(String id, Long version) {
        return Optional.ofNullable((Object[]) entityManager
                .createNativeQuery(BrandSQL.FIND_BY_ID_AND_VERSION)
                .setParameter(BrandSQL.PARAM_ID, id)
                .setParameter(BrandSQL.PARAM_VERSION, version)
                .getSingleResult())
                .map(this::toMap)
                .orElse(null);
    }

    private Map<String, Object> toMap(Object[] brand) {
        Map<String, Object> value = new HashMap<>();
        value.put("id", brand[0]);
        value.put("activated", brand[1]);
        value.put("name", brand[2]);
        value.put("tenantid", brand[3]);
        value.put("version", brand[4]);
        return value;
    }


}
