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
import umg.edu.gt.BD.DAO;
import umg.edu.gt.BD.PersonaDAO;
import umg.edu.gt.DTO.PersonaDTO;
import umg.edu.gt.DTO.PersonaEntity;
import umg.edu.gt.DTO.UsuarioDTO;

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

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("PrimerWS")
    public String PrimerWS() {
        return "Hola desde un webservice de Marlon#1";
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("SegundoWS")
    public String SegundoWS() {
        return "Hola desde un segundo webservice de Marlon#2";
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
    @Produces (MediaType.APPLICATION_JSON)
    @Path("CuartoWS")                          //path  TERCER WEBserviCE
    public JSONObject CuartoWS(@QueryParam("pNombre") String pNombre,
                            @QueryParam("pEdad") String pEdad,
                            @QueryParam("pCorreo") String pCorreo) {
        System.out.println("pNombre= " + pNombre);
        System.out.println("pEdad= " + pEdad);
        System.out.println("pcorreo= " + pCorreo);
        
        JSONObject json = new JSONObject();
        json.put("Nombre:",pNombre);
        json.put("Edad", pEdad);
        json.put("Correo", pCorreo);     
        System.out.println("El Json es: " +json.toJSONString());
        
        return json;
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
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("SextoWS")
    public JSONObject SextoWS(
        @QueryParam("nombre") String nombre,
        @QueryParam("correo") String correo
    ) {
        JSONObject json = new JSONObject();
        if (nombre == null || nombre.isEmpty() || correo == null || correo.isEmpty()) {
            json.put("status", "ERROR");
            json.put("mensaje", "Faltan parámetros obligatorios");
        } else {
            json.put("status", "OK");
            json.put("nombre", nombre);
            json.put("correo", correo);
        }
        return json;   
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("SeptimoWS")
    public JSONObject SeptimoWS(
        @QueryParam("n1") double n1,
        @QueryParam("n2") double n2
    ) {
        JSONObject json = new JSONObject();
        json.put("Suma", n1 + n2);
        json.put("Resta", n1 - n2);
        json.put("Multiplicación", n1 * n2);
        json.put("División", n2 != 0 ? n1 / n2 : "Error: División por cero");
        return json;
    }
        
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("Insertar")
    public JSONObject Insertar(
        @QueryParam("Nombre") String Nombre,
        @QueryParam("Apellido") String Apellido,
        @QueryParam("Telefono") String Telefono,
        @QueryParam("Correo") String Correo        
    ) {
        JSONObject json = new JSONObject();

        try {
            if (Nombre == null || Apellido == null ||
                Telefono == null || Correo == null ||               
                Nombre.isEmpty() || Apellido.isEmpty() ||
                Telefono.isEmpty() || Correo.isEmpty()){
                
                json.put("status", "ERROR");
                json.put("mensaje", "Faltan parámetros requeridos");
                return json;
            }

            PersonaDTO persona = new PersonaDTO();
            persona.setNombre(Nombre);
            persona.setApellido(Apellido);
            persona.setTelefono(Telefono);
            persona.setCorreo(Correo);
            persona.setEstado(1);

            DAO dao = new DAO();
            dao.InsertarPersona(persona);

            json.put("status", "OK");
            json.put("mensaje", "Usuario insertado correctamente desde el equipo adminsitrador");
        } catch (Exception e) {
            json.put("status", "ERROR");
            json.put("mensaje", "Error al insertar usuario: " + e.getMessage());
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
        @QueryParam("Correo") String Correo,
        @QueryParam("Telefono") String Telefono,
        @QueryParam("Estado") int Estado
    ) {
        JSONObject json = new JSONObject();

        try {
                if (Nombre == null || Apellido == null || 
                    Correo == null || Telefono == null ||
                    Nombre.isEmpty() || Apellido.isEmpty() ||
                    Correo.isEmpty() || Telefono.isEmpty()){
                    
                json.put("status", "ERROR");
                json.put("mensaje", "Faltan parámetros requeridos");
                return json;
            }

            PersonaDTO Persona = new PersonaDTO();
            Persona.setIdPersona(IdPersona);
            Persona.setNombre(Nombre);
            Persona.setApellido(Apellido);
            Persona.setCorreo(Correo);
            Persona.setTelefono(Telefono);
            Persona.setEstado(Estado);

            DAO dao = new DAO();
            dao.ModificarPersona(Persona);

            json.put("status", "OK");
            json.put("mensaje", "Usuario actualizado correctamente desde el equipo adminsitrador");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("status", "ERROR");
            json.put("mensaje", "Error al actualizar usuario: " + e.toString());
        }

        return json;
    }
    
    @GET
    @Path("Borrar")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject Borrar(@QueryParam("IdPersona") int IdPersona) {
        JSONObject json = new JSONObject();

        try {
            PersonaDTO Persona = new PersonaDTO();
            Persona.setIdPersona(IdPersona);
            Persona.setEstado(0); 

            DAO dao = new DAO();
            dao.BorrarPersona(Persona);

            json.put("status", "OK");
            json.put("mensaje", "Usuario marcado como inactivo correctamente desde el equipo adminsitrador.");

        } catch (Exception e) {
            json.put("status", "ERROR");
            json.put("mensaje", "Error al intentar eliminar usuario: " + e.getMessage());
        }

        return json;
    }


    @GET
    @Path("Consulta")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject Consulta(
        @QueryParam("IdPersona") int IdPersona
    ) {
        JSONObject json = new JSONObject();

        try {
            PersonaDTO Persona = new PersonaDTO();
            Persona.setIdPersona(IdPersona);

            DAO dao = new DAO();
            dao.ListarPersona(Persona); // este ya llena los datos en el mismo objeto

            // Validamos si trajo datos
            if (Persona.getNombre() != null && Persona.getCorreo() != null && Persona.getEstado() == 1){
                json.put("status", "OK, Consulta hecha en equipo administrador");
                json.put("IdPersona", Persona.getIdPersona());
                json.put("Nombre", Persona.getNombre());
                json.put("Apellido", Persona.getApellido());
                json.put("Telefono", Persona.getTelefono());
                json.put("Correo", Persona.getCorreo());
            } else {
                json.put("status", "ERROR");
                json.put("mensaje", "No se encontró usuario con ID: " + IdPersona);
            }

        } catch (Exception e) {
            json.put("status", "ERROR");
            json.put("mensaje", "Error al consultar usuario: " + e.getMessage());
        }
        
        return json;
    }
    
    
    
    
    /**
     * NUEVOS WEB SERVICES FUNCIONALES CON HIBERNATE
     */
    
    @GET
    @Path("Insertar2")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject Insertar2(
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
    @Path("Modificar2")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject Modificar2(
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
    @Path("Borrar2")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject Borrar2(@QueryParam("IdPersona") int IdPersona) {
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
    @Path("Consultar2")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject Consultar2(@QueryParam("IdPersona") int IdPersona) {
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
    
    @GET
    @Path("Listar")
    @Produces(MediaType.TEXT_PLAIN)
    public String listarPersonasActivasComoTexto() {
        StringBuilder resultado = new StringBuilder();

        try {
            PersonaDAO dao = new PersonaDAO();
            List<PersonaEntity> personas = dao.listarPersonasActivas();

            if (personas != null && !personas.isEmpty()) {
                for (PersonaEntity persona : personas) {
                    resultado.append("ID: ").append(persona.getIdPersona()).append("\n")
                             .append("Nombre: ").append(persona.getNombre()).append(" ").append(persona.getApellido()).append("\n")
                             .append("Teléfono: ").append(persona.getTelefono()).append("\n")
                             .append("Correo: ").append(persona.getCorreo()).append("\n")
                             .append("--------------------------\n");
                }
            } else {
                resultado.append("No hay personas activas.");
            }
        } catch (Exception e) {
            resultado.append("Error al listar personas activas: ").append(e.getMessage());
        }

        return resultado.toString();
    }

}