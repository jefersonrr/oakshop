/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USUARIO
 */
@Entity
@Table(name = "Carrito")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Carrito.findAll", query = "SELECT c FROM Carrito c")
    , @NamedQuery(name = "Carrito.findByIdProducto", query = "SELECT c FROM Carrito c WHERE c.carritoPK.idProducto = :idProducto")
    , @NamedQuery(name = "Carrito.findByCantidad", query = "SELECT c FROM Carrito c WHERE c.cantidad = :cantidad")
    , @NamedQuery(name = "Carrito.findByIdCliente", query = "SELECT c FROM Carrito c WHERE c.carritoPK.idCliente = :idCliente")})
public class Carrito implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CarritoPK carritoPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad")
    private int cantidad;
    @JoinColumn(name = "idCliente", referencedColumnName = "cedula", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Persona persona;
    @JoinColumn(name = "idProducto", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Producto producto;

    public Carrito() {
    }

    public Carrito(CarritoPK carritoPK) {
        this.carritoPK = carritoPK;
    }

    public Carrito(CarritoPK carritoPK, int cantidad) {
        this.carritoPK = carritoPK;
        this.cantidad = cantidad;
    }

    public Carrito(int idProducto, String idCliente) {
        this.carritoPK = new CarritoPK(idProducto, idCliente);
    }

    public CarritoPK getCarritoPK() {
        return carritoPK;
    }

    public void setCarritoPK(CarritoPK carritoPK) {
        this.carritoPK = carritoPK;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (carritoPK != null ? carritoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Carrito)) {
            return false;
        }
        Carrito other = (Carrito) object;
        if ((this.carritoPK == null && other.carritoPK != null) || (this.carritoPK != null && !this.carritoPK.equals(other.carritoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.Carrito[ carritoPK=" + carritoPK + " ]";
    }
    
}
