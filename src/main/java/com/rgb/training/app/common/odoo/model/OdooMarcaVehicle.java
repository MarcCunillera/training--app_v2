package com.rgb.training.app.common.odoo.model;

import com.rgb.training.app.common.odoo.utils.DataTypeParser;
import jakarta.json.bind.annotation.JsonbTransient;
import java.util.HashMap;
import java.util.Map;

public class OdooMarcaVehicle {

    public static final String ID_TAG = "id";
    public static final String NAME_TAG = "name";

    private Integer id;
    private String name;

    public OdooMarcaVehicle() {
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

    /**
     * Converteix l'objecte a un HashMap de camps (campo -> valor)
     * @return 
     */
    @JsonbTransient
    public Map<String, Object> getFieldsAsHashMap() {
        Map<String, Object> result = new HashMap<>();
        if (id != null) {
            result.put(ID_TAG, id);
        }
        if (name != null) {
            result.put(NAME_TAG, name);
        }
        return result;
    }

    /**
     * Inicialitza l'objecte a partir d'un HashMap de camps
     * @param fieldMap
     */
    public void initializeFromHashMap(HashMap<String, Object> fieldMap) {
        if (fieldMap != null) {
            this.setId(DataTypeParser.objectToInteger(fieldMap.get(ID_TAG)));
            this.setName(DataTypeParser.objectToString(fieldMap.get(NAME_TAG)));
        }
    }
}
