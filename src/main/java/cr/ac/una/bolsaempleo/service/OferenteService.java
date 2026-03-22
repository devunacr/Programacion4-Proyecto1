package cr.ac.una.bolsaempleo.service;

import cr.ac.una.bolsaempleo.dtos.OferenteDTO;
import cr.ac.una.bolsaempleo.model.Oferente;
import cr.ac.una.bolsaempleo.repository.IrepositoryMethods;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OferenteService {

    private final IrepositoryMethods<Oferente> repository;

    public OferenteService(IrepositoryMethods<Oferente> repository) {
        this.repository = repository;
    }

    //devuelve todos
    public List<OferenteDTO> obtenerTodos() {

        return repository.buscarATodos()
                .stream()
                .map(this::cambioDTO)
                .collect(Collectors.toList());
    }

    //por ID
    public OferenteDTO obtenerPorId(String id) {

        Oferente oferente = repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Oferente no encontrado con id: " + id));

        return cambioDTO(oferente);
    }

    // por nombre
    public OferenteDTO obtenerPorNombre(String nombre) {

        Oferente oferente = repository.buscarPorNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Oferente no encontrado con nombre: " + nombre));

        return cambioDTO(oferente);
    }

    //crea
    public OferenteDTO crear(OferenteDTO dto) {

        Oferente oferente = cambioModel(dto);

        // 🔐 password se maneja aquí (NO viene del DTO)
        oferente.setPassword("1234"); // puedes cambiar esto después

        Oferente creado = repository.crearObjeto(oferente);

        return cambioDTO(creado);
    }

    //actualiza
    public OferenteDTO actualizar(String id, OferenteDTO dto) {

        Oferente oferenteExistente = repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Oferente no encontrado con id: " + id));

        oferenteExistente.setNombre(dto.getNombre());
        oferenteExistente.setApellidos(dto.getApellidos());
        oferenteExistente.setNacionalidad(dto.getNacionalidad());
        oferenteExistente.setTelefono(dto.getTelefono());
        oferenteExistente.setCorreoPersonal(dto.getCorreoPersonal());
        oferenteExistente.setResidencia(dto.getResidencia());

        Oferente actualizado = repository.actualizarObjeto(oferenteExistente);

        return cambioDTO(actualizado);
    }

    // los elimina
    public void eliminar(String id) {

        Optional<Oferente> oferente = repository.buscarPorId(id);

        if (oferente.isEmpty()) {
            throw new RuntimeException("Oferente no encontrado con id: " + id);
        }

        repository.eliminarObjeto(id);
    }

    //modelo → DTO
    private OferenteDTO cambioDTO(Oferente o) {

        return new OferenteDTO(
                o.getId(),
                o.getNombre(),
                o.getApellidos(),
                o.getNacionalidad(),
                o.getTelefono(),
                o.getCorreoPersonal(),
                o.getResidencia()
        );
    }

    //dto → Modelo
    private Oferente cambioModel(OferenteDTO dto) {

        return new Oferente(
                dto.getId(),
                dto.getNombre(),
                dto.getApellidos(),
                dto.getNacionalidad(),
                dto.getTelefono(),
                dto.getCorreoPersonal(),
                dto.getResidencia(),
                null //  password NO viene del DTO, lo voy a manejr en otro DTO por si solo
        );
    }
}