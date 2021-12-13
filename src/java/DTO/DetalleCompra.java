/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Cristian
 */
@Entity
@Table(name = "Detalle_Compra")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleCompra.findAll", query = "SELECT d FROM DetalleCompra d")
    , @NamedQuery(name = "DetalleCompra.findByIdCompra", query = "SELECT d FROM DetalleCompra d WHERE d.idCompra = :idCompra")
    , @NamedQuery(name = "DetalleCompra.findByCantidad", query = "SELECT d FROM DetalleCompra d WHERE d.cantidad = :cantidad")})
public class DetalleCompra implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idCompra")
    private Integer idCompra;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad")
    private int cantidad;
    @JoinColumn(name = "idProducto", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Producto idProducto;
    @JoinColumn(name = "idCompra", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Compra compra;

    public DetalleCompra() {
    }

    public DetalleCompra(Integer idCompra) {
        this.idCompra = idCompra;
    }

    public DetalleCompra(Integer idCompra, int cantidad) {
        this.idCompra = idCompra;
        this.cantidad = cantidad;
    }

    public Integer getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Integer idCompra) {
        this.idCompra = idCompra;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCompra != null ? idCompra.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleCompra)) {
            return false;
        }
        DetalleCompra other = (DetalleCompra) object;
        if ((this.idCompra == null && other.idCompra != null) || (this.idCompra != null && !this.idCompra.equals(other.idCompra))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.DetalleCompra[ idCompra=" + idCompra + " ]";
    }
    
}
