package cr.ac.una.bolsaempleo.controller;

import cr.ac.una.bolsaempleo.model.Empresa;
import cr.ac.una.bolsaempleo.model.Oferente;
import cr.ac.una.bolsaempleo.model.Puesto;
import cr.ac.una.bolsaempleo.repository.EmpresaRepository;
import cr.ac.una.bolsaempleo.repository.OferenteRepository;
import cr.ac.una.bolsaempleo.repository.PuestoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private OferenteRepository oferenteRepository;

    @Autowired
    private PuestoRepository puestoRepository;

    @GetMapping("/admin/dashboard")
    public String adminDashboard(@RequestParam(value = "tab", required = false, defaultValue = "oferentes") String tab,
                                 Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null || !"admin".equals(session.getAttribute("rol"))) {
            return "redirect:/login";
        }

        model.addAttribute("totalPuestos", puestoRepository.contarTodos());
        model.addAttribute("totalOferentes", oferenteRepository.contarTodos());
        model.addAttribute("totalEmpresas", empresaRepository.contarTodos());
        model.addAttribute("puestosActivos", puestoRepository.contarActivos());

        model.addAttribute("activeTab", tab);

        if ("oferentes".equals(tab)) {
            model.addAttribute("oferentes", oferenteRepository.buscarTodos());
            model.addAttribute("oferentesPendientes", oferenteRepository.buscarPendientes());
        } else if ("empresas".equals(tab)) {
            model.addAttribute("empresas", empresaRepository.buscarTodos());
        }
        // ... agregar las otras pestañas

        return "admin/dashboard";
    }

}


