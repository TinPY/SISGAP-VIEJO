/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ar.edu.undec.sisgap.model;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hugo
 */
@Entity
@Table(name = "agente", schema = "ap")
@SequenceGenerator(name="agente_id_seq", sequenceName="ap.agente_id_seq", allocationSize=1)
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Agente.findAll", query = "SELECT a FROM Agente a"),
    @NamedQuery(name = "Agente.findByApellido", query = "SELECT a FROM Agente a WHERE a.apellido = :apellido"),
    @NamedQuery(name = "Agente.findByNombres", query = "SELECT a FROM Agente a WHERE a.nombres = :nombres"),
    @NamedQuery(name = "Agente.findByNumerodocumento", query = "SELECT a FROM Agente a WHERE a.numerodocumento = :numerodocumento"),
    @NamedQuery(name = "Agente.findByTelefono", query = "SELECT a FROM Agente a WHERE a.telefono = :telefono"),
    @NamedQuery(name = "Agente.findByCelular", query = "SELECT a FROM Agente a WHERE a.celular = :celular"),
    @NamedQuery(name = "Agente.findByEmail", query = "SELECT a FROM Agente a WHERE a.email = :email"),
    @NamedQuery(name = "Agente.findByOtroemail", query = "SELECT a FROM Agente a WHERE a.otroemail = :otroemail"),
    @NamedQuery(name = "Agente.findByProfesion", query = "SELECT a FROM Agente a WHERE a.profesion = :profesion"),
    @NamedQuery(name = "Agente.findById", query = "SELECT a FROM Agente a WHERE a.id = :id"),
    @NamedQuery(name = "Agente.findByDomicilio", query = "SELECT a FROM Agente a WHERE a.domicilio = :domicilio")})
public class Agente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 25)
    @Column(name = "apellido")
    private String apellido;
    @Size(max = 35)
    @Column(name = "nombres")
    private String nombres;
    @Size(max = 15)
    @Column(name = "numerodocumento")
    private String numerodocumento;
    @Size(max = 25)
    @Column(name = "telefono")
    private String telefono;
    @Size(max = 25)
    @Column(name = "celular")
    private String celular;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 60)
    @Column(name = "email")
    private String email;
    @Size(max = 60)
    @Column(name = "otroemail")
    private String otroemail;
    @Size(max = 100)
    @Column(name = "profesion")
    private String profesion;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="agente_id_seq")
    @Basic(optional = true)
    @Column(name = "id")
    private Integer id;
    @Size(max = 2147483647)
    @Column(name = "domicilio")
    private String domicilio;
    @JoinColumn(name = "tipodocumentoid", referencedColumnName = "tipodocumentoid")
    @ManyToOne
    private Tipodocumento tipodocumentoid;
    @JoinColumn(name = "dependenciaid", referencedColumnName = "id")
    @ManyToOne
    private Dependencia dependenciaid;
    @JoinColumn(name = "usuarioid", referencedColumnName = "usuarioid" )
    @ManyToOne
    private Usuario usuarioid;
    @Size(max = 60)
    @Column(name = "especialidad")
    private String especialidad;
    

    public Agente() {
    }

    public Agente(Integer id) {
        this.id = id;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getNumerodocumento() {
        return numerodocumento;
    }

    public void setNumerodocumento(String numerodocumento) {
        this.numerodocumento = numerodocumento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtroemail() {
        return otroemail;
    }

    public void setOtroemail(String otroemail) {
        this.otroemail = otroemail;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public Tipodocumento getTipodocumentoid() {
        return tipodocumentoid;
    }

    public void setTipodocumentoid(Tipodocumento tipodocumentoid) {
        this.tipodocumentoid = tipodocumentoid;
    }

    public Dependencia getDependenciaid() {
        return dependenciaid;
    }

    public void setDependenciaid(Dependencia dependenciaid) {
        this.dependenciaid = dependenciaid;
    }

    public Usuario getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(Usuario usuarioid) {
        this.usuarioid = usuarioid;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
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
        if (!(object instanceof Agente)) {
            return false;
        }
        Agente other = (Agente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return numerodocumento+" | "+apellido+", "+nombres;
    }
    
}
