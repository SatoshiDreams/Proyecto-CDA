
package DbContext;

import Entidades.Empleados;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;


public class EmpleadosCtx {
    private EntityManagerFactory fabricaEntity = null;
    
    public EmpleadosCtx(EntityManagerFactory manager) {
        this.fabricaEntity = manager;
    }
    
    public EmpleadosCtx() {
        this.fabricaEntity = Persistence.createEntityManagerFactory("CdaPU");
    }
    
    public EntityManager obtenerManejadorEntidad(){
        return fabricaEntity.createEntityManager();
    }
    
    public Empleados obtenerPorCredencial(String credencial) throws Exception {
        EntityManager manejador = null;
        try {
            manejador = obtenerManejadorEntidad();
            
            manejador.getTransaction().begin();
            
            StoredProcedureQuery procedimientoAlmacenado = manejador.createNamedStoredProcedureQuery("ObtenerEmpleadoPorDocumento");
            
            procedimientoAlmacenado.setParameter("documento_entrada", credencial);
            
            procedimientoAlmacenado.execute();
            
            List<Empleados> listaEmpleados = procedimientoAlmacenado.getResultList(); 

            manejador.getTransaction().commit();

            if (!listaEmpleados.isEmpty()) return listaEmpleados.get(0); 
            
            return null;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            if (manejador.getTransaction().isActive()) manejador.getTransaction().rollback();
            throw new Exception(e);
        } finally {
            manejador.close();
        }
    }
    
    
    public List<Empleados> obtenerEmpleadosPorRolYEstado(String rol) throws Exception {
        EntityManager manejador = null;
        try {
            manejador = obtenerManejadorEntidad();
            
            manejador.getTransaction().begin();

            StoredProcedureQuery procedimientoAlmacenado = manejador.createNamedStoredProcedureQuery("ExisteRolNoBloqueado");
            
            procedimientoAlmacenado.setParameter("nombre_rol", rol); 
            
            procedimientoAlmacenado.execute();
             
            List<Empleados> empleados = procedimientoAlmacenado.getResultList(); 
            
            manejador.getTransaction().commit();
            
            return empleados;
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            if (manejador.getTransaction().isActive()) manejador.getTransaction().rollback();
            throw new Exception(e);
        } finally {
            manejador.close();
        }
    }
    
    public void guardar(Empleados manejadorpleado) throws Exception {
        EntityManager manejador = null;
        try {
            manejador = obtenerManejadorEntidad();
            manejador.getTransaction().begin();
            manejador.persist(manejadorpleado);
            manejador.getTransaction().commit();
        } catch (Exception e) {
            if (manejador.getTransaction().isActive()) manejador.getTransaction().rollback();
            throw new Exception(e);
        } finally {
            manejador.close();
        }
    }
    
    public List<Empleados> obtenerTodosLosEmpleados() throws Exception {
        EntityManager manejador = null;
        try {
            manejador = obtenerManejadorEntidad();
            
            manejador.getTransaction().begin();

            StoredProcedureQuery procedimientoAlmacenado = manejador.createNamedStoredProcedureQuery("ObtenerTodosLosEmpleados");
            
            procedimientoAlmacenado.execute();
            
            List<Empleados> empleados = procedimientoAlmacenado.getResultList(); 

            manejador.getTransaction().commit();
            
            return empleados;
        } catch (NoResultException e) {
            return Collections.emptyList();
        } catch (Exception e) {
            if (manejador.getTransaction().isActive()) manejador.getTransaction().rollback();
            throw new Exception(e);
        } finally {
            manejador.close();
        }
    }
    
    public void eliminarEmpleadoPorCredencial(String credencial) throws Exception {
        EntityManager manejador = null;
        try {
            manejador = obtenerManejadorEntidad();
            
            manejador.getTransaction().begin();

            StoredProcedureQuery procedimientoAlmacenado = manejador.createNamedStoredProcedureQuery("EliminarEmpleadoPorDocumento");

            procedimientoAlmacenado.setParameter("documento_entrada", credencial); 
            
            procedimientoAlmacenado.execute();

            manejador.getTransaction().commit();
        } catch (Exception e) {
            if (manejador.getTransaction().isActive()) manejador.getTransaction().rollback();
            throw new Exception(e);
        } finally {
            manejador.close();
        }
    }
    
    public void actualizarEstadoEmpleado(String credencial) throws Exception {
        EntityManager manejador = null;
        try {
            manejador = obtenerManejadorEntidad();
            
            manejador.getTransaction().begin();

            StoredProcedureQuery procedimientoAlmacenado = manejador.createNamedStoredProcedureQuery("CambiarEstadoActivoEmpleado");

            procedimientoAlmacenado.setParameter("documento_entrada", credencial);

            procedimientoAlmacenado.execute();

            manejador.getTransaction().commit();
        } catch (Exception e) {
            if (manejador.getTransaction().isActive()) manejador.getTransaction().rollback();
            e.printStackTrace();
            throw new Exception(e);
        } finally {
            manejador.close();
        }
    }
}

