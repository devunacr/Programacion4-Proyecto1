package cr.ac.una.bolsaempleo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "habilidad")
public class Habilidad {

    @Id
    private String id;
    private String nombre;

    public Habilidad() {}

    public Habilidad(String nombre) {
        this.nombre = nombre;
    }

    public Habilidad(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    @Override
    public String toString() {
        return nombre;
    }
}
