package cr.ac.una.controller;

import cr.ac.una.dto.ReporteMensualDTO;
import cr.ac.una.model.Administrador;
import cr.ac.una.model.Caracteristica;
import cr.ac.una.repository.CaracteristicaRepository;
import cr.ac.una.service.AdministradorService;
import cr.ac.una.service.EmpresaService;
import cr.ac.una.service.OferenteService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.YearMonth;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdministradorService administradorService;
    private final EmpresaService empresaService;
    private final OferenteService oferenteService;
    private final CaracteristicaRepository caracteristicaRepository;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("rol", "Administrador");
        model.addAttribute("accion", "/admin/login");
        model.addAttribute("labelUsuario", "Nombre de usuario");
        model.addAttribute("linkRegistro", null);
        model.addAttribute("textoRegistro", null);
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String usuario,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes ra) {
        try {
            Administrador admin = administradorService.login(usuario, password);
            session.setAttribute("adminId", admin.getId());
            return "redirect:/admin/dashboard";
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (session.getAttribute("adminId") == null) return "redirect:/admin/login";
        List<cr.ac.una.model.Empresa> pendientes = empresaService.listarPendientes();
        model.addAttribute("empresasPendientes",
                pendientes != null ? pendientes : Collections.emptyList());
        return "admin/dashboard";
    }


    @PostMapping("/empresa/aprobar/{id}")
    public String aprobarEmpresa(@PathVariable Long id,
                                 HttpSession session,
                                 RedirectAttributes ra) {
        if (session.getAttribute("adminId") == null) return "redirect:/admin/login";
        try {
            administradorService.aprobarEmpresa(id);
            ra.addFlashAttribute("success", "Empresa aprobada correctamente.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }


    @GetMapping("/oferentes")
    public String oferentesPendientes(HttpSession session, Model model) {
        if (session.getAttribute("adminId") == null) return "redirect:/admin/login";
        List<cr.ac.una.model.Oferente> pendientes = oferenteService.listarPendientes();
        model.addAttribute("oferentesPendientes",
                pendientes != null ? pendientes : Collections.emptyList());
        return "admin/oferentes";
    }


    @PostMapping("/oferente/aprobar/{id}")
    public String aprobarOferente(@PathVariable String id,
                                  HttpSession session,
                                  RedirectAttributes ra) {
        if (session.getAttribute("adminId") == null) return "redirect:/admin/login";
        try {
            administradorService.aprobarOferente(id);
            ra.addFlashAttribute("success", "Oferente aprobado correctamente.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/oferentes";
    }


    @GetMapping("/caracteristicas")
    public String caracteristicas(HttpSession session, Model model) {
        if (session.getAttribute("adminId") == null) return "redirect:/admin/login";
        List<Caracteristica> raices = caracteristicaRepository.findByPadreIsNull();
        model.addAttribute("raices",
                raices != null ? raices : Collections.emptyList());
        return "admin/caracteristicas";
    }

    @PostMapping("/caracteristicas/nueva")
    public String nuevaCaracteristica(@RequestParam String nombre,
                                      @RequestParam(required = false) Long padreId,
                                      HttpSession session,
                                      RedirectAttributes ra) {
        if (session.getAttribute("adminId") == null) return "redirect:/admin/login";
        try {
            Caracteristica c = new Caracteristica();
            c.setNombre(nombre);
            if (padreId != null) {
                c.setPadre(caracteristicaRepository.findById(padreId)
                        .orElseThrow(() -> new IllegalArgumentException("Categoría padre no encontrada.")));
            }
            caracteristicaRepository.save(c);
            ra.addFlashAttribute("success", "Característica agregada.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/caracteristicas";
    }


    @GetMapping("/reporte")
    public String reporteForm(HttpSession session,
                              @RequestParam(required = false) String mes,
                              Model model) {
        if (session.getAttribute("adminId") == null) return "redirect:/admin/login";

        if (mes != null && !mes.isBlank()) {
            ReporteMensualDTO reporte = administradorService.generarReporteMensual(YearMonth.parse(mes));
            model.addAttribute("reporte", reporte);
            model.addAttribute("mesSeleccionado", mes);
        }
        return "admin/reporte";
    }
}
