package umg.edu.gt.DTO;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "DEPARTAMENTO")
@NamedQueries({
    @NamedQuery(name = "Departamento.findAll", query = "SELECT d FROM DepartamentoEntity d"),
    @NamedQuery(name = "Departamento.findByIdDepartamento", query = "SELECT d FROM DepartamentoEntity d WHERE d.idDepartamento = :idDepartamento")
})
public class DepartamentoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDDEPARTAMENTO")   
    private int idDepartamento;

    @Column(name = "NOMBRE", length = 50, unique = true)    
    private String nombre;

    // Constructores
    public DepartamentoEntity() {}

    public DepartamentoEntity(String nombre) {
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getIdDepartamento() { 
        return idDepartamento; 
    }
    
    public void setIdDepartamento(int idDepartamento) { 
        this.idDepartamento = idDepartamento; 
    }

    public String getNombre() { 
        return nombre; 
    }
    
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }
}