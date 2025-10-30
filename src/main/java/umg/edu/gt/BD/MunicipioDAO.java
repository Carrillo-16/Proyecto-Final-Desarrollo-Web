package umg.edu.gt.BD;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;
import umg.edu.gt.DTO.MunicipioEntity;

public class MunicipioDAO {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("UMG2025PU");

    public void InsertarMunicipio(MunicipioEntity municipio) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(municipio);
            em.getTransaction().commit();
            System.out.println("✅ Municipio insertado correctamente.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("❌ Error al insertar: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void ModificarMunicipio(MunicipioEntity municipio) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.merge(municipio);
            em.getTransaction().commit();
            System.out.println("✅ Municipio actualizado correctamente.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("❌ Error al modificar: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void BorrarMunicipio(int idMunicipio) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            MunicipioEntity municipio = em.find(MunicipioEntity.class, idMunicipio);
            if (municipio != null) {
                municipio.setEstado(0); // Baja lógica
                em.merge(municipio);
                em.getTransaction().commit();
                System.out.println("✅ Municipio marcado como inactivo.");
            } else {
                System.out.println("⚠️ No se encontró el municipio.");
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("❌ Error al borrar: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public MunicipioEntity ConsultarMunicipio(int idMunicipio) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(MunicipioEntity.class, idMunicipio);
        } finally {
            em.close();
        }
    }

    public List<MunicipioEntity> listarMunicipiosActivos() {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT m FROM MunicipioEntity m WHERE m.estado = 1";
            TypedQuery<MunicipioEntity> query = em.createQuery(jpql, MunicipioEntity.class);
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