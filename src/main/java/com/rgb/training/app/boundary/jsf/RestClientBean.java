package com.rgb.training.app.boundary.jsf;

import com.rgb.training.app.data.model.MarcaVehicle;
import com.rgb.training.app.data.model.ModelVehicles;
import com.rgb.training.app.data.repository.MarcaVehicleJTARepository;
import com.rgb.training.app.data.repository.ModelVehicleJTARepository;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named("restClientBean")
@SessionScoped
public class RestClientBean implements Serializable {

    @EJB
    private MarcaVehicleJTARepository marcaRepo;

    @EJB
    private ModelVehicleJTARepository modelRepo;

    @Inject
    private LoginBean loginBean;

    private String selectedTable;
    private String selectedOp; // "get","post"

    private String result = "";
    private List<?> resultList; // GET ALL
    private Integer editingId = null;

    // --- MarcaVehicle fields ---
    private Integer marcaId;
    private String marcaName;

    // --- ModelVehicles fields ---
    private Integer modelId;
    private String modelName;
    private Integer modelBrandId;

    // --- Mètode principal ---
    public void execute() {
        try {
            result = "";
            resultList = null;

            if (selectedTable == null || selectedOp == null) {
                result = "Selecciona tabla i operació";
                return;
            }

            switch (selectedTable.toLowerCase()) {
                case "marcavehicle":
                    handleMarcaVehicle();
                    break;
                case "modelvehicles":
                    handleModelVehicles();
                    break;
                default:
                    result = "Tabla no válida";
            }
        } catch (Exception e) {
            result = "Error: " + e.getMessage();
        }
    }

    private void handleMarcaVehicle() {
        switch (selectedOp.toLowerCase()) {
            case "get":
                resultList = marcaRepo.getAll(0, 100);
                break;

            case "post":
                MarcaVehicle nuevo = new MarcaVehicle();
                nuevo.setBrandName(marcaName);
                MarcaVehicle creado = marcaRepo.create(nuevo);
                result = (creado != null) ? "Creado: " + creado.toString() : "Error al crear";
                break;

            case "put":
                if (marcaId == null) {
                    result = "ID requerido para actualizar";
                    return;
                }
                MarcaVehicle actual = marcaRepo.get(marcaId);
                if (actual != null) {
                    actual.setBrandName(marcaName);
                    MarcaVehicle actualizado = marcaRepo.update(actual);
                    result = (actualizado != null) ? "Actualizado: " + actualizado.toString() : "Error al actualizar";
                } else {
                    result = "No encontrado para actualizar";
                }
                break;

            case "delete":
                if (marcaId == null) {
                    result = "ID requerido para borrar";
                    return;
                }
                Integer borrado = marcaRepo.delete(marcaId);
                result = (borrado > 0) ? "Borrado ID=" + marcaId : "Error al borrar";
                break;

            default:
                result = "Operación no válida";
        }
    }

    private void handleModelVehicles() {
        switch (selectedOp.toLowerCase()) {
            case "get":
                resultList = modelRepo.getAll(0, 100);
                break;

            case "post":
                if (modelBrandId == null) {
                    result = "Brand ID requerido para crear";
                    return;
                }
                ModelVehicles nuevo = new ModelVehicles();
                nuevo.setModelName(modelName);
                MarcaVehicle marca = marcaRepo.get(modelBrandId);
                nuevo.setBrandId(marca);
                ModelVehicles creado = modelRepo.create(nuevo);
                result = (creado != null) ? "Creado: " + creado.toString() : "Error al crear";
                break;

            case "put":
                if (modelId == null || modelBrandId == null) {
                    result = "ID del model y Brand ID requeridos para actualizar";
                    return;
                }
                ModelVehicles actual = modelRepo.get(modelId);
                if (actual != null) {
                    actual.setModelName(modelName);
                    MarcaVehicle marcaUpd = marcaRepo.get(modelBrandId);
                    actual.setBrandId(marcaUpd);
                    ModelVehicles actualizado = modelRepo.update(actual);
                    result = (actualizado != null) ? "Actualizado: " + actualizado.toString() : "Error al actualizar";
                } else {
                    result = "No encontrado para actualizar";
                }
                break;

            case "delete":
                if (modelId == null) {
                    result = "ID del model requerido para borrar";
                    return;
                }
                Integer borrado = modelRepo.delete(modelId);
                result = (borrado > 0) ? "Borrado ID=" + modelId : "Error al borrar";
                break;
            default:
                result = "Operación no válida";
        }
    }

