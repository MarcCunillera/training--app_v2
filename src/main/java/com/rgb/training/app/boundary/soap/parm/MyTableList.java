package com.rgb.training.app.boundary.soap.parm;

import com.rgb.training.app.data.model.MyTable;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LuisCarlosGonzalez
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "MyTableList")
public class MyTableList {

    @XmlElementWrapper(name = "values")
    @XmlElement(name = "MyTable")
    private List<MyTable> values = null;

    public List<MyTable> getValues() {
        if (values == null) {
            values = new ArrayList<>();
        }
        return values;
    }

    public void setValues(List<MyTable> values) {
        this.values = values;
    }
}
