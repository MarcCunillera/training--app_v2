package com.rgb.training.app.data.repository;

import com.rgb.training.app.data.model.MyTable;
import jakarta.annotation.PreDestroy;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LuisCarlosGonzalez
 */
@Stateless
public class MyTableJTARepository {

    @PersistenceContext(unitName = "testdbd")
    private EntityManager entityManager;

    public MyTableJTARepository() {
        
    }

    /**
     * Busca un asiento contable por su identificador.
     *
     * @param entryId Identificador del asiento contable
     *
     * @return Un asiento contable(Entity)
     */

    public MyTable get(Long entryId) {
        try {
            return entityManager.createQuery(
                    "SELECT mt FROM MyTable mt WHERE mt.id = :entryId", MyTable.class)
                    .setParameter("entryId", entryId)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<MyTable> getAll() {
        return getAll(null, null);
    }

    public List<MyTable> getAll(Integer offset, Integer maxResults) {
        List<MyTable> results = new ArrayList<>();
        offset = offset == null ? 0 : offset;
        maxResults = maxResults == null ? 500 : maxResults;
        try {
            results = entityManager.createQuery("SELECT mt FROM MyTable mt "
                    + "ORDER BY mt.id")
                    .setFirstResult(offset)
                    .setMaxResults(maxResults)
                    .getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return results;
    }

    /**
     * Guarda(persist) en la base de datos.
     *
     * @param entry Datos (Entity)
     *
     * @return El registro guardado(Entity)
     */
    public MyTable create(MyTable entry) {
        try {
            entityManager.joinTransaction();
            entityManager.persist(entry);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return entry;
    }

    /**
     * Guarda(merge) en la base de datos.
     *
     * @param entry Datos (Entity)
     *
     * @return El registro guardado(Entity)
     */
    public MyTable update(MyTable entry) {
        try {
            entityManager.joinTransaction();
            entry = entityManager.merge(entry);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return entry;
    }

    /**
     * Elimina un registro por la entidad manejada.
     *
     * @param entry Datos del asiento contable(Entity)
     *
     * @return El asiento contable eliminado(Entity)
     */
    public MyTable delete(MyTable entry) {
        try {
            entityManager.remove(entry);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return entry;
    }

    /**
     * Elimina un registro por su identificador
     *
     * @param entryId
     *
     * @return El resultado de la operación(LongEntity).
     */
    public Long delete(Long entryId) {
        Long result = -1L;
        try {
            MyTable reference = entityManager.getReference(MyTable.class, entryId);
            if (reference != null) {
                entityManager.remove(reference);
                result = entryId;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @PreDestroy
    public void close() {
        if (this.entityManager != null) {
            this.entityManager.close();
        }
    }
}
