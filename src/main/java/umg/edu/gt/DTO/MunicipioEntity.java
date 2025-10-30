package umg.edu.gt.DTO;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "MUNICIPIO")
@NamedQueries({
    @NamedQuery(name = "Municipio.findAll", query = "SELECT m FROM MunicipioEntity m"),
    @NamedQuery(name = "Municipio.findByIdMunicipio", query = "SELECT m FROM MunicipioEntity m WHERE m.idMunicipio = :idMunicipio")
})
public class MunicipioEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDMUNICIPIO")
    private int idMunicipio;

    @Column(name = "NOMBRE", length = 50)
    private String nombre;

    @Column(name = "IDDEPARTAMENTO")
    private int idDepartamento;

    @Column(name = "ESTADO")
    private int estado;

    // Constructores
    public MunicipioEntity() {}

    public MunicipioEntity(String nombre, int idDepartamento, int estado) {
        this.nombre = nombre;
        this.idDepartamento = idDepartamento;
        this.estado = estado;
    }

    // Getters y Setters
    public int getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(int idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    @Transient
    private String nombreDepartamento;

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

}