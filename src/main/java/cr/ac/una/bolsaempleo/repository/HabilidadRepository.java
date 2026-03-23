package cr.ac.una.bolsaempleo.repository;

import cr.ac.una.bolsaempleo.model.Habilidad;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class HabilidadRepository implements IrepositoryMethods<Habilidad> {

    private final JdbcTemplate jdbcTemplate;

    public HabilidadRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Habilidad> buscarATodos() {
        String sql = "SELECT id, nombre FROM habilidad";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Habilidad(
                rs.getString("id"),
                rs.getString("nombre")
        ));
    }

    @Override
    public Optional<Habilidad> buscarPorNombre(String nombre) {
        String sql = "SELECT id, nombre FROM habilidad WHERE nombre = ?";
        List<Habilidad> result = jdbcTemplate.query(sql, (rs, rowNum) -> new Habilidad(
                rs.getString("id"),
                rs.getString("nombre")
        ), nombre);
        return result.stream().findFirst();
    }

    @Override
    public Optional<Habilidad> buscarPorId(String id) {
        String sql = "SELECT id, nombre FROM habilidad WHERE id = ?";
        List<Habilidad> result = jdbcTemplate.query(sql, (rs, rowNum) -> new Habilidad(
                rs.getString("id"),
                rs.getString("nombre")
        ), id);
        return result.stream().findFirst();
    }

    @Override
    public Habilidad crearObjeto(Habilidad habilidadNueva) {
        String sql = "INSERT INTO habilidad (id, nombre) VALUES (?, ?)";
        jdbcTemplate.update(sql, habilidadNueva.getId(), habilidadNueva.getNombre());
        return habilidadNueva;
    }

    @Override
    public Habilidad actualizarObjeto(Habilidad habilidadActualizada) {
        String sql = "UPDATE habilidad SET nombre = ? WHERE id = ?";
        jdbcTemplate.update(sql, habilidadActualizada.getNombre(), habilidadActualizada.getId());
        return habilidadActualizada;
    }

    @Override
    public void eliminarObjeto(String id) {
        String sql = "DELETE FROM habilidad WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Habilidad> buscarPorCorreo(String correo) {
        return Optional.empty();//este metodo solo lo usa AdminRepository pero como es interfaz debe estar implementado en todas las clases
    }
}
