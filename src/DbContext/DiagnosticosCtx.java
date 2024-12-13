/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DbContext;

import Entidades.Diagnosticos;
import Mapeo.TiempoPromedio;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;


public class DiagnosticosCtx {
    private EntityManagerFactory fabricaEntity = null;
    
    public DiagnosticosCtx(EntityManagerFactory manager) {
        this.fabricaEntity = manager;
    }
    
    public DiagnosticosCtx() {
        this.fabricaEntity = Persistence.createEntityManagerFactory("CdaPU");
    }
    
    public EntityManager obtenerManejadorEntidad(){
        return fabricaEntity.createEntityManager();
    }
    
    public void crear(Diagnosticos diagnostico) throws Exception {
        EntityManager manejador = null;
        try {
            manejador = obtenerManejadorEntidad();
            manejador.getTransaction().begin();
            manejador.persist(diagnostico);
            manejador.getTransaction().commit();
        } catch (Exception e) {
            if (manejador.getTransaction().isActive()) manejador.getTransaction().rollback();
            throw new Exception(e);
        } finally {
            manejador.close();
        }
    }
    
    public List<Diagnosticos> todosDiagnosticosPorTipo(String tipo) throws Exception {
        EntityManager manejador = null;
        try {
            manejador = obtenerManejadorEntidad();
            
            manejador.getTransaction().begin();
            
            StoredProcedureQuery procedimientoAlmacenado = manejador.createNamedStoredProcedureQuery("ObtenerDiagnosticosPorTipoDeVehiculo");
            
            procedimientoAlmacenado.setParameter("tipo_entrada", tipo);
            
            procedimientoAlmacenado.execute();
            
            manejador.getTransaction().commit();
            
            return procedimientoAlmacenado.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        } catch (Exception e) {
            if (manejador.getTransaction().isActive()) manejador.getTransaction().rollback();
            throw new Exception(e);
        } finally {
            manejador.close();
        }
    }
    
    public List<Diagnosticos> todosDiagnosticosPorMes(int año, int mes) throws Exception {
        EntityManager manejador = null;
        try {
            manejador = obtenerManejadorEntidad();
            
            manejador.getTransaction().begin();
            
            StoredProcedureQuery procedimientoAlmacenado = manejador.createNamedStoredProcedureQuery("ObtenerDiagnosticosPorAoYMes");
            
            procedimientoAlmacenado.setParameter("ao_entrada", año);
            procedimientoAlmacenado.setParameter("mes_entrada", mes);
            
            procedimientoAlmacenado.execute();
            
            manejador.getTransaction().commit();
            
            return procedimientoAlmacenado.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        } catch (Exception e) {
            if (manejador.getTransaction().isActive()) manejador.getTransaction().rollback();
            e.printStackTrace();
            throw new Exception(e);
        } finally {
            manejador.close();
        }
    }
    
    public List<Diagnosticos> todosDiagnosticosPorAñoConClientes(int año) throws Exception {
        EntityManager manejador = null;
        try {
            manejador = obtenerManejadorEntidad();
            
            manejador.getTransaction().begin();
            
            StoredProcedureQuery procedimientoAlmacenado = manejador.createNamedStoredProcedureQuery("ObtenerDiagnosticosPorAo");
            
            procedimientoAlmacenado.setParameter("ao_entrada", año);
            
            procedimientoAlmacenado.execute();
            
            manejador.getTransaction().commit();
            
            return procedimientoAlmacenado.getResultList();
        } catch (NoResultException e) {
            return Collections.emptyList();
        } catch (Exception e) {
            if (manejador.getTransaction().isActive()) manejador.getTransaction().rollback();
            e.printStackTrace();
            throw new Exception(e);
        } finally {
            manejador.close();
        }
    }
    
    public List<TiempoPromedio> todosPromediosDeTiempo() throws Exception {
        EntityManager manejador = null;
        List<TiempoPromedio> listaPromedioTiempo = new ArrayList();
        
        try {
            manejador = obtenerManejadorEntidad();
            
            manejador.getTransaction().begin();
            
            StoredProcedureQuery procedimientoAlmacenado = manejador.createNamedStoredProcedureQuery("ObtenerPromedioTiempoDiagnosticoPorTipoDeVehiculo");
            
            procedimientoAlmacenado.execute();
            
            List<Object[]> resultados = procedimientoAlmacenado.getResultList();
            
            for (Object[] resultado : resultados) {
                TiempoPromedio promedioTiempo = new TiempoPromedio();
                
                promedioTiempo.setTipo((String) resultado[0]);
                
                BigDecimal bigDecimal = (BigDecimal) resultado[1];
                
                promedioTiempo.setMinutos((Double) bigDecimal.doubleValue());
                
                listaPromedioTiempo.add(promedioTiempo);
            }
            
            manejador.getTransaction().commit();
            
            return listaPromedioTiempo;
        } catch (NoResultException e) {
            return Collections.emptyList();
        } catch (Exception e) {
            if (manejador.getTransaction().isActive()) manejador.getTransaction().rollback();
            e.printStackTrace();
            throw new Exception(e);
        } finally {
            manejador.close();
        }
    }
    
}

