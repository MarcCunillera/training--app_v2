package com.rgb.training.app.controller.mytable;

import com.rgb.training.app.controller.odoo.OdooIntegrationControllerMarcaVehicle;
import com.rgb.training.app.controller.odoo.OdooIntegrationControllerModelVehicle;
import com.rgb.training.app.data.model.MarcaVehicle;
import com.rgb.training.app.data.model.ModelVehicles;
import com.rgb.training.app.data.repository.MarcaVehicleJTARepository;
import com.rgb.training.app.data.repository.ModelVehicleJTARepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class Java2OdooSyncController {

    @EJB
    private OdooIntegrationControllerMarcaVehicle marcaController;

    @EJB
    private OdooIntegrationControllerModelVehicle modelController;

    @EJB
    private MarcaVehicleJTARepository marcaVehicleRepo;

    @EJB
    private ModelVehicleJTARepository modelVehicleRepo;

    public void sync() throws IOException {   
        try {
            // 🔹 1 - Obtenir dades locals
            List<MarcaVehicle> localMarcas = marcaVehicleRepo.getAll();
            List<ModelVehicles> localModels = modelVehicleRepo.getAll();

            // 🔹 2 - Sincronitzar Marques a Odoo
            for (MarcaVehicle localMarca : localMarcas) {
                try {
                    MarcaVehicle marcaOdoo = marcaController.getMarcaVehicleByName(localMarca.getBrandName());

                    if (marcaOdoo == null) {
                        try {
                            marcaController.createMarcaVehicle(localMarca);
                            System.out.println("Marca creada a Odoo: " + localMarca.getBrandName());
                        } catch (Exception e) {
                            if (e.getMessage().contains("Tag name must be unique")) {
                                System.out.println("Marca ja existeix a Odoo: " + localMarca.getBrandName());
                            } else {
                                throw e;
                            }
                        }
                    } else {
                        marcaController.updateMarcaVehicle(localMarca);
                        System.out.println("Marca actualitzada a Odoo: " + localMarca.getBrandName());
                    }
                } catch (Exception e) {
                    System.err.println("Error sincronitzant Marca: " + localMarca.getBrandName() + " - " + e.getMessage());
                }
            }

            // 🔹 3 - Eliminar Marques de Odoo que no existeixen localment
            List<Integer> marcaIdsToDelete = new ArrayList<>();
            List<MarcaVehicle> odooMarcas = marcaController.getAllMarcaVehicles();

            for (MarcaVehicle odooMarca : odooMarcas) {
                boolean existsLocally = localMarcas.stream()
                        .anyMatch(local -> local.getBrandName().equalsIgnoreCase(odooMarca.getBrandName()));

                if (!existsLocally) {
                    marcaIdsToDelete.add(odooMarca.getId());
                    System.out.println("Marca marcada per eliminar a Odoo: " + odooMarca.getBrandName());
                }
            }

            if (!marcaIdsToDelete.isEmpty()) {
                try {
                    marcaController.deleteMarcaVehicleByIds(marcaIdsToDelete);
                    System.out.println("Eliminades marques de Odoo: " + marcaIdsToDelete);
                } catch (Exception e) {
                    System.err.println("Error eliminant marques a Odoo: " + e.getMessage());
                }
            }

            // 🔹 4 - Sincronitzar Models a Odoo
            for (ModelVehicles localModel : localModels) {
                try {
                    ModelVehicles modelOdoo = modelController.getModelVehicleByName(localModel.getModelName());

                    if (modelOdoo == null) {
                        modelController.createModelVehicle(localModel);
                        System.out.println("Model creat a Odoo: " + localModel.getModelName());
                    } else {
                        modelController.updateModelVehicle(localModel);
                        System.out.println("Model actualitzat a Odoo: " + localModel.getModelName());
                    }
                } catch (Exception e) {
                    System.err.println("Error sincronitzant Model: " + localModel.getModelName() + " → " + e.getMessage());
                }
            }

            // 🔹 5 - Eliminar Models de Odoo que no existeixen localment
            List<Integer> idsToDelete = new ArrayList<>();
            List<ModelVehicles> odooModels = modelController.getAllModelVehicles();

            for (ModelVehicles odooModel : odooModels) {
                boolean existsLocally = localModels.stream()
                        .anyMatch(local ->
                                local.getModelName().equalsIgnoreCase(odooModel.getModelName()) &&
                                local.getBrandId() != null && odooModel.getBrandId() != null &&
                                local.getBrandId().getBrandName().equalsIgnoreCase(odooModel.getBrandId().getBrandName())
                        );

                if (!existsLocally) {
                    idsToDelete.add(odooModel.getId());
                    System.out.println("Model marcat per eliminar de Odoo: " + odooModel.getModelName());
                }
            }

            if (!idsToDelete.isEmpty()) {
                try {
                    modelController.deleteModelsByIds(idsToDelete);
                    System.out.println("Eliminats models de Odoo: " + idsToDelete);
                } catch (Exception e) {
                    System.err.println("Error eliminant models a Odoo: " + e.getMessage());
                }
            }

            System.out.println("Sincronització Java → Odoo completada correctament\n");

        } catch (Exception e) {
            System.err.println("Error global de sincronització: " + e.getMessage());
            e.printStackTrace();
        }
    }
}


