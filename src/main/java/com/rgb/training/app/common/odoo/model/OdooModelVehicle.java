package com.rgb.training.app.common.odoo.model;

import com.rgb.training.app.common.odoo.utils.DataTypeParser;
import jakarta.json.bind.annotation.JsonbTransient;
import java.util.HashMap;
import java.util.Map;

public class OdooModelVehicle {

    public static final String ID_TAG = "id";
    public static final String NAME_TAG = "name";
    public static final String BRAND_ID_TAG = "brand_id"; // camp de relació

    private Integer id;
    private String name;
    private Integer brand_id; // Marca a la que pertany

    public OdooModelVehicle() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(Integer brand_id) {
        this.brand_id = brand_id;
    }

    /**
     * Converteix l'objecte a un HashMap de camps (campo -> valor)
     * @return 
     */
    @JsonbTransient
    public Map<String, Object> getFieldsAsHashMap() {
        Map<String, Object> result = new HashMap<>();
        if (id != null) result.put(ID_TAG, id);
        if (name != null) result.put(NAME_TAG, name);
        if (brand_id != null) result.put(BRAND_ID_TAG, brand_id);
        return result;
    }

    /**
     * Inicialitza l'objecte a partir d'un HashMap de camps
     * @param fieldMap
     */
    public void initializeFromHashMap(HashMap<String, Object> fieldMap) {
        if (fieldMap != null) {
            this.id = DataTypeParser.objectToInteger(fieldMap.get(ID_TAG));
            this.name = DataTypeParser.objectToString(fieldMap.get(NAME_TAG));
            this.brand_id = DataTypeParser.objectToInteger(fieldMap.get(BRAND_ID_TAG));
        }
    }
}
