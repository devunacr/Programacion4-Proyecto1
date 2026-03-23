package cr.ac.una.bolsaempleo.model;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Puesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private Double salario;
    @Column(length = 1000)
    private String descripcion;
    private String fecha;
    private boolean publico;
    private String categoria;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @ManyToMany
    @JoinTable(
            name = "puesto_habilidad",
            joinColumns = @JoinColumn(name = "puesto_id"),
            inverseJoinColumns = @JoinColumn(name = "habilidad_id")
    )
    private List<Habilidad> habilidades = new ArrayList<>();

    public Puesto(Long id, String titulo, Double salario, String descripcion, String fecha, boolean publico, String categoria, Empresa empresa, List<Habilidad> habilidades) {
        this.id = id;
        this.titulo = titulo;
        this.salario = salario;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.publico = publico;
        this.categoria = categoria;
        this.empresa = empresa;
        this.habilidades = habilidades;
    }

    public Puesto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isPublico() {
        return publico;
    }

    public void setPublico(boolean publico) {
        this.publico = publico;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<Habilidad> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(List<Habilidad> habilidades) {
        this.habilidades = habilidades;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}