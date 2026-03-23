package cr.ac.una.bolsaempleo.model;
import jakarta.persistence.*;

@Entity
public class Oferente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String identificacion;
    private String nacionalidad;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String residencia;
    private String correo;
    private String password;

    public Oferente(Long id, String identificacion, String nacionalidad, String nombre, String apellidos, String telefono, String residencia, String correo, String password) {
        this.id = id;
        this.identificacion = identificacion;
        this.nacionalidad = nacionalidad;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.residencia = residencia;
        this.correo = correo;
        this.password = password;
    }

    public Oferente() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getResidencia() {
        return residencia;
    }

    public void setResidencia(String residencia) {
        this.residencia = residencia;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}