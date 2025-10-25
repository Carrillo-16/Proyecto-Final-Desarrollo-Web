package umg.edu.gt.BD;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import umg.edu.gt.DTO.DepartamentoEntity;

public class DepartamentoDAO {
    
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("UMG2025PU");

    public void InsertarDepartamento(DepartamentoEntity departamento) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(departamento);
            em.getTransaction().commit();
            System.out.println("✅ Departamento insertado correctamente.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("❌ Error al insertar: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void ModificarDepartamento(DepartamentoEntity departamento) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.merge(departamento);
            em.getTransaction().commit();
            System.out.println("✅ Departamento actualizado correctamente.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("❌ Error al modificar: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void BorrarDepartamento(int idDepartamento) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            DepartamentoEntity departamento = em.find(DepartamentoEntity.class, idDepartamento);
            if (departamento != null) {
                em.remove(departamento);  // Eliminación física (no lógica, ya que no tiene estado)
                em.getTransaction().commit();
                System.out.println("✅ Departamento eliminado.");
            } else {
                System.out.println("⚠️ No se encontró el departamento.");
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("❌ Error al borrar: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public DepartamentoEntity ConsultarDepartamento(int idDepartamento) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(DepartamentoEntity.class, idDepartamento);
        } finally {
            em.close();
        }
    }

    public List<DepartamentoEntity> listarDepartamentos() {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT d FROM DepartamentoEntity d";
            TypedQuery<DepartamentoEntity> query = em.createQuery(jpql, DepartamentoEntity.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void cerrar() {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}