package umg.edu.gt.BD;
import umg.edu.gt.DTO.PersonaEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;


public class PersonaDAO {
    
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("UMG2025PU");

    public void InsertarPersona(PersonaEntity persona) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(persona);
            em.getTransaction().commit();
            System.out.println("✅ Persona insertada correctamente con Hibernate.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("❌ Error al insertar: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void ModificarPersona(PersonaEntity persona) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.merge(persona);
            em.getTransaction().commit();
            System.out.println("✅ Persona actualizada correctamente.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("❌ Error al modificar: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public void BorrarPersona(int idPersona) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            PersonaEntity persona = em.find(PersonaEntity.class, idPersona);
            if (persona != null) {
                persona.setEstado(0); // Baja lógica
                em.merge(persona);
                em.getTransaction().commit();
                System.out.println("✅ Persona marcada como inactiva.");
            } else {
                System.out.println("⚠️ No se encontró la persona.");
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("❌ Error al borrar: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    public PersonaEntity ConsultarPersona(int idPersona) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(PersonaEntity.class, idPersona);
        } finally {
            em.close();
        }
    }

    /*Listar todas las personas activas (estado = 1).*/
    public List<PersonaEntity> listarPersonasActivas() {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT p FROM PersonaEntity p WHERE p.estado = 1";
            TypedQuery<PersonaEntity> query = em.createQuery(jpql, PersonaEntity.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    /*Cerrar la fábrica de EntityManager (usualmente se llama al cerrar la aplicación)*/
    public void cerrar() {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}