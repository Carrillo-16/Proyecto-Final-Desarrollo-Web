package umg.edu.gt.BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
        
public class Conexion {
    //private static final String URL = "jdbc:mysql://192.168.10.20:3306/UMG2025?useSSL=false&serverTimezone=UTC"; //SERVIDOR POR CABLEADO
    //private static final String URL = "jdbc:mysql://192.168.192.11:3306/UMG2025?useSSL=false&serverTimezone=UTC"; //SERVIDOR POR VPN
    //private static final String USER = "root";   
    //private static final String PASSWORD = "root"; 

    public static Connection getConnection() throws SQLException {
        /*try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException ex) {
            throw new SQLException("Error al cargar el driver de MySQL", ex);
        }*/
        return null;
    };
}