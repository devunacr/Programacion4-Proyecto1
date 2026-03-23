package cr.ac.una.bolsaempleo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "administrador")
public class Administrador {

    @Id
    private String id;
    private String nombreAdmin;
    private String password;

    public Administrador() {}

    public Administrador(String idAdmin, String nombreAdmin, String password) {
        this.id = idAdmin;
        this.nombreAdmin = nombreAdmin;
        this.password = password;
    }

    public String getId() { return id; }
    public void setIdAdmin(String idAdmin) { this.id = idAdmin; }

    public String getNombreAdmin() { return nombreAdmin; }
    public void setNombreAdmin(String nombreAdmin) { this.nombreAdmin = nombreAdmin; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
