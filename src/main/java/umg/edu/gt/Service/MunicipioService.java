package umg.edu.gt.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;
import umg.edu.gt.DTO.MunicipioEntity;

public class MunicipioService {

    private static final String BASE_URL = "http://localhost:8080/wsprograIII/webresources/wsprograIII";
    private HttpClient client = HttpClient.newHttpClient();
    private Gson gson = new Gson();

    // Método común para enviar request
    private String enviarRequest(HttpRequest request) throws Exception {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    // Insertar municipio (POST)
    public String insertarMunicipio(MunicipioEntity municipio) throws Exception {
        String json = gson.toJson(municipio);
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/InsertarMunicipio"))
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();
        return enviarRequest(request);
    }

    // Modificar municipio (PUT)
    public String modificarMunicipio(MunicipioEntity municipio) throws Exception {
        String json = gson.toJson(municipio);
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/ModificarMunicipio"))
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .PUT(HttpRequest.BodyPublishers.ofString(json))
            .build();
        return enviarRequest(request);
    }

    // Borrar municipio (DELETE)
    public String borrarMunicipio(int id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/BorrarMunicipio?IdMunicipio=" + id))
            .header("Accept", "application/json")
            .DELETE()
            .build();
        return enviarRequest(request);
    }

    // Consultar municipio (GET)
    public String consultarMunicipio(int id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/ConsultarMunicipio?IdMunicipio=" + id))
            .header("Accept", "application/json")
            .GET()
            .build();
        return enviarRequest(request);
    }

    // Listar municipios (GET)
    public String listarMunicipios() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/ListarMunicipio"))
            .header("Accept", "application/json")
            .GET()
            .build();
        return enviarRequest(request);
    }
}