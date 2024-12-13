/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import Entidades.Vehiculos;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "diagnosticos")
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(
        name = "ObtenerPromedioTiempoDiagnosticoPorTipoDeVehiculo", 
        procedureName = "ObtenerPromedioTiempoDiagnosticoPorTipoDeVehiculo"
    ),
    @NamedStoredProcedureQuery(
        name = "ObtenerDiagnosticosPorAo", 
        procedureName = "ObtenerDiagnosticosPorAo", 
        resultClasses = { Diagnosticos.class }, 
        parameters = {
            @StoredProcedureParameter(
                name = "ao_entrada", 
                type = Integer.class, 
                mode = ParameterMode.IN 
            )
        }
    ),
    @NamedStoredProcedureQuery(
        name = "ObtenerDiagnosticosPorAoYMes", 
        procedureName = "ObtenerDiagnosticosPorAoYMes", 
        resultClasses = { Diagnosticos.class }, 
        parameters = {
            @StoredProcedureParameter(
                name = "ao_entrada", 
                type = Integer.class, 
                mode = ParameterMode.IN 
            ),
            @StoredProcedureParameter(
                name = "mes_entrada", 
                type = Integer.class, 
                mode = ParameterMode.IN 
            )
        }
    ),
    @NamedStoredProcedureQuery(
        name = "ObtenerDiagnosticosPorTipoDeVehiculo", 
        procedureName = "ObtenerDiagnosticosPorTipoDeVehiculo", 
        resultClasses = { Diagnosticos.class },
        parameters = {
            @StoredProcedureParameter(
                name = "tipo_entrada", 
                type = String.class, 
                mode = ParameterMode.IN 
            )
        }
    )
})
@NamedQueries({
    @NamedQuery(name = "Diagnosticos.findAll", query = "SELECT d FROM Diagnosticos d")})
public class Diagnosticos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "resultado")
    private String resultado;
    @Lob
    @Column(name = "observaciones")
    private String observaciones;
    @Column(name = "duracion")
    @Temporal(TemporalType.TIME)
    private Date duracion;
    @JoinColumn(name = "fk_empleado_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Empleados fkEmpleadoId;
    @JoinColumn(name = "fk_vehiculo_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Vehiculos fkVehiculoId;

    public Diagnosticos() {
    }

    public Diagnosticos(String id) {
        this.id = id;
    }

    public Diagnosticos(String id, Date fecha, String resultado) {
        this.id = id;
        this.fecha = fecha;
        this.resultado = resultado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getDuracion() {
        return duracion;
    }

    public void setDuracion(Date duracion) {
        this.duracion = duracion;
    }

    public Empleados getFkEmpleadoId() {
        return fkEmpleadoId;
    }

    public void setFkEmpleadoId(Empleados fkEmpleadoId) {
        this.fkEmpleadoId = fkEmpleadoId;
    }

    public Vehiculos getFkVehiculoId() {
        return fkVehiculoId;
    }

    public void setFkVehiculoId(Vehiculos fkVehiculoId) {
        this.fkVehiculoId = fkVehiculoId;
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
        if (!(object instanceof Diagnosticos)) {
            return false;
        }
        Diagnosticos other = (Diagnosticos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Diagnosticos[ id=" + id + " ]";
    }
    
}
