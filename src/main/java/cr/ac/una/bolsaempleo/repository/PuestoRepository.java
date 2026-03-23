package cr.ac.una.bolsaempleo.repository;

import cr.ac.una.bolsaempleo.model.Puesto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PuestoRepository implements IrepositoryMethods<Puesto> {

    private final JdbcTemplate jdbcTemplate;

    public PuestoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Puesto> buscarATodos() {
        String sql = "SELECT id, salario, titulo, descripcion FROM puesto";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Puesto(
                rs.getString("id"),
                rs.getString("salario"),
                rs.getString("titulo"),
                rs.getString("descripcion")
        ));
    }

    @Override
    public Optional<Puesto> buscarPorId(String id) {
        String sql = "SELECT id, salario, titulo, descripcion FROM puesto WHERE id = ?";
        List<Puesto> result = jdbcTemplate.query(sql, (rs, rowNum) -> new Puesto(
                rs.getString("id"),
                rs.getString("salario"),
                rs.getString("titulo"),
                rs.getString("descripcion")
        ), id);
        return result.stream().findFirst();
    }

    @Override
    public Puesto crearObjeto(Puesto nuevo) {
        String sql = "INSERT INTO puesto (id, salario, titulo, descripcion) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                nuevo.getId(),
                nuevo.getSalario(),
                nuevo.getTitulo(),
                nuevo.getDescripcion()
        );
        return nuevo;
    }

    @Override
    public Puesto actualizarObjeto(Puesto puestoAc) {
        String sql = "UPDATE puesto SET salario = ?, titulo = ?, descripcion = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                puestoAc.getSalario(),
                puestoAc.getTitulo(),
                puestoAc.getDescripcion(),
                puestoAc.getId()
        );
        return puestoAc;
    }

    @Override
    public void eliminarObjeto(String id) {
        String sql = "DELETE FROM puesto WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Puesto> buscarPorCorreo(String correo) {
        return Optional.empty(); //este metodo solo lo usa AdminRepository pero como es interfaz debe estar implementado en todas las clases
    }

    @Override
    public Optional<Puesto> buscarPorNombre(String nombre) {
        String sql = "SELECT id, salario, titulo, descripcion FROM puesto WHERE titulo = ?";
        List<Puesto> result = jdbcTemplate.query(sql, (rs, rowNum) -> new Puesto(
                rs.getString("id"),
                rs.getString("salario"),
                rs.getString("titulo"),
                rs.getString("descripcion")
        ), nombre);
        return result.stream().findFirst();
    }

    public List<Puesto> buscarUltimosCinco() {
        String sql = "SELECT id, salario, titulo, descripcion FROM puesto ORDER BY id DESC LIMIT 5";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Puesto(
                rs.getString("id"),
                rs.getString("salario"),
                rs.getString("titulo"),
                rs.getString("descripcion")
        ));
    }


}
