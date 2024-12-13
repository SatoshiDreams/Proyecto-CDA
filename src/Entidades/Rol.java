/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import Entidades.Empleados;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;


@Entity
@Table(name = "rol")
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(
        name = "ObtenerRolPorNombre", 
        procedureName = "ObtenerRolPorNombre",
        resultClasses = { Rol.class }, 
        parameters = {
            @StoredProcedureParameter(
                name = "rol_entrada", 
                type = String.class, 
                mode = ParameterMode.IN
            )
        }
    ),
    @NamedStoredProcedureQuery(
        name = "ObtenerTodosLosRoles", 
        procedureName = "ObtenerTodosLosRoles",
        resultClasses = { Rol.class }
    )
})
@NamedQueries({
    @NamedQuery(name = "Rol.findAll", query = "SELECT r FROM Rol r")})
public class Rol implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @Column(name = "rol")
    private String rol;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkRolId")
    private Collection<Empleados> empleadosCollection;

    public Rol() {
    }

    public Rol(String id) {
        this.id = id;
    }

    public Rol(String id, String rol) {
        this.id = id;
        this.rol = rol;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Collection<Empleados> getEmpleadosCollection() {
        return empleadosCollection;
    }

    public void setEmpleadosCollection(Collection<Empleados> empleadosCollection) {
        this.empleadosCollection = empleadosCollection;
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
        if (!(object instanceof Rol)) {
            return false;
        }
        Rol other = (Rol) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Rol[ id=" + id + " ]";
    }
    
}
