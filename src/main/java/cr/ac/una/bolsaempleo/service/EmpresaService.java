package cr.ac.una.bolsaempleo.service;

import cr.ac.una.bolsaempleo.dtos.EmpresaDTO;
import cr.ac.una.bolsaempleo.model.Empresa;
import cr.ac.una.bolsaempleo.repository.IrepositoryMethods;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmpresaService {

    private final IrepositoryMethods<Empresa> repository;

    public EmpresaService(IrepositoryMethods<Empresa> repository) {
        this.repository = repository;
    }

    public List<EmpresaDTO> obtenerTodos() {
        return repository.buscarATodos()
                .stream()
                .map(this::cambioDTO)
                .collect(Collectors.toList());
    }

    public EmpresaDTO obtenerPorId(String id) {
        Empresa empresa = repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        return cambioDTO(empresa);
    }

    public EmpresaDTO obtenerPorNombre(String nombre) {
        Empresa empresa = repository.buscarPorNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
        return cambioDTO(empresa);
    }


    public EmpresaDTO crear(EmpresaDTO dto) {
        Empresa empresa = cambioModel(dto);

        //password se maneja aquí, debo arreglar esto ********************************************
        empresa.setPassword("1234"); // ejemplo

        Empresa creada = repository.crearObjeto(empresa);

        return cambioDTO(creada);
    }

    public EmpresaDTO actualizar(String id, EmpresaDTO dto) {

        Empresa empresa = repository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        empresa.setNombre(dto.getNombre());
        empresa.setDireccion(dto.getDireccion());
        empresa.setCorreo(dto.getCorreo());
        empresa.setTelefono(dto.getTelefono());
        empresa.setDescripcion(dto.getDescripcion());

        Empresa actualizada = repository.actualizarObjeto(empresa);

        return cambioDTO(actualizada);
    }


    public void eliminar(String id) {
        Optional<Empresa> empresa = repository.buscarPorId(id);

        if (empresa.isEmpty()) {
            throw new RuntimeException("Empresa no encontrada");
        }

        repository.eliminarObjeto(id);
    }

    //objeto → DTO
    private EmpresaDTO cambioDTO(Empresa empresa) {
        return new EmpresaDTO(
                empresa.getId(),
                empresa.getNombre(),
                empresa.getDireccion(),
                empresa.getCorreo(),
                empresa.getTelefono(),
                empresa.getDescripcion()
        );
    }

    //DTO → Modelo
    private Empresa cambioModel(EmpresaDTO dto) {
        return new Empresa(
                dto.getId(),
                dto.getNombre(),
                dto.getDireccion(),
                dto.getCorreo(),
                dto.getTelefono(),
                null, // sin password el profe dijo que no debia ir en la DTO porque es info sensible
                dto.getDescripcion()
        );
    }
}