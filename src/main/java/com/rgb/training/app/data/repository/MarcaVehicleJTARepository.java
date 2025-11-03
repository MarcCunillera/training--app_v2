package com.rgb.training.app.data.repository;

import com.rgb.training.app.data.model.MarcaVehicle;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class MarcaVehicleJTARepository {

    @PersistenceContext(unitName = "testdbd")
    private EntityManager entityManager;

    public MarcaVehicle get(Integer entryId) {
        try {
            return entityManager.createQuery(
                    "SELECT mv FROM MarcaVehicle mv WHERE mv.id = :entryId", 
                    MarcaVehicle.class)
                .setParameter("entryId", entryId)
                .getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<MarcaVehicle> getAll() {
        return getAll(null, null);
    }

    public List<MarcaVehicle> getAll(Integer offset, Integer maxResults) {
        List<MarcaVehicle> results = new ArrayList<>();
        offset = offset == null ? 0 : offset;
        maxResults = maxResults == null ? 500 : maxResults;
        try {
            results = entityManager.createQuery(
                    "SELECT mv FROM MarcaVehicle mv ORDER BY mv.id", 
                    MarcaVehicle.class)
                .setFirstResult(offset)
                .setMaxResults(maxResults)
                .getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return results;
    }

    public MarcaVehicle getByName(String brandName) {
        try {
            return entityManager.createQuery(
                    "SELECT mv FROM MarcaVehicle mv WHERE mv.brandName = :brandName",
                    MarcaVehicle.class)
                .setParameter("brandName", brandName)
                .setMaxResults(1)
                .getSingleResult();
        } catch (Exception ex) {
            System.err.println("⚠ No s'ha trobat cap Marca amb brandName=" + brandName);
            return null;
        }
    }

    public MarcaVehicle create(MarcaVehicle entry) {
        try {
            entityManager.persist(entry);
            return entry;
        } catch (Exception ex) {
            System.err.println("Error a create(): " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public MarcaVehicle update(MarcaVehicle entry) {
        try {
            return entityManager.merge(entry);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public MarcaVehicle delete(MarcaVehicle entry) {
        try {
            MarcaVehicle managed = entityManager.merge(entry); // assegurar que està gestionat
            entityManager.remove(managed);
            return entry;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public Integer delete(Integer entryId) { 
        try { MarcaVehicle reference = entityManager.getReference(MarcaVehicle.class, entryId); 
        entityManager.remove(reference); 
        return entryId; 
    } catch (Exception ex) { 
        ex.printStackTrace(); 
        return -1; 
    } 
  }
}

