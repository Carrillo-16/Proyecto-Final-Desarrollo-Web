package umg.edu.gt.wsprograiii;

public class Persona {
    private String nombre;
    private String edad;
    private String correo;
        
    public Persona(){
        
    };
    
     public Persona( String nombre, String edad, String correo){
         this.nombre=nombre;
         this.edad =edad;
         this.correo=correo;
    };
        
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }
    
   
    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }           
}