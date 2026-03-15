package cr.ac.una.bolsaempleo.model;

public class Puesto {
    String descripcionPuesto; //se usa para el nombre del puesto
    String categoria; //para saber si es junior, mid o senior
    double salario;
    String publicacion; // privada o publica, se debe gestionar en el front

    public Puesto() {
    }

    public Puesto(String descripcionPuesto, String categoria, double salario, String publicacion) {
        this.descripcionPuesto = descripcionPuesto;
        this.categoria = categoria;
        this.salario = salario;
        this.publicacion = publicacion;
    }

    public String getDescripcionPuesto() {
        return descripcionPuesto;
    }

    public void setDescripcionPuesto(String descripcionPuesto) {
        this.descripcionPuesto = descripcionPuesto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(String publicacion) {
        this.publicacion = publicacion;
    }
}