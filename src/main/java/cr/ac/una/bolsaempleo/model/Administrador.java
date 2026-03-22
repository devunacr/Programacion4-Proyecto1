package cr.ac.una.bolsaempleo.model;

public class Administrador {

    private String idAdmin;
    private String nombreAdmin;
    private String password; // 🔐 interno

    public Administrador() {}

    public Administrador(String idAdmin, String nombreAdmin, String password) {
        this.idAdmin = idAdmin;
        this.nombreAdmin = nombreAdmin;
        this.password = password;
    }

    public String getIdAdmin() { return idAdmin; }
    public String getNombreAdmin() { return nombreAdmin; }
    public String getPassword() { return password; }

    public void setIdAdmin(String idAdmin) { this.idAdmin = idAdmin; }
    public void setNombreAdmin(String nombreAdmin) { this.nombreAdmin = nombreAdmin; }
    public void setPassword(String password) { this.password = password; }
}