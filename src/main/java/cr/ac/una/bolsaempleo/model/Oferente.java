package cr.ac.una.bolsaempleo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "oferente")
public class Oferente {

    @Id
    private String id;
    private String nombre;
    private String apellidos;
    private String nacionalidad;
    private String telefono;
    private String correopersonal;
    private String residencia;
    private String password;

    public Oferente() {}

    public Oferente(String id, String nombre, String apellidos, String nacionalidad, String telefono, String correopersonal, String residencia, String password) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nacionalidad = nacionalidad;
        this.telefono = telefono;
        this.correopersonal = correopersonal;
        this.residencia = residencia;
        this.password = password;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getNacionalidad() { return nacionalidad; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreoPersonal() { return correopersonal; }
    public void setCorreoPersonal(String correopersonal) { this.correopersonal = correopersonal; }

    public String getResidencia() { return residencia; }
    public void setResidencia(String residencia) { this.residencia = residencia; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
