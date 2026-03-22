package cr.ac.una.bolsaempleo.dtos;

public class AdministradorDTO {

    private String idAdmin;
    private String nombreAdmin;

    public AdministradorDTO() {
    }

    public AdministradorDTO(String idAdmin, String nombreAdmin) {
        this.idAdmin = idAdmin;
        this.nombreAdmin = nombreAdmin;
    }

    public String getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(String idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getNombreAdmin() {
        return nombreAdmin;
    }

    public void setNombreAdmin(String nombreAdmin) {
        this.nombreAdmin = nombreAdmin;
    }
}