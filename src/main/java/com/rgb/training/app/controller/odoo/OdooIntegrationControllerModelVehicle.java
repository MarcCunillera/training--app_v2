package com.rgb.training.app.controller.odoo;

import com.rgb.training.app.common.odoo.connection.OdooConnection;
import com.rgb.training.app.common.odoo.model.OdooModelVehicle;
import com.rgb.training.app.common.odoo.types.Domain;
import com.rgb.training.app.common.odoo.types.Record;
import com.rgb.training.app.common.odoo.types.Recordset;
import com.rgb.training.app.common.odoo.types.Values;
import com.rgb.training.app.config.CustomConfig;
import com.rgb.training.app.data.model.MarcaVehicle;
import com.rgb.training.app.data.model.ModelVehicles;
import jakarta.ejb.Stateless;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador per a la integració amb Odoo de models de vehicles
 */
@Stateless
public class OdooIntegrationControllerModelVehicle {

    private OdooConnection odooConnection;

    public List<ModelVehicles> getAllModelVehicles() throws Exception {
        List<ModelVehicles> result = new ArrayList<>();
        try {
            Domain domain = Domain.create();
            Values fields = modelFields();
            Recordset records = odoo().search_read("concessionaire.model.type", domain, fields);

            for (Record record : records) {
                OdooModelVehicle odooModel = new OdooModelVehicle();
                odooModel.initializeFromHashMap(record.getFields());

                Object[] brand = null;
                Object brandObj = record.get("brand_id");
                if (brandObj instanceof Object[]) {
                    brand = (Object[]) brandObj;
                    odooModel.setBrand_id(((Number) brand[0]).intValue());
                }

                result.add(mapOdooModelToModelVehicle(odooModel, brand));
            }
        } catch (Exception error) {
            System.out.println(error.getMessage());
            throw new Exception(error);
        }
        return result;
    }

    public ModelVehicles getModelVehicleByName(String name) throws Exception {
        try {
            Domain domain = Domain.create().filter("name", "=", name);
            Values fields = modelFields();
            Recordset records = odoo().search_read("concessionaire.model.type", domain, fields);

            if (!records.isEmpty()) {
                Record record = records.get(0);
                OdooModelVehicle odooModel = new OdooModelVehicle();
                odooModel.initializeFromHashMap(record.getFields());

                Object[] brand = null;
                Object brandObj = record.get("brand_id");
                if (brandObj instanceof Object[]) {
                    brand = (Object[]) brandObj;
                    odooModel.setBrand_id(((Number) brand[0]).intValue());
                }

                return mapOdooModelToModelVehicle(odooModel, brand);
            }
        } catch (Exception error) {
            System.out.println(error.getMessage());
            throw new Exception(error);
        }
        return null;
    }

    public Integer createModelVehicle(ModelVehicles model) throws Exception {
        Integer modelId = null;
        try {
            OdooModelVehicle odooModel = new OdooModelVehicle();
            odooModel.setName(model.getModelName());
            if (model.getBrandId() != null) {
                odooModel.setBrand_id(model.getBrandId().getId());
            }

            Values fields = Values.create(odooModel.getFieldsAsHashMap());
            modelId = odoo().create("concessionaire.model.type", fields);
        } catch (Exception error) {
            System.out.println(error.getMessage());
            throw new Exception(error);
        }
        return modelId;
    }

    public Boolean updateModelVehicle(ModelVehicles model) throws Exception {
        Boolean result = null;
        try {
            OdooModelVehicle odooModel = new OdooModelVehicle();
            odooModel.setId(model.getId());
            odooModel.setName(model.getModelName());
            odooModel.setBrand_id(model.getBrandId() != null ? model.getBrandId().getId() : null);

            Values ids = Values.create(model.getId().intValue());
            Values fields = Values.create(ids, odooModel.getFieldsAsHashMap());

            result = odoo().update("concessionaire.model.type", fields);
        } catch (Exception error) {
            System.out.println(error.getMessage());
            throw new Exception(error);
        }
        return result;
    }

    public Boolean deleteModelsByIds(List<Integer> ids) throws Exception {
        if (ids == null || ids.isEmpty()) return false;
        try {
            return odoo().unlink("concessionaire.model.type", ids);
        } catch (Exception error) {
            System.out.println(error.getMessage());
            throw new Exception(error);
        }
    }

    private ModelVehicles mapOdooModelToModelVehicle(OdooModelVehicle odooModel, Object[] brand) {
        ModelVehicles model = new ModelVehicles();
        model.setId(odooModel.getId());
        model.setModelName(odooModel.getName());

        if (brand != null && brand.length > 1) {
            MarcaVehicle marca = new MarcaVehicle();
            marca.setId(((Number) brand[0]).intValue());
            marca.setBrandName((String) brand[1]);
            model.setBrandId(marca);
        }

        return model;
    }

    private Values modelFields() {
        return Values.create("id", "name", "brand_id");
    }

    synchronized OdooConnection odoo() throws Exception {
        if (this.odooConnection == null) {
            try {
                this.odooConnection = new OdooConnection(
                        CustomConfig.getODOO_URL(),
                        CustomConfig.getODOO_DB_NAME(),
                        CustomConfig.getODOO_USER_ID(),
                        CustomConfig.getODOO_PASSWORD()
                );
            } catch (Exception error) {
                System.out.println("Can't create Odoo connection.");
                error.printStackTrace();
                throw new Exception(error);
            }
        }
        return this.odooConnection;
    }
}

