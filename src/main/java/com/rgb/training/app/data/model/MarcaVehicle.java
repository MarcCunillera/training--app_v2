package com.rgb.training.app.data.model;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author marccunillera
 */
@Entity
@Table(name = "marcavehicle", schema = "public")
@XmlType(name = "MarcaVehicle")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class MarcaVehicle implements Serializable {

    private static final int serialVersionUID = 1;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "brand_name", nullable = false, length = 100)
    private String brandName;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "brandId")
    
    @XmlTransient
    private List<Vehicles> vehiclesCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "brandId")
    
    @XmlTransient
    private List<ModelVehicles> modelvehiclesCollection;

    public MarcaVehicle() {
    }

    public MarcaVehicle(Integer id) {
        this.id = id;
    }

    public MarcaVehicle(Integer id, String brandName) {
        this.id = id;
        this.brandName = brandName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @JsonbTransient
    @XmlTransient
    public Collection<Vehicles> getVehiclesCollection() {
        return vehiclesCollection;
    }

    public void setVehiclesCollection(List<Vehicles> vehiclesCollection) {
        this.vehiclesCollection = vehiclesCollection;
    }

    @JsonbTransient
    @XmlTransient
    public Collection<ModelVehicles> getModelvehiclesCollection() {
        return modelvehiclesCollection;
    }

    public void setModelvehiclesCollection(List<ModelVehicles> modelvehiclesCollection) {
        this.modelvehiclesCollection = modelvehiclesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarcaVehicle)) {
            return false;
        }
        MarcaVehicle other = (MarcaVehicle) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MarcaVehicle{" + "id=" + id + "BrandName=" + brandName + '}';
    }
    
}
