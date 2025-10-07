package umg.edu.gt.BD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import umg.edu.gt.DTO.PersonaDTO;

public class DAO {
    public void InsertarPersona(PersonaDTO pPersona){
        Conexion con = new Conexion();
        
        String sql = "INSERT INTO PERSONA (NOMBRE, APELLIDO, TELEFONO, CORREO, ESTADO) VALUES (?,?,?,?,?)";
        //String sql = "INSERT INTO USUARIO (NombreUsuario, Correo) VALUES (?,?)";
        
        try{
            Connection conn = con.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);            
            pstm.setString(1, pPersona.getNombre());
            pstm.setString(2, pPersona.getApellido());
            pstm.setString(3, pPersona.getTelefono());
            pstm.setString(4, pPersona.getCorreo());
            pstm.setInt(5, pPersona.getEstado());
            pstm.executeUpdate();
            System.out.println("Datos insertados correctamente...");
            
        }catch(Exception e){
            System.out.println("ERROR" + e);
        }     
    }
    
    public void ModificarPersona(PersonaDTO pPersona) {
        Conexion con = new Conexion();
        String sql = "UPDATE PERSONA SET NOMBRE = ?, APELLIDO = ?, TELEFONO = ?, CORREO = ? WHERE IDPERSONA = ?";

        try (Connection conn = con.getConnection(); 
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, pPersona.getNombre());
            pstm.setString(2, pPersona.getApellido());
            pstm.setString(3, pPersona.getTelefono());
            pstm.setString(4, pPersona.getCorreo());
            pstm.setInt(5, pPersona.getEstado());
            pstm.setInt(6, pPersona.getIdPersona());

            int rows = pstm.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Usuario actualizado correctamente.");
            } else {
                System.out.println("⚠️ No se encontró usuario con ese ID.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void BorrarPersona(PersonaDTO pPersona){
        Conexion con = new Conexion();
        String sql = "UPDATE PERSONA SET ESTADO = ? WHERE IDPERSONA = ?";
    
        try (Connection conn = con.getConnection(); 
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setInt(1, pPersona.getEstado());
            pstm.setInt(2, pPersona.getIdPersona());

            int rows = pstm.executeUpdate();
            if(rows > 0){
                System.out.println("✅ Usuario marcado como inactivo correctamente.");
            } else{
                System.out.println("⚠️ No se encontró usuario con ese ID.");
            } 

        } catch(Exception ex) {
            System.out.println("❌ ERROR: " + ex);
        }
    }
    
    public void ListarPersona(PersonaDTO pPersona){
        Conexion con = new Conexion();
        String sql = "SELECT * FROM PERSONA WHERE IDPERSONA = ?";
        
        try (Connection conn = con.getConnection(); 
             PreparedStatement pstm = conn.prepareStatement(sql)) {
            
            pstm.setInt(1, pPersona.getIdPersona());
            ResultSet rs = pstm.executeQuery();
            
            if (rs.next()) {
                pPersona.setNombre(rs.getString("Nombre"));
                pPersona.setApellido(rs.getString("Apellido"));
                pPersona.setTelefono(rs.getString("Telefono"));
                pPersona.setCorreo(rs.getString("Correo"));
                pPersona.setEstado(rs.getInt("Estado")); 

                System.out.println("✅ Usuario encontrado:");
                System.out.println("Nombre: " + pPersona.getNombre());
                System.out.println("Correo: " + pPersona.getCorreo());
            } else {
                System.out.println("⚠️ No se encontró usuario con ese ID.");
            }
            
        } catch(Exception ex){
            System.out.println("ERROR en ListarPersona: " + ex);
        }        
    }
}