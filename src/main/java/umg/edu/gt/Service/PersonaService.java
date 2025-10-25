package umg.edu.gt.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;
import umg.edu.gt.DTO.PersonaEntity;

public class PersonaService {

    private static final String BASE_URL = "http://localhost:8080/wsprograIII/webresources/wsprograIII";
    private HttpClient client = HttpClient.newHttpClient();
    private Gson gson = new Gson();

    // Método común para enviar peticiones HTTP
    private String enviarRequest(HttpRequest request) throws Exception {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    // Insertar persona (POST)
    public String insertarPersona(PersonaEntity persona) throws Exception {
        String json = gson.toJson(persona);
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/InsertarPersona"))
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();
        return enviarRequest(request);
    }

    // Modificar persona (PUT)
    public String modificarPersona(PersonaEntity persona) throws Exception {
        String json = gson.toJson(persona);
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/ModificarPersona"))
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .PUT(HttpRequest.BodyPublishers.ofString(json))
            .build();
        return enviarRequest(request);
    }

    // Borrar persona (DELETE)
    public String borrarPersona(int id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/BorrarPersona?IdPersona=" + id))
            .header("Accept", "application/json")
            .DELETE()
            .build();
        return enviarRequest(request);
    }

    // Consultar persona (GET)
    public String consultarPersona(int id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/ConsultarPersona?IdPersona=" + id))
            .header("Accept", "application/json")
            .GET()
            .build();
        return enviarRequest(request);
    }

    // Listar personas (GET)
    public String listarPersonas() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/ListarPersonas"))
            .header("Accept", "application/json")
            .GET()
            .build();
        return enviarRequest(request);
    }
}