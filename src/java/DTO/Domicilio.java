/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Cristian
 */
@Entity
@Table(name = "Domicilio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Domicilio.findAll", query = "SELECT d FROM Domicilio d")
    , @NamedQuery(name = "Domicilio.findById", query = "SELECT d FROM Domicilio d WHERE d.id = :id")
    , @NamedQuery(name = "Domicilio.findByDireccion", query = "SELECT d FROM Domicilio d WHERE d.direccion = :direccion")
    , @NamedQuery(name = "Domicilio.findByBarrio", query = "SELECT d FROM Domicilio d WHERE d.barrio = :barrio")
    , @NamedQuery(name = "Domicilio.findByDescripcion", query = "SELECT d FROM Domicilio d WHERE d.descripcion = :descripcion")})
public class Domicilio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "direccion")
    private String direccion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "barrio")
    private String barrio;
    @Size(max = 400)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "idCiudad", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Ciudad idCiudad;
    @JoinColumn(name = "idCliente", referencedColumnName = "cedula")
    @ManyToOne(optional = false)
    private Persona idCliente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDomicilio")
    private List<Envio> envioList;

    public Domicilio() {
    }

    public Domicilio(Integer id) {
        this.id = id;
    }

    public Domicilio(Integer id, String direccion, String barrio) {
        this.id = id;
        this.direccion = direccion;
        this.barrio = barrio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Ciudad getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(Ciudad idCiudad) {
        this.idCiudad = idCiudad;
    }

    public Persona getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Persona idCliente) {
        this.idCliente = idCliente;
    }

    @XmlTransient
    public List<Envio> getEnvioList() {
        return envioList;
    }

    public void setEnvioList(List<Envio> envioList) {
        this.envioList = envioList;
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
        if (!(object instanceof Domicilio)) {
            return false;
        }
        Domicilio other = (Domicilio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Domicilio[ id=" + id + " ]";
    }
    
}
