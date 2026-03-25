package cr.ac.una.bolsaempleo.repository;

import cr.ac.una.bolsaempleo.model.Empresa;
import cr.ac.una.bolsaempleo.model.Habilidad;
import cr.ac.una.bolsaempleo.model.Puesto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
        String sql = "SELECT p.id, p.salario, p.titulo, p.descripcion, e.nombre AS nombre_empresa " +
                "FROM puesto p " +
                "LEFT JOIN empresa e ON p.empresa_id = e.id " +
                "ORDER BY p.id DESC LIMIT 5";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Puesto p = new Puesto(
                    rs.getString("id"),
                    rs.getString("salario"),
                    rs.getString("titulo"),
                    rs.getString("descripcion")
            );

            Empresa e = new Empresa();
            e.setNombre(rs.getString("nombre_empresa"));
            p.setEmpresa(e);

            return p;
        });
    }

    public List<Puesto> buscarConFiltros(String texto, String habilidad, Double salarioMin) {
        StringBuilder sql = new StringBuilder(
                "SELECT DISTINCT p.id, p.salario, p.titulo, p.descripcion, p.fecha, e.nombre AS nombre_empresa " +
                        "FROM puesto p " +
                        "LEFT JOIN empresa e ON p.empresa_id = e.id "
        );

        if (habilidad != null && !habilidad.isEmpty()) {
            sql.append(" JOIN puesto_habilidad ph ON p.id = ph.puesto_id ");
            sql.append(" JOIN habilidad h_filtro ON ph.habilidad_id = h_filtro.id ");
        }

        sql.append(" WHERE CAST(p.salario AS NUMERIC) >= ? ");

        List<Object> params = new ArrayList<>();
        params.add(salarioMin);

        if (texto != null && !texto.isEmpty()) {
            sql.append(" AND (p.titulo ILIKE ? OR p.descripcion ILIKE ?)");
            params.add("%" + texto + "%");
            params.add("%" + texto + "%");
        }

        if (habilidad != null && !habilidad.isEmpty()) {
            sql.append(" AND h_filtro.nombre = ? ");
            params.add(habilidad);
        }

        List<Puesto> puestos = jdbcTemplate.query(sql.toString(), (rs, rowNum) -> {
            Puesto p = new Puesto(
                    rs.getString("id"),
                    rs.getString("salario"),
                    rs.getString("titulo"),
                    rs.getString("descripcion")
            );
            p.setFecha(rs.getString("fecha") != null ? rs.getString("fecha") : "Reciente");

            Empresa e = new Empresa();
            e.setNombre(rs.getString("nombre_empresa"));
            p.setEmpresa(e);
            return p;
        }, params.toArray());

        for (Puesto p : puestos) {
            String sqlH = "SELECT h.id, h.nombre FROM habilidad h " +
                    "JOIN puesto_habilidad ph ON h.id = ph.habilidad_id " +
                    "WHERE ph.puesto_id = ?";
            List<Habilidad> listaH = jdbcTemplate.query(sqlH, (rs, rowNum) ->
                    new Habilidad(rs.getString("id"), rs.getString("nombre")), p.getId());
            p.setHabilidades(listaH);
        }

        return puestos;
    }

    public long contarTodos() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM puesto", Long.class);
    }

    public long contarActivos() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM puesto WHERE estado = 'ACTIVO'", Long.class);
    }
}
