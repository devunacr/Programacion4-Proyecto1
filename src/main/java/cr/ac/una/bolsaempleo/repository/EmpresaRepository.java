package cr.ac.una.bolsaempleo.repository;

import cr.ac.una.bolsaempleo.model.Empresa;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class EmpresaRepository implements IrepositoryMethods<Empresa> {

    private final JdbcTemplate jdbcTemplate;

    public EmpresaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Empresa> buscarATodos() {
        String sql = "SELECT id, nombre, correo FROM empresa";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Empresa(
                rs.getString("id"),
                rs.getString("nombre"),
                null, // dirección
                rs.getString("correo"),
                null, // teléfono
                null, // descripción
                null  // password
        ));
    }

    @Override
    public Optional<Empresa> buscarPorId(String id) {
        String sql = "SELECT id, nombre, correo FROM empresa WHERE id = ?";
        List<Empresa> result = jdbcTemplate.query(sql, (rs, rowNum) -> new Empresa(
                rs.getString("id"),
                rs.getString("nombre"),
                null,
                rs.getString("correo"),
                null,
                null,
                null
        ), id);
        return result.stream().findFirst();
    }

    @Override
    public Empresa crearObjeto(Empresa empresaNueva) {
        String sql = "INSERT INTO empresa (id, nombre, correo) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, empresaNueva.getId(), empresaNueva.getNombre(), empresaNueva.getCorreo());
        return empresaNueva;
    }

    @Override
    public Empresa actualizarObjeto(Empresa empresaActualizada) {
        String sql = "UPDATE empresa SET nombre = ?, correo = ? WHERE id = ?";
        jdbcTemplate.update(sql, empresaActualizada.getNombre(), empresaActualizada.getCorreo(), empresaActualizada.getId());
        return empresaActualizada;
    }

    @Override
    public void eliminarObjeto(String id) {
        String sql = "DELETE FROM empresa WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Empresa> buscarPorCorreo(String correo) {
        String sql = "SELECT id, nombre, direccion, telefono, correo, password " +
                "FROM empresa WHERE correo = ?";
        List<Empresa> result = jdbcTemplate.query(sql, (rs, rowNum) -> new Empresa(
                rs.getString("id"),
                rs.getString("nombre"),
                rs.getString("direccion"),
                rs.getString("correo"),
                rs.getString("telefono"),
                rs.getString("descripcion"),
                rs.getString("password")
        ), correo);

        return result.stream().findFirst();
    }

    @Override
    public Optional<Empresa> buscarPorNombre(String nombre) {
        String sql = "SELECT id, nombre, correo FROM empresa WHERE nombre = ?";
        List<Empresa> result = jdbcTemplate.query(sql, (rs, rowNum) -> new Empresa(
                rs.getString("id"),
                rs.getString("nombre"),
                null,
                rs.getString("correo"),
                null,
                null,
                null
        ), nombre);
        return result.stream().findFirst();
    }
}
