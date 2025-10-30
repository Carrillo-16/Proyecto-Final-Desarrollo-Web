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
import umg.edu.gt.BD.DepartamentoDAO;
import umg.edu.gt.BD.MunicipioDAO;

import umg.edu.gt.BD.PersonaDAO;
import umg.edu.gt.DTO.DepartamentoEntity;
import umg.edu.gt.DTO.MunicipioEntity;
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
    
    /**
     * WEB SERVICES [APARTADO DEPARTAMENTO]
     */

    @POST
    @Path("InsertarDepartamento")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject InsertarDepartamento(DepartamentoEntity departamento) {
        JSONObject json = new JSONObject();
        try {
            if (departamento.getNombre() == null || departamento.getNombre().isEmpty()) {
                json.put("status", "ERROR");
                json.put("mensaje", "Nombre requerido");
                return json;
            }
            new DepartamentoDAO().InsertarDepartamento(departamento);
            json.put("status", "OK");
            json.put("mensaje", "Departamento insertado correctamente");
        } catch (Exception e) {
            json.put("status", "ERROR");
            json.put("mensaje", "Error al insertar departamento: " + e.getMessage());
        }
        return json;
    }

    @PUT
    @Path("ModificarDepartamento")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject ModificarDepartamento(DepartamentoEntity departamento) {
        JSONObject json = new JSONObject();
        try {
            if (departamento.getIdDepartamento() <= 0 || departamento.getNombre() == null || departamento.getNombre().isEmpty()) {
                json.put("status", "ERROR");
                json.put("mensaje", "Parámetros inválidos");
                return json;
            }
            new DepartamentoDAO().ModificarDepartamento(departamento);
            json.put("status", "OK");
            json.put("mensaje", "Departamento actualizado correctamente");
        } catch (Exception e) {
            json.put("status", "ERROR");
            json.put("mensaje", "Error al modificar departamento: " + e.getMessage());
        }
        return json;
    }

    @DELETE
    @Path("BorrarDepartamento")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject BorrarDepartamento(@QueryParam("IdDepartamento") int idDepartamento) {
        JSONObject json = new JSONObject();
        try {
            if (idDepartamento <= 0) {
                json.put("status", "ERROR");
                json.put("mensaje", "IdDepartamento inválido");
                return json;
            }
            new DepartamentoDAO().BorrarDepartamento(idDepartamento);
            json.put("status", "OK");
            json.put("mensaje", "Departamento eliminado");
        } catch (Exception e) {
            json.put("status", "ERROR");
            json.put("mensaje", "Error al borrar departamento: " + e.getMessage());
        }
        return json;
    }

    @GET
    @Path("ConsultarDepartamento")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject ConsultarDepartamento(@QueryParam("IdDepartamento") int idDepartamento) {
        JSONObject json = new JSONObject();
        try {
            if (idDepartamento <= 0) {
                json.put("status", "ERROR");
                json.put("mensaje", "IdDepartamento inválido");
                return json;
            }
            DepartamentoEntity departamento = new DepartamentoDAO().ConsultarDepartamento(idDepartamento);
            if (departamento != null) {
                json.put("status", "OK");
                json.put("IdDepartamento", departamento.getIdDepartamento());
                json.put("Nombre", departamento.getNombre());
            } else {
                json.put("status", "ERROR");
                json.put("mensaje", "No se encontró el departamento");
            }
        } catch (Exception e) {
            json.put("status", "ERROR");
            json.put("mensaje", "Error al consultar departamento: " + e.getMessage());
        }
        return json;
    }

    @GET
    @Path("ListarDepartamento")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject ListarDepartamento() {
        JSONObject json = new JSONObject();
        try {
            List<DepartamentoEntity> departamentos = new DepartamentoDAO().listarDepartamentos();
            if (departamentos != null && !departamentos.isEmpty()) {
                json.put("status", "OK");
                json.put("total", departamentos.size());
                List<JSONObject> lista = new java.util.ArrayList<>();
                for (DepartamentoEntity d : departamentos) {
                    JSONObject depJson = new JSONObject();
                    depJson.put("IdDepartamento", d.getIdDepartamento());
                    depJson.put("Nombre", d.getNombre());
                    lista.add(depJson);
                }
                json.put("departamentos", lista);
            } else {
                json.put("status", "OK");
                json.put("mensaje", "No hay departamentos");
            }
        } catch (Exception e) {
            json.put("status", "ERROR");
            json.put("mensaje", "Error al listar departamentos: " + e.getMessage());
        }
        return json;
    }
    
    /**
     * WEB SERVICES [APARTADO MUNICIPIO]
     */
    
    @POST
    @Path("InsertarMunicipio")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject InsertarMunicipio(MunicipioEntity municipio) {
        JSONObject json = new JSONObject();
        try {
            // Validación de parámetros
            if (municipio.getNombre() == null || municipio.getNombre().isEmpty() || municipio.getIdDepartamento() <= 0) {
                json.put("status", "ERROR");
                json.put("mensaje", "Parámetros requeridos faltantes");
                return json;
            }

            municipio.setEstado(1); // Activo por defecto
            new MunicipioDAO().InsertarMunicipio(municipio);

            json.put("status", "OK");
            json.put("mensaje", "Municipio insertado correctamente");
        } catch (Exception e) {
            json.put("status", "ERROR");
            json.put("mensaje", "Error al insertar municipio: " + e.getMessage());
        }
        return json;
    }


    @PUT
    @Path("ModificarMunicipio")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JSONObject ModificarMunicipio(MunicipioEntity municipio) {
        JSONObject json = new JSONObject();
        try {
            if (municipio.getIdMunicipio() <= 0 || municipio.getNombre() == null || municipio.getNombre().isEmpty() || municipio.getIdDepartamento() <= 0) {
                json.put("status", "ERROR");
                json.put("mensaje", "Parámetros inválidos");
                return json;
            }
            new MunicipioDAO().ModificarMunicipio(municipio);
            json.put("status", "OK");
            json.put("mensaje", "Municipio actualizado correctamente");
        } catch (Exception e) {
            json.put("status", "ERROR");
            json.put("mensaje", "Error al modificar municipio: " + e.getMessage());
        }
        return json;
    }

    @DELETE
    @Path("BorrarMunicipio")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject BorrarMunicipio(@QueryParam("IdMunicipio") int idMunicipio) {
        JSONObject json = new JSONObject();
        try {
            if (idMunicipio <= 0) {
                json.put("status", "ERROR");
                json.put("mensaje", "IdMunicipio inválido");
                return json;
            }
            new MunicipioDAO().BorrarMunicipio(idMunicipio);
            json.put("status", "OK");
            json.put("mensaje", "Municipio marcado como inactivo");
        } catch (Exception e) {
            json.put("status", "ERROR");
            json.put("mensaje", "Error al borrar municipio: " + e.getMessage());
        }
        return json;
    }

    @GET
    @Path("ConsultarMunicipio")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject ConsultarMunicipio(@QueryParam("IdMunicipio") int idMunicipio) {
        JSONObject json = new JSONObject();
        try {
            // Verificación de parámetros
            if (idMunicipio <= 0) {
                json.put("status", "ERROR");
                json.put("mensaje", "IdMunicipio inválido");
                return json;
            }

            MunicipioEntity municipio = new MunicipioDAO().ConsultarMunicipio(idMunicipio);
            if (municipio != null) {
                if (municipio.getEstado() == 1) {
                    json.put("status", "OK");
                    json.put("IdMunicipio", municipio.getIdMunicipio());
                    json.put("Nombre", municipio.getNombre());
                    json.put("IdDepartamento", municipio.getIdDepartamento());
                } else {
                    json.put("status", "ERROR");
                    json.put("mensaje", "Municipio inactivo");
                }
            } else {
                json.put("status", "ERROR");
                json.put("mensaje", "Municipio no encontrado");
            }
        } catch (Exception e) {
            json.put("status", "ERROR");
            json.put("mensaje", "Error al consultar municipio: " + e.getMessage());
        }
        return json;
    }


    @GET
    @Path("ListarMunicipio")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject ListarMunicipio() {
        JSONObject json = new JSONObject();
        try {
            List<MunicipioEntity> municipios = new MunicipioDAO().listarMunicipiosActivos();
            if (municipios != null && !municipios.isEmpty()) {
                json.put("status", "OK");
                json.put("total", municipios.size());
                List<JSONObject> lista = new java.util.ArrayList<>();
                for (MunicipioEntity m : municipios) {
                    JSONObject municipioJson = new JSONObject();
                    municipioJson.put("IdMunicipio", m.getIdMunicipio());
                    municipioJson.put("Nombre", m.getNombre());
                    municipioJson.put("IdDepartamento", m.getIdDepartamento());
                    lista.add(municipioJson);
                }
                json.put("municipios", lista);
            } else {
                json.put("status", "OK");
                json.put("mensaje", "No hay municipios activos");
            }
        } catch (Exception e) {
            json.put("status", "ERROR");
            json.put("mensaje", "Error al listar municipios: " + e.getMessage());
        }
        return json;
    }
}