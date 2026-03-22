package cr.ac.una.bolsaempleo.dtos;

public class OferenteDTO {

    private String id;
    private String nombre;
    private String apellidos;
    private String nacionalidad;
    private String telefono;
    private String correoPersonal;
    private String residencia;

    public OferenteDTO() {
    }

    public OferenteDTO(String id, String nombre, String apellidos, String nacionalidad,
                       String telefono, String correoPersonal, String residencia) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nacionalidad = nacionalidad;
        this.telefono = telefono;
        this.correoPersonal = correoPersonal;
        this.residencia = residencia;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
    public String getNacionalidad() { return nacionalidad; }
    public String getTelefono() { return telefono; }
    public String getCorreoPersonal() { return correoPersonal; }
    public String getResidencia() { return residencia; }

    public void setId(String id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setCorreoPersonal(String correoPersonal) { this.correoPersonal = correoPersonal; }
    public void setResidencia(String residencia) { this.residencia = residencia; }
}