package cr.ac.una.bolsaempleo.dtos;

public class AdministradorDTO {

    private String id;
    private String nombreAdmin;

    public AdministradorDTO() {
    }

    public AdministradorDTO(String id, String nombreAdmin) {
        this.id = id;
        this.nombreAdmin = nombreAdmin;
    }

    public String getId() {
        return id;
    }

    public void setIdAdmin(long idAdmin) {
        this.id = id;
    }

    public String getNombreAdmin() {
        return nombreAdmin;
    }

    public void setNombreAdmin(String nombreAdmin) {
        this.nombreAdmin = nombreAdmin;
    }
}