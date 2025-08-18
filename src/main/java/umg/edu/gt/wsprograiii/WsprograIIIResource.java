package umg.edu.gt.wsprograiii;

import java.time.LocalDate;
import java.time.Period;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.json.simple.JSONObject;

/**
 * REST Web Service
 * localhost:8080/wsprograIII/webresources/wsprograIII/CuartoWS?pNombre=Antonio Jose&pEdad=20&pCorreo=antonio@gmail.com
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
    
}
