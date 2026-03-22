package cr.ac.una.bolsaempleo.service;

import cr.ac.una.bolsaempleo.dtos.AdministradorDTO;
import cr.ac.una.bolsaempleo.model.Administrador;
import cr.ac.una.bolsaempleo.repository.IrepositoryMethods;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AdministradorService {

    private final IrepositoryMethods<Administrador> repository;

    public AdministradorService(IrepositoryMethods<Administrador> repository) {
        this.repository = repository;
    }

    //devuelve todos
    public List<AdministradorDTO> obtenerTodos() {
        return repository.buscarATodos()
                .stream()
                .map(this::cambioDTO)
                .collect(Collectors.toList());
    }

    //por id
    public AdministradorDTO obtenerPorId(String id) {
        Administrador admin = repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Administrador no encontrado"));

        return cambioDTO(admin);
    }

    public AdministradorDTO obtenerPorNombre(String nombre) {
        Administrador admin = repository.buscarPorNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Administrador no encontrado"));
        return cambioDTO(admin);
    }

    //crea
    public AdministradorDTO crear(AdministradorDTO dto) {

        Administrador admin = cambioModel(dto);

        // 🔐 password se maneja aquí (NO en DTO)
        admin.setPassword("admin123"); // ejemplo

        Administrador creado = repository.crearObjeto(admin);

        return cambioDTO(creado);
    }

    //actualiza
    public AdministradorDTO actualizar(String id, AdministradorDTO dto) {

        Administrador admin = repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Administrador no encontrado"));

        admin.setNombreAdmin(dto.getNombreAdmin());

        Administrador actualizado = repository.actualizarObjeto(admin);

        return cambioDTO(actualizado);
    }

    //elimina
    public void eliminar(String id) {
        Optional<Administrador> admin = repository.buscarPorId(id);

        if (admin.isEmpty()) {
            throw new RuntimeException("Administrador no encontrado");
        }

        repository.eliminarObjeto(id);
    }

    //Modelo → DTO
    private AdministradorDTO cambioDTO(Administrador admin) {
        return new AdministradorDTO(
                admin.getIdAdmin(),
                admin.getNombreAdmin()
        );
    }

    //dto → Modelo
    private Administrador cambioModel(AdministradorDTO dto) {
        return new Administrador(
                dto.getIdAdmin(),
                dto.getNombreAdmin(),
                null // 🔐 password no viene del DTO
        );
    }
}