    // --- Autenticació ---
    public void redirectIfNotLoggedIn() throws IOException {
        if (!loginBean.isLoggedIn()) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        }
    }

    // --- CRUD des de la taula ---
    public String startEditing(Object item) {
        if (item instanceof MarcaVehicle) {
            editingId = ((MarcaVehicle) item).getId();
        } else if (item instanceof ModelVehicles) {
            editingId = ((ModelVehicles) item).getId();
        }
        return null;
    }

    public String saveEdit(Object item) {
        try {
            if (item instanceof MarcaVehicle) {
                MarcaVehicle marca = (MarcaVehicle) item;
                marcaId = marca.getId();
                marcaName = marca.getBrandName();
                selectedTable = "marcavehicle";
                selectedOp = "put"; // 👈 Indiquem que és una actualització
            } else if (item instanceof ModelVehicles) {
                ModelVehicles model = (ModelVehicles) item;
                modelId = model.getId();
                modelName = model.getModelName();
                modelBrandId = model.getBrandId().getId();
                selectedTable = "modelvehicles";
                selectedOp = "put"; // 👈 Indiquem que és una actualització
            }

            execute(); // 👈 Fa el PUT (update)
            selectedOp = "get";
            execute(); // 👈 Refresca la llista
            editingId = null;
            result = "Canvis guardats correctament.";
        } catch (Exception e) {
            result = "Error guardant: " + e.getMessage();
        }
        return null;
    }

    public String cancelEdit() {
        editingId = null;
        result = "";
        return null;
    }

    public String deleteItem(Object item) {
        try {
            if (item instanceof MarcaVehicle) {
                MarcaVehicle marca = (MarcaVehicle) item;
                marcaId = marca.getId();
                selectedTable = "marcavehicle";
                selectedOp = "delete";
            } else if (item instanceof ModelVehicles) {
                ModelVehicles model = (ModelVehicles) item;
                modelId = model.getId();
                selectedTable = "modelvehicles";
                selectedOp = "delete";
            }

            execute(); // DELETE
            selectedOp = "get";
            execute(); // refresca
            result = "Registre eliminat correctament.";
        } catch (Exception e) {
            result = "Error al eliminar: " + e.getMessage();
        }
        return null;
    }

    // --- Getters i Setters ---
    public String getSelectedTable() {
        return selectedTable;
    }

    public void setSelectedTable(String selectedTable) {
        this.selectedTable = selectedTable;
    }

    public String getSelectedOp() {
        return selectedOp;
    }

    public void setSelectedOp(String selectedOp) {
        this.selectedOp = selectedOp;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<?> getResultList() {
        return resultList;
    }

    public void setResultList(List<?> resultList) {
        this.resultList = resultList;
    }

    public Integer getMarcaId() {
        return marcaId;
    }

    public void setMarcaId(Integer marcaId) {
        this.marcaId = marcaId;
    }

    public String getMarcaName() {
        return marcaName;
    }

    public void setMarcaName(String marcaName) {
        this.marcaName = marcaName;
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Integer getModelBrandId() {
        return modelBrandId;
    }

    public void setModelBrandId(Integer modelBrandId) {
        this.modelBrandId = modelBrandId;
    }

    public Integer getEditingId() {
        return editingId;
    }

    public void setEditingId(Integer editingId) {
        this.editingId = editingId;
    }
}
