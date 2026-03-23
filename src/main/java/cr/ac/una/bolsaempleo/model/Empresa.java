package cr.ac.una.bolsaempleo.model;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Empresa {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String correo;
    private String password;
    private String localizacion;
    private String telefono;

    @OneToMany(mappedBy = "empresa")
    private List<Puesto> puestos;

    public Empresa(Long id, String nombre, String correo, String password, String localizacion, String telefono, List<Puesto> puestos) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.localizacion = localizacion;
        this.telefono = telefono;
        this.puestos = puestos;
    }

    public Empresa() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Puesto> getPuestos() {
        return puestos;
    }

    public void setPuestos(List<Puesto> puestos) {
        this.puestos = puestos;
    }
}