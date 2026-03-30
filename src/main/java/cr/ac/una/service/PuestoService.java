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
    public Puesto publicarPuesto(Puesto puesto, Long empresaId, List<cr.ac.una.dto.HabilidadDTO> habilidades) {
        Empresa empresa = empresaService.buscarPorId(empresaId);
        if (!empresa.getAprobado()) {
            throw new IllegalStateException("La empresa no está aprobada para publicar puestos.");
        }
        puesto.setEmpresa(empresa);
        puesto.setActivo(true);
        puesto.setFechaPublicacion(LocalDateTime.now());
        Puesto guardado = puestoRepository.save(puesto);

        if (habilidades != null) {
            for (cr.ac.una.dto.HabilidadDTO h : habilidades) {
                if (h.getCaracteristicaId() == null || h.getNivel() == null || h.getNivel() < 1) continue;
                cr.ac.una.model.Caracteristica c = new cr.ac.una.model.Caracteristica();
                c.setId(h.getCaracteristicaId());
                PuestoHabilidad ph = new PuestoHabilidad();
                ph.setPuesto(guardado);
                ph.setCaracteristica(c);
                ph.setNivel(h.getNivel());
                ph.getId().setPuestoId(guardado.getId());
                ph.getId().setCaracteristicaId(h.getCaracteristicaId());
                puestoHabilidadRepository.save(ph);
            }
        }
        return guardado;
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

    public List<Puesto> buscarPuestosPorCaracteristicas(List<Long> caracteristicasIds) {
        if (caracteristicasIds == null || caracteristicasIds.isEmpty()) return List.of();
        return puestoRepository.findPublicosByCaracteristicas(caracteristicasIds);
    }

    public List<cr.ac.una.dto.CandidatoResultadoDTO> calcularCandidatos(Long puestoId) {
        Puesto puesto = buscarPorId(puestoId);
        List<cr.ac.una.model.PuestoHabilidad> requisitos = puesto.getHabilidades();
        if (requisitos == null || requisitos.isEmpty()) return java.util.List.of();

        int total = requisitos.size();
        // obtener todos los oferentes aprobados que cumplen al menos un requisito
        java.util.Set<cr.ac.una.model.Oferente> candidatosSet = new java.util.LinkedHashSet<>();
        for (cr.ac.una.model.PuestoHabilidad req : requisitos) {
            candidatosSet.addAll(
                oferenteService.buscarCandidatos(req.getCaracteristica().getId(), req.getNivel())
            );
        }

        java.util.List<cr.ac.una.dto.CandidatoResultadoDTO> resultado = new java.util.ArrayList<>();
        for (cr.ac.una.model.Oferente o : candidatosSet) {
            java.util.List<cr.ac.una.dto.HabilidadDTO> cumplidas = new java.util.ArrayList<>();
            for (cr.ac.una.model.PuestoHabilidad req : requisitos) {
                o.getHabilidades().stream()
                    .filter(oh -> oh.getCaracteristica().getId().equals(req.getCaracteristica().getId())
                               && oh.getNivel() >= req.getNivel())
                    .findFirst()
                    .ifPresent(oh -> {
                        cr.ac.una.dto.HabilidadDTO h = new cr.ac.una.dto.HabilidadDTO();
                        h.setCaracteristicaId(req.getCaracteristica().getId());
                        h.setNombreCaracteristica(req.getCaracteristica().getNombre());
                        h.setNivel(oh.getNivel());
                        cumplidas.add(h);
                    });
            }
            double porcentaje = Math.round((cumplidas.size() * 100.0 / total) * 10.0) / 10.0;
            cr.ac.una.dto.CandidatoResultadoDTO dto = new cr.ac.una.dto.CandidatoResultadoDTO();
            dto.setOferenteId(o.getId());
            dto.setNombre(o.getNombre());
            dto.setApellidos(o.getApellidos());
            dto.setCorreopersonal(o.getCorreopersonal());
            dto.setResidencia(o.getResidencia());
            dto.setHabilidades(cumplidas);
            dto.setPorcentajeCoincidencia(porcentaje);
            dto.setTieneCv(o.getCv() != null && o.getCv().length > 0);
            resultado.add(dto);
        }
        resultado.sort(java.util.Comparator.comparingDouble(
            cr.ac.una.dto.CandidatoResultadoDTO::getPorcentajeCoincidencia).reversed());
        return resultado;
    }

    public Puesto buscarPorId(Long id) {
        return puestoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Puesto no encontrado con id: " + id));
    }

    public List<Puesto> listarTodos() {
        return puestoRepository.findAll();
    }
}
