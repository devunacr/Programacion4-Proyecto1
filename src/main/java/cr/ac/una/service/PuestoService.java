package cr.ac.una.service;

import cr.ac.una.model.Empresa;
import cr.ac.una.model.Puesto;
import cr.ac.una.model.PuestoHabilidad;
import cr.ac.una.repository.PuestoHabilidadRepository;
import cr.ac.una.repository.PuestoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PuestoService {

    private final PuestoRepository puestoRepository;
    private final PuestoHabilidadRepository puestoHabilidadRepository;
    private final EmpresaService empresaService;
    private final OferenteService oferenteService;

    @Transactional
    public Puesto publicarPuesto(Puesto puesto, Long empresaId) {
        Empresa empresa = empresaService.buscarPorId(empresaId);
        if (!empresa.getAprobado()) {
            throw new IllegalStateException("La empresa no está aprobada para publicar puestos.");
        }
        puesto.setEmpresa(empresa);
        puesto.setActivo(true);
        puesto.setFechaPublicacion(LocalDateTime.now());
        return puestoRepository.save(puesto);
    }

    @Transactional
    public Puesto desactivarPuesto(Long id) {
        Puesto puesto = buscarPorId(id);
        puesto.setActivo(false);
        return puestoRepository.save(puesto);
    }

    @Transactional
    public PuestoHabilidad agregarHabilidad(PuestoHabilidad habilidad) {
        if (habilidad.getNivel() < 1 || habilidad.getNivel() > 5) {
            throw new IllegalArgumentException("El nivel debe estar entre 1 y 5.");
        }
        return puestoHabilidadRepository.save(habilidad);
    }

    // candidatos aptos para este puesto segun una característica y nivel minimo
    public List<?> buscarCandidatos(Long caracteristicaId, Integer nivelMinimo) {
        return oferenteService.buscarCandidatos(caracteristicaId, nivelMinimo);
    }

    public List<Puesto> listarUltimosPublicos() {
        return puestoRepository.findTop5ByTipoPublicacionAndActivoTrueOrderByFechaPublicacionDesc("publico");
    }

    public List<Puesto> buscarPorCaracteristica(Long caracteristicaId) {
        return puestoRepository.findByCaracteristica(caracteristicaId);
    }

    public Puesto buscarPorId(Long id) {
        return puestoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Puesto no encontrado con id: " + id));
    }

    public List<Puesto> listarTodos() {
        return puestoRepository.findAll();
    }
}
