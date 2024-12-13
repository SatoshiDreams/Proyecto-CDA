/*
 * Click nbfs://nbhost/SystmanejadorFileSystmanejador/Tmanejadorplates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystmanejadorFileSystmanejador/Tmanejadorplates/Classes/Class.java to edit this tmanejadorplate
 */
package DbContext;

import Entidades.Rol;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;


public class RolesCtx {
    private EntityManagerFactory fabricaEntity = null;
    
    public RolesCtx(EntityManagerFactory manager) {
        this.fabricaEntity = manager;
    }
    
    public RolesCtx() {
        this.fabricaEntity = Persistence.createEntityManagerFactory("CdaPU");
    }
    
    public EntityManager obtenerManejadorEntidad() {
        return fabricaEntity.createEntityManager();
    }
    
    public Rol obtenerPorRol(String rol) throws Exception {
        EntityManager manejador = null;
        try {
            manejador = obtenerManejadorEntidad();
            
            manejador.getTransaction().begin();

            StoredProcedureQuery procedimientoAlmacenado = manejador.createNamedStoredProcedureQuery("ObtenerRolPorNombre");

            procedimientoAlmacenado.setParameter("rol_entrada", rol); 
            
            procedimientoAlmacenado.execute();

            List<Rol> roles = procedimientoAlmacenado.getResultList();

            manejador.getTransaction().commit();

            if (!roles.isEmpty()) return roles.get(0); 
            
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
    
    public void guardar(Rol rol) throws Exception {
        EntityManager manejador = null;
        try {
            manejador = obtenerManejadorEntidad();
            manejador.getTransaction().begin();
            manejador.persist(rol);
            manejador.getTransaction().commit();
        } catch (Exception e) {
            if (manejador.getTransaction().isActive()) manejador.getTransaction().rollback();
            throw new Exception(e);
        } finally {
            manejador.close();
        }
    }
    
    public List<Rol> obtenerTodosLosRoles() throws Exception {
        EntityManager manejador = null;
        try {
            manejador = obtenerManejadorEntidad();
            
            manejador.getTransaction().begin();

            StoredProcedureQuery procedimientoAlmacenado = manejador.createNamedStoredProcedureQuery("ObtenerTodosLosRoles");
            
            procedimientoAlmacenado.execute();
            
            List<Rol> roles = procedimientoAlmacenado.getResultList();

            manejador.getTransaction().commit();

            return roles;
        } catch (Exception e) {
            if (manejador.getTransaction().isActive()) manejador.getTransaction().rollback();
            throw new Exception(e);
        } finally {
            manejador.close();
        }
    }
}

