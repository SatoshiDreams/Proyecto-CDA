
package DbContext;

import Entidades.Clientes;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;


public class ClienteCtx {
    private EntityManagerFactory fabricaEntity = null;
    
    public ClienteCtx(EntityManagerFactory manager) {
        this.fabricaEntity = manager;
    }
    
    public ClienteCtx() {
        this.fabricaEntity = Persistence.createEntityManagerFactory("CdaPU");
    }   
    
    public EntityManager obtenerManejadorEntidad(){
        return fabricaEntity.createEntityManager();
    }
    
    public Clientes obtenerPorCredencial(String credencial) throws Exception {
        EntityManager manejador = null;
        try {
            manejador = obtenerManejadorEntidad();
            
            manejador.getTransaction().begin();
            
            StoredProcedureQuery procedimientoAlmacenado = manejador.createNamedStoredProcedureQuery("ObtenerClientePorCredencial");
            
            procedimientoAlmacenado.setParameter("credencial_cliente", credencial);
            
            procedimientoAlmacenado.execute();
            
            List<Clientes> listaClientes = procedimientoAlmacenado.getResultList();
            
            manejador.getTransaction().commit();
            
            if (!listaClientes.isEmpty()) {
                return listaClientes.get(0);
            } else {
                return null; 
            }
            
        } catch (Exception e) {
            if (manejador.getTransaction().isActive()) manejador.getTransaction().rollback();
            throw new Exception(e);
        } finally {
            manejador.close();
        }
    }
    
    public void guardar(Clientes cliente) throws Exception {
        EntityManager manejador = null;
        try {
            manejador = obtenerManejadorEntidad();
            manejador.getTransaction().begin();
            manejador.persist(cliente);
            manejador.getTransaction().commit();
        } catch (Exception e) {
            if (manejador.getTransaction().isActive()) manejador.getTransaction().rollback();
            throw new Exception(e);
        } finally {
            manejador.close();
        }
    }
}

