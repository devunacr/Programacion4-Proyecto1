package cr.ac.una.bolsaempleo.Dtos;

public class OferenteDTO {

    String nombre;
    String Apellidos;
    String telefono;
    String correoPersonal;
    String residencia;

    public OferenteDTO() {
    }

    public OferenteDTO(String nombre, String apellidos, String telefono, String correoPersonal, String residencia) {
        this.nombre = nombre;
        Apellidos = apellidos;
        this.telefono = telefono;
        this.correoPersonal = correoPersonal;
        this.residencia = residencia;
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
