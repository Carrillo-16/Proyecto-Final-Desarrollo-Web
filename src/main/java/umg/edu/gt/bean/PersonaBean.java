package umg.edu.gt.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.primefaces.event.RowEditEvent;

import umg.edu.gt.Service.PersonaService;
import umg.edu.gt.DTO.PersonaEntity;

@Named("personaBean")
@RequestScoped
public class PersonaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private PersonaService personaService = new PersonaService();
    private Gson gson = new Gson();

    private PersonaEntity persona;
    private List<PersonaEntity> listarPersona;

    @PostConstruct
    public void init() {
        limpiar();
        listarPersona();
    }

    // Método para limpiar el formulario
    public void limpiar() {
        persona = new PersonaEntity();
    }

    // Método para ingresar nueva persona
    public void ingresarPersona() {
        try {
            persona.setEstado(1); // Asignar estado activo

            String response = personaService.insertarPersona(persona);
            JsonObject json = gson.fromJson(response, JsonObject.class);

            if ("OK".equals(json.get("status").getAsString())) {
                limpiar();
                listarPersona();
                System.out.println("Persona agregada correctamente.");
            } else {
                System.out.println("Error al ingresar persona: " + json.get("mensaje").getAsString());
            }
        } catch (Exception e) {
            System.out.println("Error al consumir servicio insertarPersona: " + e.getMessage());
        }
    }

    //Metodo para modificar registro de persona
    public void editarPersona(RowEditEvent<PersonaEntity> event) {
        PersonaEntity personaEditada = event.getObject();
        try {
            String response = personaService.modificarPersona(personaEditada);
            JsonObject json = gson.fromJson(response, JsonObject.class);

            if ("OK".equals(json.get("status").getAsString())) {
                listarPersona();
                System.out.println("Persona modificada correctamente.");
            } else {
                System.out.println("Error al modificar persona: " + json.get("mensaje").getAsString());
            }
        } catch (Exception e) {
            System.out.println("Error al editar persona: " + e.getMessage());
        }
    }


    public void cancelarEdicion(RowEditEvent<PersonaEntity> event) {
        System.out.println("Edición cancelada para ID: " + event.getObject().getIdPersona());
    }

    //Metodo para eliminar registro de persona
    public void eliminarPersona(PersonaEntity persona) {
        try {
            int idPersona = persona.getIdPersona(); // Obtienes el ID del objeto
            String response = personaService.borrarPersona(idPersona);
            JsonObject json = gson.fromJson(response, JsonObject.class);

            if ("OK".equals(json.get("status").getAsString())) {
                listarPersona();
                System.out.println("Persona eliminada correctamente.");
            } else {
                System.out.println("Error al eliminar persona: " + json.get("mensaje").getAsString());
            }
        } catch (Exception e) {
            System.out.println("Error al consumir servicio borrarPersona: " + e.getMessage());
        }
    }


    // Método para listar personas
    public void listarPersona() {
        try {
            String jsonResponse = personaService.listarPersonas();
            JsonObject json = gson.fromJson(jsonResponse, JsonObject.class);

            if ("OK".equals(json.get("status").getAsString())) {
                listarPersona = gson.fromJson(
                    json.get("personas"), new TypeToken<List<PersonaEntity>>(){}.getType()
                );
            } else {
                listarPersona = new ArrayList<>();
                System.out.println("No se encontraron personas.");
            }
        } catch (Exception e) {
            System.out.println("Error al consumir servicio listarPersonas: " + e.getMessage());
            listarPersona = new ArrayList<>();
        }
    }

    // Getters y Setters
    public PersonaEntity getPersona() {
        if (persona == null) {
            persona = new PersonaEntity();
        }
        return persona;
    }

    public void setPersona(PersonaEntity persona) {
        this.persona = persona;
    }

    public List<PersonaEntity> getListarPersona() {
        if (listarPersona == null) {
            listarPersona = new ArrayList<>();
        }
        return listarPersona;
    }

    public void setListarPersona(List<PersonaEntity> listarPersona) {
        this.listarPersona = listarPersona;
    }
}