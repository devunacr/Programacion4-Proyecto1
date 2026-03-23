package cr.ac.una.bolsaempleo.controller;

import cr.ac.una.bolsaempleo.model.Empresa;
import cr.ac.una.bolsaempleo.model.Oferente;
import cr.ac.una.bolsaempleo.repository.EmpresaRepository;
import cr.ac.una.bolsaempleo.repository.OferenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private OferenteRepository oferenteRepository;

    @GetMapping("/admin/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    // ver empresas
    @GetMapping("/admin/empresas")
    public String empresas(Model model) {
        List<Empresa> listaEmpresas = empresaRepository.buscarATodos();
        model.addAttribute("empresas", listaEmpresas);
        return "admin/empresas"; // Thymeleaf renderiza la vista con la lista
    }

    // ver oferentes
    @GetMapping("/admin/oferentes")
    public String oferentes(Model model) {
        List<Oferente> listaOferentes = oferenteRepository.buscarATodos();
        model.addAttribute("oferentes", listaOferentes);
        return "admin/oferentes";
    }


    @GetMapping("/admin/caracteristicas")
    public String caracteristicas() {
        return "admin/caracteristicas";
    }
}


