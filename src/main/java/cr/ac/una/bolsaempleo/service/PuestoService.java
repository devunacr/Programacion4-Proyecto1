package cr.ac.una.bolsaempleo.service;

import cr.ac.una.bolsaempleo.dtos.PuestoDTO;
import cr.ac.una.bolsaempleo.model.Puesto;
import cr.ac.una.bolsaempleo.repository.IrepositoryMethods;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PuestoService {

    private final IrepositoryMethods<Puesto> repository;

    public PuestoService(IrepositoryMethods<Puesto> repository) {
        this.repository = repository;
    }

    //devuelve todos los puestos
    public List<PuestoDTO> obtenerTodos() {

        return repository.buscarATodos()
                .stream()
                .map(this::cambioDTO)
                .collect(Collectors.toList());
    }

    //lo busca por ID
    public PuestoDTO obtenerPorId(String id) {

        Puesto puesto = repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Puesto no encontrado con id: " + id));

        return cambioDTO(puesto);
    }
    // bucsa por nombre
    public PuestoDTO obtenerPorNombre(String nombre) {

        Puesto puesto = repository.buscarPorNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Puesto no encontrado con nombre: " + nombre));

        return cambioDTO(puesto);
    }


    //Crea
    public PuestoDTO crear(PuestoDTO dto) {

        Puesto puesto = cambioModel(dto);

        Puesto creado = repository.crearObjeto(puesto);

        return cambioDTO(creado);
    }

    // Actualiza
    public PuestoDTO actualizar(String id, PuestoDTO dto) {

        Puesto puestoExistente = repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Puesto no encontrado con id: " + id));

        puestoExistente.setTitulo(dto.getTitulo());
        puestoExistente.setSalario(dto.getSalario());
        puestoExistente.setDescripcion(dto.getDescripcion());

        Puesto actualizado = repository.actualizarObjeto(puestoExistente);

        return cambioDTO(actualizado);
    }

    // los elimina
    public void eliminar(String id) {

        Optional<Puesto> puesto = repository.buscarPorId(id);

        if (puesto.isEmpty()) {
            throw new RuntimeException("Puesto no encontrado con id: " + id);
        }

        repository.eliminarObjeto(id);
    }

    //conviertre Modelo → DTO
    private PuestoDTO cambioDTO(Puesto puesto) {
        return new PuestoDTO(
                puesto.getId(),
                puesto.getSalario(),
                puesto.getTitulo(),
                puesto.getDescripcion()
        );
    }

    //Convierte DTO → Modelo
    private Puesto cambioModel(PuestoDTO dto) {
        return new Puesto(
                dto.getId(),
                dto.getSalario(),
                dto.getTitulo(),
                dto.getDescripcion()
        );
    }
}