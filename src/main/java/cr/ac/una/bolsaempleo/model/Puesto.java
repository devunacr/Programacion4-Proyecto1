package cr.ac.una.bolsaempleo.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "puesto")
public class Puesto {

    @Id
    private String id;
    private String salario;
    private String titulo;
    private String descripcion;
    private boolean publico = true;
    private String fecha = "2026-01-01";
    private String categoria;

    @Transient
    private Empresa empresa;

    @Transient
    private List<Habilidad> habilidades = new ArrayList<>();

    public Puesto() {}

    public Puesto(String id, String salario, String titulo, String descripcion) {
        this.id = id;
        this.salario = salario;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public void agregarHabilidad(Habilidad habilidad) {
        habilidades.add(habilidad);
    }
    public void eliminarHabilidad(Habilidad habilidad) {
        habilidades.remove(habilidad);
    }

    public List<Habilidad> getHabilidades() {
        return habilidades;
    }
    public void setHabilidades(List<Habilidad> habilidades) {
        this.habilidades = habilidades;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSalario() { return salario; }
    public void setSalario(String salario) { this.salario = salario; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Empresa getEmpresa() { return empresa; }
    public void setEmpresa(Empresa empresa) { this.empresa = empresa; }

    public boolean isPublico() { return publico; }
    public void setPublico(boolean publico) { this.publico = publico; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}