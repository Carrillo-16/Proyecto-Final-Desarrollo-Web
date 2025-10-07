package umg.edu.gt.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Servicio {
    public String consumirServicios(String pNombre, String pEdad, String pCorreo) {
        String url = "http://localhost:8080/wsprograIII/webresources/wsprograIII/TercerWS?pNombre=" + pNombre +
                     "&pEdad=" + pEdad + "&pCorreo=" + pCorreo;
        System.out.println("la url es: " + url);
        
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .header("Accept", "application/json")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception ex) {
            System.out.println("Error al consumir el ws: " + ex);
            return "";
        }
    }
    
    public String insertarUsuario(String nombre, int edad, String correo) {
        String url = "http://localhost:8080/wsprograIII/webresources/wsprograIII/insertarUsuario";
        String json = String.format("{\"nombre\":\"%s\", \"edad\":%d, \"correo\":\"%s\"}", nombre, edad, correo);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception ex) {
            System.out.println("Error al consumir el servicio insertarUsuario: " + ex);
            return "";
        }
    }
    
    public String obtenerUsuarioPorId(String id) {
        String url = "http://localhost:8080/wsprograIII/webresources/wsprograIII/obtenerUsuario?id=" + id;
        System.out.println("La URL es: " + url);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .header("Accept", "application/json")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception ex) {
            System.out.println("Error al consumir obtenerUsuario: " + ex);
            return "";
        }
    }

    public String registrarUsuario(String nombre, int edad, String correo) {
        String url = "http://localhost:8080/wsprograIII/webresources/wsprograIII/registrarUsuario";
        String json = String.format("{\"nombre\":\"%s\", \"edad\":%d, \"correo\":\"%s\"}", nombre, edad, correo);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception ex) {
            System.out.println("Error al consumir registrarUsuario: " + ex);
            return "";
        }
    }

    public String eliminarUsuario(String correo) {
        String url = "http://localhost:8080/wsprograIII/webresources/wsprograIII/eliminarUsuario?correo=" + correo;
        System.out.println("La URL es: " + url);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .DELETE()
                .header("Accept", "application/json")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception ex) {
            System.out.println("Error al consumir eliminarUsuario: " + ex);
            return "";
        }
    }
}