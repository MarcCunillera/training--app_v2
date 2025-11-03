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

import java.util.List;

@Stateless
public class Odoo2JavaSyncController {

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
            // 🔹 1 - Obtenemos datos de Odoo
            List<MarcaVehicle> odooMarcas = marcaController.getAllMarcaVehicles();
            List<ModelVehicles> odooModels = modelController.getAllModelVehicles();

            // 🔹 2 - Obtenemos datos locales
            List<MarcaVehicle> localMarcas = marcaVehicleRepo.getAll();
            List<ModelVehicles> localModels = modelVehicleRepo.getAll();

            // 🔹 3 - Sincronizar Marcas
            for (MarcaVehicle odooMarca : odooMarcas) {
                try {
                    MarcaVehicle localMarca = marcaVehicleRepo.getByName(odooMarca.getBrandName());
                    if (localMarca == null) {
                        MarcaVehicle nuevaMarca = new MarcaVehicle();
                        nuevaMarca.setBrandName(odooMarca.getBrandName());
                        marcaVehicleRepo.create(nuevaMarca);
                        System.out.println("Marca creada en local: " + odooMarca.getBrandName());
                    } else {
                        odooMarca.setId(localMarca.getId()); // mantener ID local
                        marcaVehicleRepo.update(localMarca);
                        System.out.println("Marca actualizada en local: " + odooMarca.getBrandName());
                    }
                } catch (Exception e) {
                    System.err.println("Error sincronizando Marca [" + odooMarca.getBrandName() + "]: " + e.getMessage());
                }
            }

            // 🔹 3.1 - Eliminar marcas locales que no existen en Odoo
            for (MarcaVehicle localMarca : localMarcas) {
                boolean existsInOdoo = odooMarcas.stream()
                        .anyMatch(odoo -> odoo.getBrandName().equals(localMarca.getBrandName()));
                if (!existsInOdoo) {
                    marcaVehicleRepo.delete(localMarca.getId());
                    System.out.println("Marca eliminada de la BD local: " + localMarca.getBrandName());
                }
            }

            // 🔹 4 - Sincronizar Modelos
            for (ModelVehicles odooModel : odooModels) {
                try {
                    ModelVehicles localModel = modelVehicleRepo.getByName(odooModel.getModelName());
                    MarcaVehicle marcaLocal = null;
                    if (odooModel.getBrandId() != null && odooModel.getBrandId().getBrandName() != null) {
                        marcaLocal = marcaVehicleRepo.getByName(odooModel.getBrandId().getBrandName());
                    }

                    if (marcaLocal != null) {
                        odooModel.setBrandId(marcaLocal); // assignem la marca vinculada
                    }

                    if (localModel == null) {
                        ModelVehicles nuevoModel = new ModelVehicles();
                        nuevoModel.setModelName(odooModel.getModelName());
                        nuevoModel.setBrandId(marcaLocal);
                        modelVehicleRepo.create(nuevoModel);
                        System.out.println("Modelo creado en local: " + nuevoModel.getModelName());
                    } else {
                        localModel.setModelName(odooModel.getModelName()); // 🔹 copiar dades
                        localModel.setBrandId(marcaLocal);
                        modelVehicleRepo.update(localModel);
                        System.out.println("Modelo actualizado en local: " + localModel.getModelName());
                    }
                } catch (Exception e) {
                    System.err.println("Error sincronizando Modelo [" + odooModel.getModelName() + "]: " + e.getMessage());
                }
            }

            // 🔹 4.1 - Eliminar modelos locales que no existen en Odoo
            for (ModelVehicles localModel : localModels) {
                boolean existsInOdoo = odooModels.stream()
                        .anyMatch(odoo -> odoo.getModelName().equals(localModel.getModelName()) &&
                                odoo.getBrandId() != null &&
                                localModel.getBrandId() != null &&
                                odoo.getBrandId().getBrandName().equals(localModel.getBrandId().getBrandName())
                        );
                if (!existsInOdoo) {
                    modelVehicleRepo.delete(localModel.getId());
                    System.out.println("Modelo eliminado de la BD local: " + localModel.getModelName());
                }
            }

            System.out.println("🚀 Sincronización Odoo → Java completada correctamente\n");

        } catch (Exception e) {
            System.err.println("💥 Error global en la sincronización: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
