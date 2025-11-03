package com.rgb.training.app.data.repository;


import com.rgb.training.app.data.model.Vehicles;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marccunillera
 */
public class VehiclesJTARepository {
    @PersistenceContext(unitName = "testdbd")
    private EntityManager entityManager;

    public VehiclesJTARepository() {
    }

    public Vehicles get(Integer entryId) {
        Vehicles result = null;
        try {
            result = entityManager.createQuery("SELECT v FROM Vehicles v WHERE v.id = :entryId", Vehicles.class)
                    .setParameter("entryId", entryId)
                    .getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public List<Vehicles> getAll() {
        return getAll(null, null);
    }

    public List<Vehicles> getAll(Integer offset, Integer maxResults) {
        List<Vehicles> results = new ArrayList<>();
        offset = offset == null ? 0 : offset;
        maxResults = maxResults == null ? 500 : maxResults;
        try {
            results = entityManager.createQuery("SELECT v FROM Vehicles v ORDER BY v.id")
                    .setFirstResult(offset)
                    .setMaxResults(maxResults)
                    .getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return results;
    }

    public Vehicles create(Vehicles entry) {
        try {
            entityManager.joinTransaction();
            entityManager.persist(entry);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return entry;
    }

    public Vehicles update(Vehicles entry) {
        try {
            entityManager.joinTransaction();
            entry = entityManager.merge(entry);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return entry;
    }

    public Vehicles delete(Vehicles entry) {
        try {
            entityManager.remove(entry);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return entry;
    }

    public Integer delete(Integer entryId) {
        Integer result = -1;
        try {
            Vehicles reference = entityManager.getReference(Vehicles.class, entryId);
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

