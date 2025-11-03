package com.rgb.training.app.config;

import com.rgb.training.app.data.model.MarcaVehicle;
import com.rgb.training.app.data.model.ModelVehicles;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Scanner;

/**
 *
 * @author marccunillera
 */
public class ObjectDB {

    public static void main(String[] args) {

        EntityManagerFactory emf
                = Persistence.createEntityManagerFactory("ObjectDB");
        EntityManager em = emf.createEntityManager();
        Scanner key = new Scanner(System.in);
        try {
            // Crea una Marca
            String name = "";
            MarcaVehicle marca = new MarcaVehicle();
            ModelVehicles model = new ModelVehicles();

            for (int i = 0; i < 10; i++) {
                System.out.println("Escriu la Marca");
                System.out.print("NOM: ");
                name = key.nextLine();
                
                marca = new MarcaVehicle();
                marca.setBrandName(name);
                
                System.out.println("Escriu el Model");
                System.out.print("NOM: ");
                name = key.nextLine();
                
                // Crear un Model i asigna la Marca
                model = new ModelVehicles();
                model.setModelName(name);
                model.setBrandId(marca);

                // Guarda a la base de dades
                em.getTransaction().begin();
                em.persist(marca);
                em.persist(model);
                em.getTransaction().commit();
            }

            System.out.println("Guardat: " + marca.getBrandName() + " - " + model.getModelName());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
