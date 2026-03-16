package cr.ac.una.bolsaempleo.dtos;

public class AministradorDTO {
    String nombreAdmin;
    String idAdmin;

    public AministradorDTO() {
    }

    public AministradorDTO(String nombreAdmin, String idAdmin) {
        this.nombreAdmin = nombreAdmin;
        this.idAdmin = idAdmin;
    }

    public String getNombreAdmin() {
        return nombreAdmin;
    }

    public void setNombreAdmin(String nombreAdmin) {
        this.nombreAdmin = nombreAdmin;
    }

    public String getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(String idAdmin) {
        this.idAdmin = idAdmin;
    }
}
