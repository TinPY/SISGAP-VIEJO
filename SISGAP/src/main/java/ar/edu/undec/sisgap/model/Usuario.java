/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.edu.undec.sisgap.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author Hugo
 */
@Entity
@Table(name = "usuario", schema = "ap")
@SequenceGenerator(name="usuario_usuarioid_seq", sequenceName="ap.usuario_usuarioid_seq", allocationSize=1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByUsuarioid", query = "SELECT u FROM Usuario u WHERE u.usuarioid = :usuarioid"),
    @NamedQuery(name = "Usuario.findByUsuarionombre", query = "SELECT u FROM Usuario u WHERE u.usuarionombre = :usuarionombre"),
    @NamedQuery(name = "Usuario.findByUsuarioclave", query = "SELECT u FROM Usuario u WHERE u.usuarioclave = :usuarioclave"),
    @NamedQuery(name = "Usuario.findByUsuarioestado", query = "SELECT u FROM Usuario u WHERE u.usuarioestado = :usuarioestado"),
    @NamedQuery(name = "Usuario.findByUsuariofechaalta", query = "SELECT u FROM Usuario u WHERE u.usuariofechaalta = :usuariofechaalta"),
    @NamedQuery(name = "Usuario.findByUsuariofechabaja", query = "SELECT u FROM Usuario u WHERE u.usuariofechabaja = :usuariofechabaja"),
    @NamedQuery(name = "Usuario.findByHabilitacion", query = "SELECT u FROM Usuario u WHERE u.habilitacion = :habilitacion")})
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="usuario_usuarioid_seq")
    @Basic(optional = false)
    @Column(name = "usuarioid")
    private Integer usuarioid;
    @Size(max = 2147483647)
    @Column(name = "usuarionombre")
    private String usuarionombre;
    @Size(max = 255)
    @Column(name = "usuarioclave")
    private String usuarioclave;
    @Basic(optional = false)
    @NotNull
    @Column(name = "usuarioestado")
    private char usuarioestado;
    @Column(name = "usuariofechaalta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usuariofechaalta;
    @Column(name = "usuariofechabaja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date usuariofechabaja;
    @Size(max = 2147483647)
    @Column(name = "habilitacion")
    private String habilitacion;
    @JoinColumn(name = "usuariorol", referencedColumnName = "rolid")
    @ManyToOne(optional = false)
    private Perfil usuariorol;

    public Usuario() {
    }

    public Usuario(Integer usuarioid) {
        this.usuarioid = usuarioid;
    }

    public Usuario(Integer usuarioid, char usuarioestado) {
        this.usuarioid = usuarioid;
        this.usuarioestado = usuarioestado;
    }

    public Integer getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(Integer usuarioid) {
        this.usuarioid = usuarioid;
    }

    public String getUsuarionombre() {
        return usuarionombre;
    }

    public void setUsuarionombre(String usuarionombre) {
        this.usuarionombre = usuarionombre;
    }

    public String getUsuarioclave() {
        return usuarioclave;
    }

    public void setUsuarioclave(String usuarioclave) {
        this.usuarioclave = usuarioclave;
    }

    public char getUsuarioestado() {
        return usuarioestado;
    }

    public void setUsuarioestado(char usuarioestado) {
        this.usuarioestado = usuarioestado;
    }

    public Date getUsuariofechaalta() {
        return usuariofechaalta;
    }

    public void setUsuariofechaalta(Date usuariofechaalta) {
        this.usuariofechaalta = usuariofechaalta;
    }

    public Date getUsuariofechabaja() {
        return usuariofechabaja;
    }

    public void setUsuariofechabaja(Date usuariofechabaja) {
        this.usuariofechabaja = usuariofechabaja;
    }

    public String getHabilitacion() {
        return habilitacion;
    }

    public void setHabilitacion(String habilitacion) {
        this.habilitacion = habilitacion;
    }

    public Perfil getUsuariorol() {
        return usuariorol;
    }

    public void setUsuariorol(Perfil ususariorol) {
        this.usuariorol = ususariorol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuarioid != null ? usuarioid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.usuarioid == null && other.usuarioid != null) || (this.usuarioid != null && !this.usuarioid.equals(other.usuarioid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return usuarionombre;
    }
    
}
