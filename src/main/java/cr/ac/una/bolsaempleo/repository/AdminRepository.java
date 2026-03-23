package cr.ac.una.bolsaempleo.repository;

import cr.ac.una.bolsaempleo.model.Administrador;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AdminRepository {

    private final JdbcTemplate jdbcTemplate;

    public AdminRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Administrador> buscarPorNombre(String nombreAdmin) {
        String sql = "SELECT id, nombreadmin, password FROM administrador WHERE nombreadmin = ?";
        List<Administrador> result = jdbcTemplate.query(sql, (rs, rowNum) -> new Administrador(
                rs.getString("id"),
                rs.getString("nombreadmin"),
                rs.getString("password")
        ), nombreAdmin);

        return result.stream().findFirst();
    }

    // buscarATodos(), buscarPorId(), crearObjeto(), actualizarObjeto(), eliminarObjeto()
}
