package com.rgb.training.app.controller.odoo;

import com.rgb.training.app.common.odoo.connection.OdooConnection;
import com.rgb.training.app.common.odoo.model.OdooMarcaVehicle;
import com.rgb.training.app.common.odoo.types.Domain;
import com.rgb.training.app.common.odoo.types.Record;
import com.rgb.training.app.common.odoo.types.Recordset;
import com.rgb.training.app.common.odoo.types.Values;
import com.rgb.training.app.config.CustomConfig;
import com.rgb.training.app.data.model.MarcaVehicle;
import jakarta.ejb.Stateless;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador per a la integració amb Odoo de marques de vehicles
 */
@Stateless
public class OdooIntegrationControllerMarcaVehicle {

    private OdooConnection odooConnection;

    public List<MarcaVehicle> getAllMarcaVehicles() throws Exception {
        List<MarcaVehicle> result = new ArrayList<>();
        try {
            Domain domain = Domain.create();
            Values fields = this.marcaFields();
            Recordset records = odoo().search_read("concessionaire.brand.type", domain, fields);

            for (Record record : records) {
                OdooMarcaVehicle odooMarca = new OdooMarcaVehicle();
                odooMarca.initializeFromHashMap(record.getFields());
                result.add(mapOdooMarcaToMarcaVehicle(odooMarca));
            }
        } catch (Exception error) {
            System.out.println(error.getMessage());
            throw new Exception(error);
        }
        return result;
    }

    public MarcaVehicle getMarcaVehicleByName(String name) throws Exception {
        try {
            Domain domain = Domain.create().filter("name", "=", name);
            Values fields = this.marcaFields();
            Recordset records = odoo().search_read("concessionaire.brand.type", domain, fields);

            if (!records.isEmpty()) {
                Record record = records.get(0);
                OdooMarcaVehicle odooMarca = new OdooMarcaVehicle();
                odooMarca.initializeFromHashMap(record.getFields());
                return mapOdooMarcaToMarcaVehicle(odooMarca);
            }
        } catch (Exception error) {
            System.out.println(error.getMessage());
            throw new Exception(error);
        }
        return null;
    }

    public Integer createMarcaVehicle(MarcaVehicle marca) throws Exception {
        Integer marcaId = null;
        try {
            OdooMarcaVehicle odooMarca = new OdooMarcaVehicle();
            odooMarca.setName(marca.getBrandName());

            Values fields = Values.create(odooMarca.getFieldsAsHashMap());
            marcaId = odoo().create("concessionaire.brand.type", fields);
        } catch (Exception error) {
            System.out.println(error.getMessage());
            throw new Exception(error);
        }
        return marcaId;
    }

    public Boolean updateMarcaVehicle(MarcaVehicle marca) throws Exception {
        Boolean result = null;
        try {
            OdooMarcaVehicle odooMarca = new OdooMarcaVehicle();
            odooMarca.setName(marca.getBrandName());

            Values ids = Values.create(marca.getId().intValue());
            Values fields = Values.create(ids, odooMarca.getFieldsAsHashMap());

            result = odoo().update("concessionaire.brand.type", fields);
        } catch (Exception error) {
            System.out.println(error.getMessage());
            throw new Exception(error);
        }
        return result;
    }

        public Boolean deleteMarcaVehicleByIds(List<Integer> ids) throws Exception {
        if (ids == null || ids.isEmpty()) return false;
        try {
            return odoo().unlink("concessionaire.brand.type", ids);
        } catch (Exception error) {
            System.out.println(error.getMessage());
            throw new Exception(error);
        }
    }

    private MarcaVehicle mapOdooMarcaToMarcaVehicle(OdooMarcaVehicle odooMarca) {
        MarcaVehicle marca = new MarcaVehicle();
        marca.setId(odooMarca.getId());
        marca.setBrandName(odooMarca.getName());
        return marca;
    }

    private Values marcaFields() {
        return Values.create(OdooMarcaVehicle.ID_TAG, OdooMarcaVehicle.NAME_TAG);
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


