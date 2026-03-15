package cr.ac.una.bolsaempleo.model;

public class Oferente {

    String id;
    String nombre;
    String Apellidos;
    String correo;
    String nacionalidad;
    String telefono;
    String correoPersonal;
    String residencia;

    public Oferente() {
    }

    public Oferente(String id, String nombre, String apellidos, String correo, String nacionalidad, String telefono, String correoPersonal, String residencia) {
        this.id = id;
        this.nombre = nombre;
        Apellidos = apellidos;
        this.correo = correo;
        this.nacionalidad = nacionalidad;
        this.telefono = telefono;
        this.correoPersonal = correoPersonal;
        this.residencia = residencia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoPersonal() {
        return correoPersonal;
    }

    public void setCorreoPersonal(String correoPersonal) {
        this.correoPersonal = correoPersonal;
    }

    public String getResidencia() {
        return residencia;
    }

    public void setResidencia(String residencia) {
        this.residencia = residencia;
    }
}