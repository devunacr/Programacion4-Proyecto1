package cr.ac.una.service;

import cr.ac.una.model.Oferente;
import cr.ac.una.model.OferenteHabilidad;
import cr.ac.una.repository.OferenteHabilidadRepository;
import cr.ac.una.repository.OferenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OferenteService {

    private final OferenteRepository oferenteRepository;
    private final OferenteHabilidadRepository oferenteHabilidadRepository;

    @Transactional
    public Oferente registrarOferente(Oferente oferente) {
        if (oferenteRepository.findByCorreopersonal(oferente.getCorreopersonal()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un oferente registrado con ese correo.");
        }
        oferente.setAprobado(false);
        return oferenteRepository.save(oferente);
    }

    @Transactional
    public Oferente aprobarOferente(String id) {
        Oferente oferente = buscarPorId(id);
        oferente.setAprobado(true);
        return oferenteRepository.save(oferente);
    }

    @Transactional
    public OferenteHabilidad agregarHabilidad(OferenteHabilidad habilidad) {
        if (habilidad.getNivel() < 1 || habilidad.getNivel() > 5) {
            throw new IllegalArgumentException("El nivel debe estar entre 1 y 5.");
        }
        return oferenteHabilidadRepository.save(habilidad);
    }

    @Transactional
    public Oferente actualizarOferente(Oferente oferente) {
        return oferenteRepository.save(oferente);
    }

    // candidatos aptos para un puesto segun una característica y nivel minimo requerido
    public List<Oferente> buscarCandidatos(Long caracteristicaId, Integer nivelMinimo) {
        return oferenteRepository.findByHabilidadYNivelMinimo(caracteristicaId, nivelMinimo);
    }

    public Oferente buscarPorId(String id) {
        return oferenteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Oferente no encontrado con id: " + id));
    }

    public Oferente buscarPorCorreo(String correo) {
        return oferenteRepository.findByCorreopersonal(correo)
                .orElseThrow(() -> new IllegalArgumentException("Oferente no encontrado con correo: " + correo));
    }

    public List<Oferente> listarAprobados() {
        return oferenteRepository.findAll().stream()
                .filter(Oferente::getAprobado)
                .toList();
    }

    public List<Oferente> listarPendientes() {
        return oferenteRepository.findAll().stream()
                .filter(o -> !o.getAprobado())
                .toList();
    }

    public List<Oferente> listarTodos() {
        return oferenteRepository.findAll();
    }
}
