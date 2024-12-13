/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import Entidades.Diagnosticos;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;


@Entity
@Table(name = "empleados")
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(
        name = "ObtenerEmpleadoPorDocumento", 
        procedureName = "ObtenerEmpleadoPorDocumento", 
        resultClasses = { Empleados.class },
        parameters = {
            @StoredProcedureParameter(
                name = "documento_entrada", 
                type = String.class, 
                mode = ParameterMode.IN
            )
        }
    ),
    @NamedStoredProcedureQuery(
        name = "ObtenerTodosLosEmpleados", 
        procedureName = "ObtenerTodosLosEmpleados",
        resultClasses = { Empleados.class }
    ),
    @NamedStoredProcedureQuery(
        name = "EliminarEmpleadoPorDocumento", 
        procedureName = "EliminarEmpleadoPorDocumento",
        parameters = {
            @StoredProcedureParameter(
                name = "documento_entrada", 
                type = String.class, 
                mode = ParameterMode.IN
            )
        }
    ),
    @NamedStoredProcedureQuery(
        name = "CambiarEstadoActivoEmpleado", 
        procedureName = "CambiarEstadoActivoEmpleado",
        parameters = {
            @StoredProcedureParameter(
                name = "documento_entrada", 
                type = String.class, 
                mode = ParameterMode.IN
            )
        }
    ),
    @NamedStoredProcedureQuery(
        name = "ExisteRolNoBloqueado", 
        procedureName = "ExisteRolNoBloqueado",
        parameters = {
            @StoredProcedureParameter(
                name = "nombre_rol", 
                type = String.class, 
                mode = ParameterMode.IN
            )
        }
    )
})
@NamedQueries({
    @NamedQuery(name = "Empleados.findAll", query = "SELECT e FROM Empleados e")})
public class Empleados implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @Column(name = "documento")
    private String documento;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "contraseña")
    private String contrasea;
    @Column(name = "esta_activo")
    private Boolean estaActivo;
    @JoinColumn(name = "fk_rol_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Rol fkRolId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkEmpleadoId")
    private Collection<Diagnosticos> diagnosticosCollection;

    public Empleados() {
    }

    public Empleados(String id) {
        this.id = id;
    }

    public Empleados(String id, String documento, String nombre) {
        this.id = id;
        this.documento = documento;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasea() {
        return contrasea;
    }

    public void setContrasea(String contrasea) {
        this.contrasea = contrasea;
    }

    public Boolean getEstaActivo() {
        return estaActivo;
    }

    public void setEstaActivo(Boolean estaActivo) {
        this.estaActivo = estaActivo;
    }

    public Rol getFkRolId() {
        return fkRolId;
    }

    public void setFkRolId(Rol fkRolId) {
        this.fkRolId = fkRolId;
    }

    public Collection<Diagnosticos> getDiagnosticosCollection() {
        return diagnosticosCollection;
    }

    public void setDiagnosticosCollection(Collection<Diagnosticos> diagnosticosCollection) {
        this.diagnosticosCollection = diagnosticosCollection;
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
        if (!(object instanceof Empleados)) {
            return false;
        }
        Empleados other = (Empleados) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Empleados[ id=" + id + " ]";
    }
    
}
