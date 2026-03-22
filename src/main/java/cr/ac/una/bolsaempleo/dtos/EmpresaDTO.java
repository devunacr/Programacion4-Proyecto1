package cr.ac.una.bolsaempleo.dtos;

public class EmpresaDTO {

    private String id;
    private String nombre;
    private String direccion;
    private String correo;
    private String telefono;
    private String descripcion;

    public EmpresaDTO() {
    }

    public EmpresaDTO(String id, String nombre, String direccion, String correo, String telefono, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.correo = correo;
        this.telefono = telefono;
        this.descripcion = descripcion;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public String getCorreo() { return correo; }
    public String getTelefono() { return telefono; }
    public String getDescripcion() { return descripcion; }

    public void setId(String id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}