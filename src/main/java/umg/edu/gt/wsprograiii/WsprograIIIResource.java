package umg.edu.gt.wsprograiii;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.enterprise.context.RequestScoped;
import javax.json.JsonObject;
import javax.ws.rs.FormParam;
import org.json.simple.JSONObject;

import umg.edu.gt.BD.PersonaDAO;
import umg.edu.gt.DTO.PersonaEntity;


/**
 * REST Web Service
 * Ruta general: http://localhost:8080/wsprograIII/webresources/wsprograIII/
 * 
 * Ruta para Insert: http://localhost:8080/wsprograIII/webresources/wsprograIII/Insertar?NombreUsuario=Marlon%20Colindre&Correo=Colindres@gmail.com
 * Ruta para Update: 
 * 
 * @author default
 */
@Path("wsprograIII")
@RequestScoped
public class WsprograIIIResource {

    @Context
    private UriInfo context;

    public WsprograIIIResource() {
    }   
        
    /**
     * WEB SERVICES [APARTADO PERSONA]
     */
    
    @POST
    @Path("InsertarPersona")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject InsertarPersona(PersonaEntity persona) {
        JSONObject json = new JSONObject();
        try {
            if (persona.getNombre() == null || persona.getApellido() == null ||
                persona.getTelefono() == null || persona.getCorreo() == null ||
                persona.getNombre().isEmpty() || persona.getApellido().isEmpty() ||
                persona.getTelefono().isEmpty() || persona.getCorreo().isEmpty()) {

                json.put("status", "ERROR");
                json.put("mensaje", "Faltan parámetros requeridos");
                return json;
            }

            persona.setEstado(1); // Activo por defecto
            PersonaDAO dao = new PersonaDAO();
            dao.InsertarPersona(persona);

            json.put("status", "OK");
            json.put("mensaje", "Persona insertada correctamente con Hibernate");

        } catch (Exception e) {
            json.put("status", "ERROR");
            json.put("mensaje", "Error al insertar persona: " + e.getMessage());
        }
        return json;
    }
    
    @PUT
    @Path("ModificarPersona")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject ModificarPersona(PersonaEntity persona) {
        JSONObject json = new JSONObject();

        try {
            if (persona.getIdPersona() <= 0) {
                json.put("status", "ERROR");
                json.put("mensaje", "IdPersona inválido");
                return json;
            }

            PersonaDAO dao = new PersonaDAO();
            PersonaEntity existente = dao.ConsultarPersona(persona.getIdPersona());

            if (existente == null) {
                json.put("status", "ERROR");
                json.put("mensaje", "No se encontró la persona con ID: " + persona.getIdPersona());
                return json;
            }

            // Actualizamos los campos
            existente.setNombre(persona.getNombre());
            existente.setApellido(persona.getApellido());
            existente.setTelefono(persona.getTelefono());
            existente.setCorreo(persona.getCorreo());
            existente.setEstado(persona.getEstado());

            dao.ModificarPersona(existente);

            json.put("status", "OK");
            json.put("mensaje", "Persona actualizada correctamente con Hibernate");

        } catch (Exception e) {
            json.put("status", "ERROR");
            json.put("mensaje", "Error al modificar persona: " + e.getMessage());
        }

        return json;
    }

    
    @DELETE
    @Path("BorrarPersona")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject BorrarPersona(@QueryParam("IdPersona") int IdPersona) {
        JSONObject json = new JSONObject();

        try {
            if (IdPersona <= 0) {
                json.put("status", "ERROR");
                json.put("mensaje", "IdPersona inválido");
                return json;
            }

            PersonaDAO dao = new PersonaDAO();
            dao.BorrarPersona(IdPersona); // Implementación lógica: estado = 0

            json.put("status", "OK");
            json.put("mensaje", "Persona marcada como inactiva (borrado lógico)");

        } catch (Exception e) {
            json.put("status", "ERROR");
            json.put("mensaje", "Error al borrar persona: " + e.getMessage());
        }

        return json;
    }
    
    @GET
    @Path("ConsultarPersona")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject ConsultarPersona(@QueryParam("IdPersona") int IdPersona) {
        JSONObject json = new JSONObject();

        try {
            if (IdPersona <= 0) {
                json.put("status", "ERROR");
                json.put("mensaje", "IdPersona inválido");
                return json;
            }

            PersonaDAO dao = new PersonaDAO();
            PersonaEntity persona = dao.ConsultarPersona(IdPersona);

            if (persona != null && persona.getEstado() == 1) {
                json.put("status", "OK");
                json.put("IdPersona", persona.getIdPersona());
                json.put("Nombre", persona.getNombre());
                json.put("Apellido", persona.getApellido());
                json.put("Telefono", persona.getTelefono());
                json.put("Correo", persona.getCorreo());
            } else {
                json.put("status", "ERROR");
                json.put("mensaje", "No se encontró persona activa con ID: " + IdPersona);
            }

        } catch (Exception e) {
            json.put("status", "ERROR");
            json.put("mensaje", "Error al consultar persona: " + e.getMessage());
        }

        return json;
    }
    
    @GET
    @Path("ListarPersonas")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject listarPersonas() {
        JSONObject json = new JSONObject();
        try {
            PersonaDAO dao = new PersonaDAO();
            List<PersonaEntity> personas = dao.listarPersonasActivas();

            json.put("status", "OK");
            json.put("personas", personas);

        } catch (Exception e) {
            json.put("status", "ERROR");
            json.put("mensaje", "Error al listar personas: " + e.getMessage());
        }
        return json;
    }
}