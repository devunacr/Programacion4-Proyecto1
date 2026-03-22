package cr.ac.una.bolsaempleo.model;

import java.util.ArrayList;
import java.util.List;

public class Puesto {

    private String id;
    private String salario;
    private String titulo;
    private String descripcion;

    // Relación del pueto con la habilidad
    private List<Habilidad> habilidades = new ArrayList<>();


    public Puesto() {
    }

    public Puesto(String id, String salario, String titulo, String descripcion) {
        this.id = id;
        this.salario = salario;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    // Métodos de relación **********************************************************************

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
     //**********************************************************************


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