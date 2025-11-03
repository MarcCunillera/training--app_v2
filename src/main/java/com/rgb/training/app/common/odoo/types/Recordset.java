package com.rgb.training.app.common.odoo.types;

import java.util.ArrayList;
import java.util.List;

public class Recordset extends ArrayList<Record> {

    private static final long serialVersionUID = 1L;

    public static Recordset empty() {
        return new Recordset();
    }

    public static Recordset create(List<Object> rows) {
        Recordset set = empty();
        if (rows != null) {
            rows.forEach(row -> set.add(Record.fromObject(row)));
        }
        return set;
    }

    public Record find(String key, Object value) {
        for (Record record : this) {
            Object val = record.get(key);
            if (val != null && val.equals(value)) {
                return record;
            }
        }
        return null;
    }
}

