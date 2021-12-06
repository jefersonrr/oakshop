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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jefersonrr
 */
@Entity
@Table(name = "Tipo_Talla")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoTalla.findAll", query = "SELECT t FROM TipoTalla t")
    , @NamedQuery(name = "TipoTalla.findById", query = "SELECT t FROM TipoTalla t WHERE t.id = :id")})
public class TipoTalla implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "idTalla", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Talla idTalla;
    @JoinColumn(name = "idTipo", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Tipo idTipo;

    public TipoTalla() {
    }

    public TipoTalla(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Talla getIdTalla() {
        return idTalla;
    }

    public void setIdTalla(Talla idTalla) {
        this.idTalla = idTalla;
    }

    public Tipo getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Tipo idTipo) {
        this.idTipo = idTipo;
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
        if (!(object instanceof TipoTalla)) {
            return false;
        }
        TipoTalla other = (TipoTalla) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DTO.TipoTalla[ id=" + id + " ]";
    }
    
}
