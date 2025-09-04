package umg.edu.gt.BD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import umg.edu.gt.DTO.UsuarioDTO;

public class DAO {
    public void InsertarUsuario(UsuarioDTO pUsuario){
        Conexion con = new Conexion();
        
        String sql = "INSERT INTO USUARIO (NombreUsuario, Correo) VALUES (?,?)";
        
        try{
            Connection conn = con.getConnection();
            PreparedStatement pstm = conn.prepareStatement(sql);            
            pstm.setString(1, pUsuario.getNombreUsuario());
            pstm.setString(2, pUsuario.getCorreo());            
            pstm.executeUpdate();
            System.out.println("Datos insertados correctamente...");
        }catch(Exception e){
            System.out.println("ERROR" + e);
        }     
    }    
        
    public void UpdateUsuario(UsuarioDTO pUsuario) {
        Conexion con = new Conexion();
        String sql = "UPDATE USUARIO SET NombreUsuario = ?, Correo = ? WHERE IdUsuario = ?";

        try (Connection conn = con.getConnection(); 
             PreparedStatement pstm = conn.prepareStatement(sql)) {

            pstm.setString(1, pUsuario.getNombreUsuario());
            pstm.setString(2, pUsuario.getCorreo());
            pstm.setInt(3, pUsuario.getIdUsuario());

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
    
}
