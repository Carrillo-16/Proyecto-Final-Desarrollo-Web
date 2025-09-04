package umg.edu.gt.BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
        
public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/UMG2025?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";   
    private static final String PASSWORD = "Carrillo#16*"; 

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException ex) {
            throw new SQLException("Error al cargar el driver de MySQL", ex);
        }
    };
}