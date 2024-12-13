/*
 * Click nbfs://nbhost/SystmanejadorFileSystmanejador/Tmanejadorplates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystmanejadorFileSystmanejador/Tmanejadorplates/Classes/Class.java to edit this tmanejadorplate
 */
package DbContext;

import Entidades.Vehiculos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;


public class VehiculoCtx {
    private EntityManagerFactory fabricaEntity = null;
    
    public VehiculoCtx(EntityManagerFactory manager) {
        this.fabricaEntity = manager;
    }
    
    public VehiculoCtx() {
        this.fabricaEntity = Persistence.createEntityManagerFactory("CdaPU");
    }
    
    public EntityManager obtenerManejadorEntidad() {
        return fabricaEntity.createEntityManager();
    }
    
    public Vehiculos obtenerPorMatricula(String matricula) throws Exception {
        EntityManager manejador = null;
        try {
            manejador = obtenerManejadorEntidad();
            
            manejador.getTransaction().begin();

            StoredProcedureQuery procedimientoAlmacenado = manejador.createNamedStoredProcedureQuery("ObtenerVehiculoPorMatricula");
            procedimientoAlmacenado.setParameter("matricula_entrada", matricula); 
            
            procedimientoAlmacenado.execute();

            List<Vehiculos> vehiculos = procedimientoAlmacenado.getResultList();

            manejador.getTransaction().commit();

            if (!vehiculos.isEmpty()) return vehiculos.get(0);
            
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
    
    public void guardar(Vehiculos vehiculo) throws Exception {
        EntityManager manejador = null;
        try {
            manejador = obtenerManejadorEntidad();
            manejador.getTransaction().begin();
            manejador.persist(vehiculo); 
            manejador.getTransaction().commit();
        } catch (Exception e) {
            if (manejador.getTransaction().isActive()) manejador.getTransaction().rollback();
            throw new Exception(e);
        } finally {
            manejador.close();
        }
    }
}

