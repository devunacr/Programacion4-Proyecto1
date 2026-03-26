package cr.ac.una.service;

import cr.ac.una.model.Empresa;
import cr.ac.una.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    @Transactional
    public Empresa registrarEmpresa(Empresa empresa) {
        if (empresaRepository.findByCorreo(empresa.getCorreo()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una empresa registrada con ese correo.");
        }
        empresa.setAprobado(false);
        return empresaRepository.save(empresa);
    }

    @Transactional
    public Empresa aprobarEmpresa(Long id) {
        Empresa empresa = buscarPorId(id);
        empresa.setAprobado(true);
        return empresaRepository.save(empresa);
    }

    public Empresa buscarPorId(Long id) {
        return empresaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada con id: " + id));
    }

    public Empresa buscarPorCorreo(String correo) {
        return empresaRepository.findByCorreo(correo)
                .orElseThrow(() -> new IllegalArgumentException("Empresa no encontrada con correo: " + correo));
    }

    public List<Empresa> listarAprobadas() {
        return empresaRepository.findByAprobado(true);
    }

    public List<Empresa> listarPendientes() {
        return empresaRepository.findByAprobado(false);
    }

    public List<Empresa> listarTodas() {
        return empresaRepository.findAll();
    }
}
