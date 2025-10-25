package umg.edu.gt.DTO;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PERSONA")
@NamedQueries({
    @NamedQuery(name = "Persona.findAll", query = "SELECT p FROM PersonaEntity p"),
    @NamedQuery(name = "Persona.findByIdPersona", query = "SELECT p FROM PersonaEntity p WHERE p.idPersona = :idPersona")
})
public class PersonaEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDPERSONA")
    private int idPersona;
    
    @Column(name = "NOMBRE", length = 100)
    private String nombre;
    
    @Column(name = "APELLIDO", length = 100)
    private String apellido;
    
    @Column(name = "TELEFONO", length = 20)
    private String telefono;
    
    @Column(name = "CORREO", length = 150, unique = true)
    private String correo;
    
    @Column(name = "ESTADO")
    private int estado;

    // Constructores
    public PersonaEntity() {}

    public PersonaEntity(String nombre, String apellido, String telefono, String correo, int estado) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
        this.estado = estado;
    }

    // Getters y Setters (genera con NetBeans: clic derecho > Insert Code > Getter and Setter)
    public int getIdPersona() { 
        return idPersona; 
    }
    
    public void setIdPersona(int idPersona) { 
        this.idPersona = idPersona; 
    }
    
    public String getNombre() { 
        return nombre; 
    }
    
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }
    
    public String getApellido() { 
        return apellido; 
    }
    
    public void setApellido(String apellido) { 
        this.apellido = apellido; 
    }
    
    public String getTelefono() { 
        return telefono; 
    }
    
    public void setTelefono(String telefono) { 
        this.telefono = telefono; 
    }
    
    public String getCorreo() { 
        return correo; 
    }
    
    public void setCorreo(String correo) { 
        this.correo = correo; 
    }
    
    public int getEstado() { 
        return estado; 
    }
    
    public void setEstado(int estado) { 
        this.estado = estado; 
    }
}