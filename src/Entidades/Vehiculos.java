
package Entidades;

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
@Table(name = "vehiculos")
@NamedStoredProcedureQueries({
    @NamedStoredProcedureQuery(
        name = "ObtenerVehiculoPorMatricula",
        procedureName = "ObtenerVehiculoPorMatricula",
        resultClasses = { Vehiculos.class }, 
        parameters = {
            @StoredProcedureParameter(
                name = "matricula_entrada", 
                type = String.class, 
                mode = ParameterMode.IN
            )
        }
    )
})
@NamedQueries({
    @NamedQuery(name = "Vehiculos.findAll", query = "SELECT v FROM Vehiculos v")})
public class Vehiculos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private String id;
    @Column(name = "tipo")
    private String tipo;
    @Basic(optional = false)
    @Column(name = "matricula")
    private String matricula;
    @JoinColumn(name = "fk_cliente_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Clientes fkClienteId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkVehiculoId")
    private Collection<Diagnosticos> diagnosticosCollection;

    public Vehiculos() {
    }

    public Vehiculos(String id) {
        this.id = id;
    }

    public Vehiculos(String id, String matricula) {
        this.id = id;
        this.matricula = matricula;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Clientes getFkClienteId() {
        return fkClienteId;
    }

    public void setFkClienteId(Clientes fkClienteId) {
        this.fkClienteId = fkClienteId;
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
        if (!(object instanceof Vehiculos)) {
            return false;
        }
        Vehiculos other = (Vehiculos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Vehiculos[ id=" + id + " ]";
    }
    
}
