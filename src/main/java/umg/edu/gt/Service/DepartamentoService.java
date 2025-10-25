package umg.edu.gt.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;
import umg.edu.gt.DTO.DepartamentoEntity;

public class DepartamentoService {

    private static final String BASE_URL = "http://localhost:8080/wsprograIII/webresources/wsprograIII";
    private HttpClient client = HttpClient.newHttpClient();
    private Gson gson = new Gson();

    // Método común para enviar request
    private String enviarRequest(HttpRequest request) throws Exception {
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    // Insertar departamento (POST)
    public String insertarDepartamento(DepartamentoEntity departamento) throws Exception {
        String json = gson.toJson(departamento);
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/InsertarDepartamento"))
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();
        return enviarRequest(request);
    }

    // Modificar departamento (PUT)
    public String modificarDepartamento(DepartamentoEntity departamento) throws Exception {
        String json = gson.toJson(departamento);
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/ModificarDepartamento"))
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .PUT(HttpRequest.BodyPublishers.ofString(json))
            .build();
        return enviarRequest(request);
    }

    // Borrar departamento (DELETE)
    public String borrarDepartamento(int id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/BorrarDepartamento?IdDepartamento=" + id))
            .header("Accept", "application/json")
            .DELETE()
            .build();
        return enviarRequest(request);
    }

    // Consultar departamento (GET)
    public String consultarDepartamento(int id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/ConsultarDepartamento?IdDepartamento=" + id))
            .header("Accept", "application/json")
            .GET()
            .build();
        return enviarRequest(request);
    }

    // Listar departamentos (GET)
    public String listarDepartamentos() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BASE_URL + "/ListarDepartamento"))
            .header("Accept", "application/json")
            .GET()
            .build();
        return enviarRequest(request);
    }
}