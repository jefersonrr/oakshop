/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTO;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jefersonrr
 */
@Entity
@Table(name = "Metodo_Pago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MetodoPago.findAll", query = "SELECT m FROM MetodoPago m")
    , @NamedQuery(name = "MetodoPago.findById", query = "SELECT m FROM MetodoPago m WHERE m.id = :id")
    , @NamedQuery(name = "MetodoPago.findByNombre", query = "SELECT m FROM MetodoPago m WHERE m.nombre = :nombre")
    , @NamedQuery(name = "MetodoPago.findByNumero", query = "SELECT m FROM MetodoPago m WHERE m.numero = :numero")
    , @NamedQuery(name = "MetodoPago.findByNombrePropietario", query = "SELECT m FROM MetodoPago m WHERE m.nombrePropietario = :nombrePropietario")
    , @NamedQuery(name = "MetodoPago.findByFechaExpiracion", query = "SELECT m FROM MetodoPago m WHERE m.fechaExpiracion = :fechaExpiracion")
    , @NamedQuery(name = "MetodoPago.findByCvc", query = "SELECT m FROM MetodoPago m WHERE m.cvc = :cvc")
    , @NamedQuery(name = "MetodoPago.findByTipoIdentificacion", query = "SELECT m FROM MetodoPago m WHERE m.tipoIdentificacion = :tipoIdentificacion")
    , @NamedQuery(name = "MetodoPago.findByIdentificacion", query = "SELECT m FROM MetodoPago m WHERE m.identificacion = :identificacion")})
public class MetodoPago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "numero")
    private String numero;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "nombrePropietario")
    private String nombrePropietario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaExpiracion")
    @Temporal(TemporalType.DATE)
    private Date fechaExpiracion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cvc")
    private short cvc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "tipoIdentificacion")
    private String tipoIdentificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "identificacion")
    private String identificacion;
    @JoinColumn(name = "idCliente", referencedColumnName = "cedula")
    @ManyToOne(optional = false)
    private Persona idCliente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMetodoPago")
    private List<Compra> compraList;

    public MetodoPago() {
    }

    public MetodoPago(Integer id) {
        this.id = id;
    }

    public MetodoPago(Integer id, String nombre, String numero, String nombrePropietario, Date fechaExpiracion, short cvc, String tipoIdentificacion, String identificacion) {
        this.id = id;
        this.nombre = nombre;
        this.numero = numero;
        this.nombrePropietario = nombrePropietario;
        this.fechaExpiracion = fechaExpiracion;
        this.cvc = cvc;
        this.tipoIdentificacion = tipoIdentificacion;
        this.identificacion = identificacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNombrePropietario() {
        return nombrePropietario;
    }

    public void setNombrePropietario(String nombrePropietario) {
        this.nombrePropietario = nombrePropietario;
    }

    public Date getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(Date fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public short getCvc() {
        return cvc;
    }

    public void setCvc(short cvc) {
        this.cvc = cvc;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public Persona getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Persona idCliente) {
        this.idCliente = idCliente;
    }
     @XmlTransient
    public List<Compra> getCompraList() {
        return compraList;
    }

    public void setCompraList(List<Compra> compraList) {
        this.compraList = compraList;
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
        if (!(object instanceof MetodoPago)) {
            return false;
        }
        MetodoPago other = (MetodoPago) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.MetodoPago[ id=" + id + " ]";
    }
    
}
