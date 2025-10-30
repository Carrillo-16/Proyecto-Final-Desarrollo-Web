package umg.edu.gt.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.model.SelectItem;
import org.primefaces.event.RowEditEvent;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import umg.edu.gt.Service.MunicipioService;
import umg.edu.gt.Service.DepartamentoService; // Para cargar deps
import umg.edu.gt.DTO.MunicipioEntity;
import umg.edu.gt.DTO.DepartamentoEntity;

@Named("municipioBean")
@ViewScoped
public class MunicipioBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private MunicipioService municipioService = new MunicipioService();
    private DepartamentoService departamentoService = new DepartamentoService(); // Para cargar deps
    private Gson gson = new Gson();
    private MunicipioEntity municipio;
    private List<MunicipioEntity> listarMunicipio;
    private List<SelectItem> departamentos; // Para el select

    @PostConstruct
    public void init() {
        this.limpiar();
        this.listarMunicipio();
        this.cargarDepartamentos();
    }

    // Insertar municipio
    public void ingresarMunicipio() {
        try {
            municipio.setEstado(1); // Activo por defecto
            String response = municipioService.insertarMunicipio(municipio);
            JsonObject json = gson.fromJson(response, JsonObject.class);

            if ("OK".equals(json.get("status").getAsString())) {
                this.limpiar();
                this.listarMunicipio();
                System.out.println("Municipio insertado correctamente.");
            } else {
                System.out.println("Error al insertar municipio: " + json.get("mensaje").getAsString());
            }
        } catch (Exception e) {
            System.out.println("Error al consumir servicio insertarMunicipio: " + e.getMessage());
        }
    }

    // Listar municipios
    public void listarMunicipio() {
        try {
            String jsonResponse = municipioService.listarMunicipios();
            JsonObject json = gson.fromJson(jsonResponse, JsonObject.class);

            if ("OK".equals(json.get("status").getAsString()) && json.has("municipios")) {
                List<MunicipioEntity> listaTemp = new ArrayList<>();
                for (JsonElement elem : json.getAsJsonArray("municipios")) {
                    JsonObject mun = elem.getAsJsonObject();
                    MunicipioEntity m = new MunicipioEntity();
                    m.setIdMunicipio(mun.get("IdMunicipio").getAsInt());
                    m.setNombre(mun.get("Nombre").getAsString());
                    m.setIdDepartamento(mun.get("IdDepartamento").getAsInt());

                    // Buscar nombre del departamento
                    for (SelectItem dep : departamentos) {
                        if ((int) dep.getValue() == m.getIdDepartamento()) {
                            m.setNombreDepartamento(dep.getLabel());
                            break;
                        }
                    }
                    listaTemp.add(m);
                }
                listarMunicipio = listaTemp;
                System.out.println("Municipios cargados: " + listarMunicipio.size());
            } else {
                listarMunicipio = new ArrayList<>();
                System.out.println("No hay municipios.");
            }
        } catch (Exception e) {
            listarMunicipio = new ArrayList<>();
            System.out.println("Error al listar municipios: " + e.getMessage());
        }
    }


    // Editar municipio (PUT)
    public void editarMunicipio(RowEditEvent<MunicipioEntity> event) {
        MunicipioEntity municipioEditado = event.getObject();
        try {
            String response = municipioService.modificarMunicipio(municipioEditado);
            JsonObject json = gson.fromJson(response, JsonObject.class);

            if ("OK".equals(json.get("status").getAsString())) {
                listarMunicipio();
                System.out.println("Municipio modificado correctamente.");
            } else {
                System.out.println("Error al modificar municipio: " + json.get("mensaje").getAsString());
            }
        } catch (Exception e) {
            System.out.println("Error al editar municipio: " + e.getMessage());
        }
    }

    // Cancelar edición
    public void cancelarEdicion(RowEditEvent<MunicipioEntity> event) {
        System.out.println("Edición cancelada para ID: " + event.getObject().getIdMunicipio());
    }

    // Eliminar municipio (DELETE)
    public void eliminarMunicipio(MunicipioEntity municipio) {
        try {
            int id = municipio.getIdMunicipio();
            String response = municipioService.borrarMunicipio(id);
            JsonObject json = gson.fromJson(response, JsonObject.class);

            if ("OK".equals(json.get("status").getAsString())) {
                listarMunicipio();
                System.out.println("Municipio eliminado correctamente.");
            } else {
                System.out.println("Error al eliminar municipio: " + json.get("mensaje").getAsString());
            }
        } catch (Exception e) {
            System.out.println("Error al consumir servicio borrarMunicipio: " + e.getMessage());
        }
    }

    // Cargar departamentos para el select
    private void cargarDepartamentos() {
        try {
            String jsonResponse = departamentoService.listarDepartamentos();
            JsonObject json = gson.fromJson(jsonResponse, JsonObject.class);
            departamentos = new ArrayList<>();
            if ("OK".equals(json.get("status").getAsString()) && json.has("departamentos")) {
                for (JsonElement elem : json.getAsJsonArray("departamentos")) {
                    JsonObject dep = elem.getAsJsonObject();
                    departamentos.add(new SelectItem(dep.get("IdDepartamento").getAsInt(), dep.get("Nombre").getAsString()));
                }
            }
        } catch (Exception e) {
            departamentos = new ArrayList<>();
            System.out.println("Error al cargar departamentos: " + e.getMessage());
        }
    }

    // Limpiar formulario
    public void limpiar() {
        municipio = new MunicipioEntity();
    }

    // Getters y Setters
    public MunicipioEntity getMunicipio() {
        if (municipio == null) {
            municipio = new MunicipioEntity();
        }
        return municipio;
    }

    public void setMunicipio(MunicipioEntity municipio) {
        this.municipio = municipio;
    }

    public List<MunicipioEntity> getListarMunicipio() {
        return listarMunicipio;
    }

    public void setListarMunicipio(List<MunicipioEntity> listarMunicipio) {
        this.listarMunicipio = listarMunicipio;
    }

    public List<SelectItem> getDepartamentos() {
        return departamentos;
    }
}