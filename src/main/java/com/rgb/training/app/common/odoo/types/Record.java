package com.rgb.training.app.common.odoo.types;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Record extends HashMap<Object, Object> {

    private static final long serialVersionUID = 1L;
    private static final String ODOO_DATE_FORMAT = "yyyy-MM-dd";

    public static Record fromObject(Object row) {
        Map<?, ?> map = (Map<?, ?>) row;
        return fromMap(map);
    }

    public static Record fromMap(Map<?, ?> row) {
        Record record = new Record();
        if (row != null) {
            row.forEach(record::put);
        }
        return record;
    }

    public static Record fromKeyValue(Object key, Object value) {
        Record record = new Record();
        record.put(key, value);
        return record;
    }

    public Boolean isNull(Object key) {
        Object value = get(key);
        return value == null || (value instanceof Boolean && !(Boolean) value);
    }

    public String getString(Object key) {
        return !isNull(key) ? (String) get(key) : null;
    }

    public Date getDate(Object key) {
        if (!isNull(key)) {
            try {
                return new SimpleDateFormat(ODOO_DATE_FORMAT).parse(get(key).toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Double getDouble(Object key) {
        Object value = get(key);
        return value instanceof Double ? (Double) value : null;
    }

    public Integer getInteger(Object key) {
        Object value = get(key);
        return value instanceof Integer ? (Integer) value : null;
    }

    // Many2One
    public Integer getId(Object key) {
        Object value = get(key);
        if (value instanceof Object[]) {
            Object[] id = (Object[]) value;
            if (id.length >= 1 && id[0] instanceof Integer) {
                return (Integer) id[0];
            }
        }
        return null;
    }

    public String getIdName(Object key) {
        Object value = get(key);
        if (value instanceof Object[]) {
            Object[] id = (Object[]) value;
            if (id.length >= 2 && id[1] instanceof String) {
                return (String) id[1];
            }
        }
        return null;
    }

    // One2Many & Many2Many
    public Integer[] getIds(Object key) {
        Object values = get(key);
        if (values instanceof Object[]) {
            return buildIds((Object[]) values);
        }
        return new Integer[0];
    }

    private Integer[] buildIds(Object[] values) {
        Integer[] ids = new Integer[values.length];
        for (int i = 0; i < values.length; i++) {
            if (values[i] instanceof Integer) {
                ids[i] = (Integer) values[i];
            } else {
                ids[i] = null;
            }
        }
        return ids;
    }

    // ✅ Implementació correcta de getFields()
    public HashMap<String, Object> getFields() {
        HashMap<String, Object> result = new HashMap<>();
        for (Map.Entry<Object, Object> entry : this.entrySet()) {
            if (entry.getKey() != null) {
                result.put(entry.getKey().toString(), entry.getValue());
            }
        }
        return result;
    }

}
