package com.rgb.training.app.data.model;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "modelvehicles", schema = "public")
@XmlType(name = "ModelVehicles")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class ModelVehicles implements Serializable {

    private static final int serialVersionUID = 1;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "model_name", nullable = false, length = 100)
    private String modelName;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "modelId")
    
    @XmlTransient
    private List<Vehicles> vehiclesCollection;
    
    @JoinColumn(name = "brand_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private MarcaVehicle brandId;
    
    public ModelVehicles() {
    }

    public ModelVehicles(Integer id) {
        this.id = id;
    }

    public ModelVehicles(Integer id, String modelName) {
        this.id = id;
        this.modelName = modelName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    @JsonbTransient
    @XmlTransient
    public Collection<Vehicles> getVehiclesCollection() {
        return vehiclesCollection;
    }

    public void setVehiclesCollection(List<Vehicles> vehiclesCollection) {
        this.vehiclesCollection = vehiclesCollection;
    }

    public MarcaVehicle getBrandId() {
        return brandId;
    }

    public void setBrandId(MarcaVehicle brandId) {
        this.brandId = brandId;
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
        if (!(object instanceof ModelVehicles)) {
            return false;
        }
        ModelVehicles other = (ModelVehicles) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ModelVehicles{" + "id=" + id + "ModelName=" + modelName + "BrandId=" + brandId +'}';
    }
    
}
