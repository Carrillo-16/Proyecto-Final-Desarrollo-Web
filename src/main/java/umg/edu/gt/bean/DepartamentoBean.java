package umg.edu.gt.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.RowEditEvent;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import umg.edu.gt.Service.DepartamentoService;
import umg.edu.gt.DTO.DepartamentoEntity;

@Named("departamentoBean")
@ViewScoped
public class DepartamentoBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private DepartamentoService departamentoService = new DepartamentoService();
    private Gson gson = new Gson();
    private DepartamentoEntity departamento;
    private List<DepartamentoEntity> listarDepartamento;

    @PostConstruct
    public void init() {
        this.limpiar();
        this.listarDepartamento();
    }

    // Insertar departamento
    public void ingresarDepartamento() {
        try {
            String response = departamentoService.insertarDepartamento(departamento);
            JsonObject json = gson.fromJson(response, JsonObject.class);

            if ("OK".equals(json.get("status").getAsString())) {
                this.limpiar();
                this.listarDepartamento();
                System.out.println("Departamento insertado correctamente.");
            } else {
                System.out.println("Error al insertar departamento: " + json.get("mensaje").getAsString());
            }
        } catch (Exception e) {
            System.out.println("Error al consumir servicio insertarDepartamento: " + e.getMessage());
        }
    }

    // Listar departamentos    
    public void listarDepartamento() {
        try {
            String jsonResponse = departamentoService.listarDepartamentos();
            JsonObject json = gson.fromJson(jsonResponse, JsonObject.class);

            if ("OK".equals(json.get("status").getAsString()) && json.has("departamentos")) {
                List<DepartamentoEntity> listaTemp = new ArrayList<>();
                for (JsonElement elem : json.getAsJsonArray("departamentos")) {
                    JsonObject dep = elem.getAsJsonObject();
                    DepartamentoEntity d = new DepartamentoEntity();
                    d.setIdDepartamento(dep.get("IdDepartamento").getAsInt());
                    d.setNombre(dep.get("Nombre").getAsString());
                    listaTemp.add(d);
                }
                listarDepartamento = listaTemp;
                System.out.println("Departamentos cargados: " + listarDepartamento.size());
            } else {
                listarDepartamento = new ArrayList<>();
                System.out.println("No hay departamentos.");
            }
        } catch (Exception e) {
            listarDepartamento = new ArrayList<>();
            System.out.println("Error al listar departamentos: " + e.getMessage());
        }
    }

    // Editar departamento (PUT)
    public void editarDepartamento(RowEditEvent<DepartamentoEntity> event) {
        DepartamentoEntity departamentoEditado = event.getObject();
        try {
            String response = departamentoService.modificarDepartamento(departamentoEditado);
            JsonObject json = gson.fromJson(response, JsonObject.class);

            if ("OK".equals(json.get("status").getAsString())) {
                listarDepartamento();
                System.out.println("Departamento modificado correctamente.");
            } else {
                System.out.println("Error al modificar departamento: " + json.get("mensaje").getAsString());
            }
        } catch (Exception e) {
            System.out.println("Error al editar departamento: " + e.getMessage());
        }
    }

    // Cancelar edición
    public void cancelarEdicion(RowEditEvent<DepartamentoEntity> event) {
        System.out.println("Edición cancelada para ID: " + event.getObject().getIdDepartamento());
    }

    // Eliminar departamento (DELETE)
    public void eliminarDepartamento(DepartamentoEntity departamento) {
        try {
            int id = departamento.getIdDepartamento();
            String response = departamentoService.borrarDepartamento(id);
            JsonObject json = gson.fromJson(response, JsonObject.class);

            if ("OK".equals(json.get("status").getAsString())) {
                listarDepartamento();
                System.out.println("Departamento eliminado correctamente.");
            } else {
                System.out.println("Error al eliminar departamento: " + json.get("mensaje").getAsString());
            }
        } catch (Exception e) {
            System.out.println("Error al consumir servicio borrarDepartamento: " + e.getMessage());
        }
    }

    // Limpiar formulario
    public void limpiar() {
        departamento = new DepartamentoEntity();
    }

    // Getters y Setters
    public DepartamentoEntity getDepartamento() {
        if (departamento == null) {
            departamento = new DepartamentoEntity();
        }
        return departamento;
    }

    public void setDepartamento(DepartamentoEntity departamento) {
        this.departamento = departamento;
    }

    public List<DepartamentoEntity> getListarDepartamento() {
        return listarDepartamento;
    }

    public void setListarDepartamento(List<DepartamentoEntity> listarDepartamento) {
        this.listarDepartamento = listarDepartamento;
    }
}