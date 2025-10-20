package umg.edu.gt.wsprograiii;

import java.time.LocalDate;
import java.time.Period;
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

    /*@GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("PrimerWS")
    public String PrimerWS() {
        return "Hola desde un webservice de CARRILLO#1";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("SegundoWS")
    public String SegundoWS() {
        return "Hola desde un segundo webservice de CARRILLO#2";
    }
        
    @GET
    @Produces (MediaType.APPLICATION_JSON)
    @Path("TercerWS")                          //path  TERCER WEBserviCE
    public Persona TercerWS(@QueryParam("pNombre") String pNombre,
                            @QueryParam("pEdad") String pEdad,
                            @QueryParam("pCorreo") String pCorreo) {
        System.out.println("pNombre= " + pNombre);
        System.out.println("pEdad= " + pEdad);
        System.out.println("pcorreo= " + pCorreo);
        
        Persona persona1 =  new Persona(pNombre, pEdad, pCorreo);
        return persona1;
        
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("QuintoWS")
    public JSONObject QuintoWS(
        @QueryParam("pUniversidad") String pUniversidad,
        @QueryParam("pNombreCurso") String pNombreCurso,
        @QueryParam("pCarnet") String pCarnet,
        @QueryParam("pNombreAlumno") String pNombreAlumno,
        @QueryParam("pFechaNacimiento") String pFechaNacimiento
    ) {
        System.out.println("Universidad: " + pUniversidad);
        System.out.println("Nombre del Curso: " + pNombreCurso);
        System.out.println("Carnet: " + pCarnet);
        System.out.println("Nombre del Alumno: " + pNombreAlumno);
        System.out.println("Fecha de Nacimiento: " + pFechaNacimiento);
        
        //Adional Variable EDAD
        LocalDate CalcularEdad = LocalDate.parse(pFechaNacimiento);
        LocalDate hoy = LocalDate.now();
        int pEdad = Period.between(CalcularEdad, hoy).getYears();

        JSONObject json = new JSONObject();
        json.put("Universidad", pUniversidad);
        json.put("Curso", pNombreCurso);
        json.put("Carnet", pCarnet);
        json.put("Nombre Alumno", pNombreAlumno);
        json.put("Fecha de Nacimiento", pFechaNacimiento);
        json.put("Edad", pEdad);
   
        return json;
    }*/                   
        
    /**
     * NUEVOS WEB SERVICES FUNCIONALES CON HIBERNATE
     */
    
    @GET
    @Path("Insertar")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject Insertar(
        @QueryParam("Nombre") String Nombre,
        @QueryParam("Apellido") String Apellido,
        @QueryParam("Telefono") String Telefono,
        @QueryParam("Correo") String Correo
    ) {
        JSONObject json = new JSONObject();

        try {
            if (Nombre == null || Apellido == null || Telefono == null || Correo == null ||
                Nombre.isEmpty() || Apellido.isEmpty() || Telefono.isEmpty() || Correo.isEmpty()) {

                json.put("status", "ERROR");
                json.put("mensaje", "Faltan parámetros requeridos");
                return json;
            }

            // Aquí usas la entidad y DAO Hibernate
            PersonaEntity persona = new PersonaEntity();
            persona.setNombre(Nombre);
            persona.setApellido(Apellido);
            persona.setTelefono(Telefono);
            persona.setCorreo(Correo);
            persona.setEstado(1);

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
    
    @GET
    @Path("Modificar")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject Modificar(
        @QueryParam("IdPersona") int IdPersona,
        @QueryParam("Nombre") String Nombre,
        @QueryParam("Apellido") String Apellido,
        @QueryParam("Telefono") String Telefono,
        @QueryParam("Correo") String Correo,
        @QueryParam("Estado") int Estado
    ) {
        JSONObject json = new JSONObject();

        try {
            if (IdPersona <= 0 || Nombre == null || Apellido == null || Telefono == null || Correo == null ||
                Nombre.isEmpty() || Apellido.isEmpty() || Telefono.isEmpty() || Correo.isEmpty()) {

                json.put("status", "ERROR");
                json.put("mensaje", "Faltan parámetros requeridos o IdPersona inválido");
                return json;
            }

            PersonaDAO dao = new PersonaDAO();
            PersonaEntity persona = dao.ConsultarPersona(IdPersona);

            if (persona == null) {
                json.put("status", "ERROR");
                json.put("mensaje", "No se encontró la persona con ID: " + IdPersona);
                return json;
            }

            persona.setNombre(Nombre);
            persona.setApellido(Apellido);
            persona.setTelefono(Telefono);
            persona.setCorreo(Correo);
            persona.setEstado(Estado);

            dao.ModificarPersona(persona);

            json.put("status", "OK");
            json.put("mensaje", "Persona actualizada correctamente con Hibernate");

        } catch (Exception e) {
            json.put("status", "ERROR");
            json.put("mensaje", "Error al modificar persona: " + e.getMessage());
        }

        return json;
    }

    @GET
    @Path("Borrar")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject Borrar(@QueryParam("IdPersona") int IdPersona) {
        JSONObject json = new JSONObject();

        try {
            if (IdPersona <= 0) {
                json.put("status", "ERROR");
                json.put("mensaje", "IdPersona inválido");
                return json;
            }

            PersonaDAO dao = new PersonaDAO();
            dao.BorrarPersona(IdPersona);

            json.put("status", "OK");
            json.put("mensaje", "Persona marcada como inactiva con Hibernate");

        } catch (Exception e) {
            json.put("status", "ERROR");
            json.put("mensaje", "Error al borrar persona: " + e.getMessage());
        }

        return json;
    }

    @GET
    @Path("Consultar")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject Consultar(@QueryParam("IdPersona") int IdPersona) {
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
                json.put("mensaje", "No se encontró persona con ID: " + IdPersona);
            }

        } catch (Exception e) {
            json.put("status", "ERROR");
            json.put("mensaje", "Error al consultar persona: " + e.getMessage());
        }

        return json;
    }
}