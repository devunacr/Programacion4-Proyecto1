package cr.ac.una.service;

import cr.ac.una.dto.ReporteMensualDTO;
import cr.ac.una.model.Administrador;
import cr.ac.una.model.Empresa;
import cr.ac.una.model.Oferente;
import cr.ac.una.model.Puesto;
import cr.ac.una.repository.AdministradorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdministradorService {

    private final AdministradorRepository administradorRepository;
    private final EmpresaService empresaService;
    private final OferenteService oferenteService;
    private final PuestoService puestoService;

    public Administrador login(String nombre, String password) {
        return administradorRepository.findByNombre(nombre)
                .filter(a -> a.getPassword().equals(password))
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas."));
    }

    public Administrador buscarPorId(Long id) {
        return administradorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Administrador no encontrado con id: " + id));
    }

    // aprobaciones delegadas al servicio correspondiente
    public Empresa aprobarEmpresa(Long empresaId) {
        return empresaService.aprobarEmpresa(empresaId);
    }

    public Oferente aprobarOferente(String oferenteId) {
        return oferenteService.aprobarOferente(oferenteId);
    }

    public ReporteMensualDTO generarReporteMensual(YearMonth mes) {
        LocalDateTime inicio = mes.atDay(1).atStartOfDay();
        LocalDateTime fin = mes.atEndOfMonth().atTime(23, 59, 59);

        List<Puesto> todosPuestos = puestoService.listarTodos();
        List<Puesto> puestosDelMes = todosPuestos.stream()
                .filter(p -> !p.getFechaPublicacion().isBefore(inicio) && !p.getFechaPublicacion().isAfter(fin))
                .toList();

        List<Empresa> empresasPendientes = empresaService.listarPendientes();
        List<Oferente> oferentesPendientes = oferenteService.listarPendientes();

        return new ReporteMensualDTO(
                mes.toString(),
                puestosDelMes.size(),
                todosPuestos.stream().filter(Puesto::getActivo).count(),
                empresaService.listarTodas().size(),
                empresasPendientes.size(),
                oferenteService.listarTodos().size(),
                oferentesPendientes.size()
        );
    }
}
