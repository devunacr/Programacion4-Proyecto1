package cr.ac.una.bolsaempleo.repository;

import cr.ac.una.bolsaempleo.model.Habilidad;
import cr.ac.una.bolsaempleo.model.HabilidadOferente;
import cr.ac.una.bolsaempleo.model.Oferente;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class OferenteRepository implements IrepositoryMethods<Oferente> {

    private final JdbcTemplate jdbcTemplate;

    public OferenteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Oferente> buscarATodos() {
        String sql = "SELECT id, nombre, apellidos, nacionalidad, telefono, correopersonal, residencia, password FROM oferente";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Oferente(
                rs.getString("id"),
                rs.getString("nombre"),
                rs.getString("apellidos"),
                rs.getString("nacionalidad"),
                rs.getString("telefono"),
                rs.getString("correopersonal"),
                rs.getString("residencia"),
                rs.getString("password")
        ));
    }

    @Override
    public Optional<Oferente> buscarPorId(String id) {
        String sql = "SELECT id, nombre, apellidos, nacionalidad, telefono, correopersonal, residencia, password FROM oferente WHERE id = ?";
        List<Oferente> result = jdbcTemplate.query(sql, (rs, rowNum) -> new Oferente(
                rs.getString("id"),
                rs.getString("nombre"),
                rs.getString("apellidos"),
                rs.getString("nacionalidad"),
                rs.getString("telefono"),
                rs.getString("correopersonal"),
                rs.getString("residencia"),
                rs.getString("password")
        ), id);
        return result.stream().findFirst();
    }

    @Override
    public Oferente crearObjeto(Oferente oferenteNuevo) {
        String sql = "INSERT INTO oferente (id, nombre, apellidos, nacionalidad, telefono, correopersonal, residencia, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                oferenteNuevo.getId(),
                oferenteNuevo.getNombre(),
                oferenteNuevo.getApellidos(),
                oferenteNuevo.getNacionalidad(),
                oferenteNuevo.getTelefono(),
                oferenteNuevo.getCorreoPersonal(),
                oferenteNuevo.getResidencia(),
                oferenteNuevo.getPassword()
        );
        return oferenteNuevo;
    }

    @Override
    public Oferente actualizarObjeto(Oferente oferenteActualizado) {
        String sql = "UPDATE oferente SET nombre = ?, apellidos = ?, nacionalidad = ?, telefono = ?, correopersonal = ?, residencia = ?, password = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                oferenteActualizado.getNombre(),
                oferenteActualizado.getApellidos(),
                oferenteActualizado.getNacionalidad(),
                oferenteActualizado.getTelefono(),
                oferenteActualizado.getCorreoPersonal(),
                oferenteActualizado.getResidencia(),
                oferenteActualizado.getPassword(),
                oferenteActualizado.getId()
        );
        return oferenteActualizado;
    }

    @Override
    public void eliminarObjeto(String id) {
        String sql = "DELETE FROM oferente WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public Optional<Oferente> buscarPorNombre(String nombre) {
        String sql = "SELECT id, nombre, apellidos, nacionalidad, telefono, correopersonal, residencia, password FROM oferente WHERE nombre = ?";
        List<Oferente> result = jdbcTemplate.query(sql, (rs, rowNum) -> new Oferente(
                rs.getString("id"),
                rs.getString("nombre"),
                rs.getString("apellidos"),
                rs.getString("nacionalidad"),
                rs.getString("telefono"),
                rs.getString("correopersonal"),
                rs.getString("residencia"),
                rs.getString("password")
        ), nombre);
        return result.stream().findFirst();
    }

    @Override
    public Optional<Oferente> buscarPorCorreo(String correo) {
        String sql = "SELECT id, nombre, apellidos, nacionalidad, telefono, correopersonal, residencia, password " +
                "FROM oferente WHERE correopersonal = ?";
        List<Oferente> result = jdbcTemplate.query(sql, (rs, rowNum) -> new Oferente(
                rs.getString("id"),
                rs.getString("nombre"),
                rs.getString("apellidos"),
                rs.getString("nacionalidad"),
                rs.getString("telefono"),
                rs.getString("correopersonal"),
                rs.getString("residencia"),
                rs.getString("password")
        ), correo);
        return result.stream().findFirst();
    }

    public List<Oferente> buscarConFiltros(String habilidadBusqueda, int nivelMinimo) {
        List<Oferente> todos = buscarATodos();

        if ((habilidadBusqueda == null || habilidadBusqueda.isEmpty()) && nivelMinimo <= 1) {
            return todos;
        }

        for (Oferente o : todos) {
            o.setHabilidadesOferente(obtenerHabilidadesDeCandidato(o.getId()));
        }

        List<Oferente> filtrados = new ArrayList<>();
        for (Oferente o : todos) {
            boolean cumple = false;
            for (HabilidadOferente ho : o.getHabilidadesOferente()) {
                boolean matchesHab = (habilidadBusqueda == null || habilidadBusqueda.isEmpty() ||
                        ho.getHabilidad().getNombre().equalsIgnoreCase(habilidadBusqueda));
                boolean matchesNivel = (ho.getNivel() >= nivelMinimo);

                if (matchesHab && matchesNivel) {
                    cumple = true;
                    break;
                }
            }
            if (cumple) filtrados.add(o);
        }
        return filtrados;
    }

    private List<HabilidadOferente> obtenerHabilidadesDeCandidato(String oferenteId) {
        String sql = "SELECT h.nombre, ho.nivel FROM habilidad_oferente ho " +
                "JOIN habilidad h ON ho.habilidad_id = h.id WHERE ho.oferente_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Habilidad h = new Habilidad();
            h.setNombre(rs.getString("nombre"));
            HabilidadOferente ho = new HabilidadOferente();
            ho.setHabilidad(h);
            ho.setNivel(rs.getInt("nivel"));
            return ho;
        }, oferenteId);
    }

    public long contarTodos() {
        String sql = "SELECT COUNT(*) FROM oferente";
        return jdbcTemplate.queryForObject(sql, Long.class);
    }


    public List<Oferente> buscarPendientes() {
        String sql = "SELECT id, nombre, apellidos, correopersonal, telefono FROM oferente WHERE estado = 'PENDIENTE'";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Oferente o = new Oferente();
            o.setId(rs.getString("id"));
            o.setNombre(rs.getString("nombre"));
            o.setApellidos(rs.getString("apellidos"));
            o.setCorreoPersonal(rs.getString("correopersonal"));
            o.setTelefono(rs.getString("telefono"));
            return o;
        });
    }

    public List<Oferente> buscarTodos() {
        return buscarATodos();
    }
}
