package cr.ac.una.bolsaempleo.dtos;

public class PuestoDTO {

    private String id;
    private String salario;
    private String titulo;
    private String descripcion;

    public PuestoDTO() {
    }

    public PuestoDTO(String id, String salario, String titulo, String descripcion) {
        this.id = id;
        this.salario = salario;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSalario() {
        return salario;
    }

    public void setSalario(String salario) {
        this.salario = salario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}