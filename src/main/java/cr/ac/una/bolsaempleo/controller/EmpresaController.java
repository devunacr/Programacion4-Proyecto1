package cr.ac.una.bolsaempleo.controller;

import cr.ac.una.bolsaempleo.model.Empresa;
import cr.ac.una.bolsaempleo.model.Puesto;
import cr.ac.una.bolsaempleo.repository.EmpresaRepository;
import cr.ac.una.bolsaempleo.repository.PuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class EmpresaController {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private PuestoRepository puestoRepository;

    // ver empresas en vista Thymeleaf
    @GetMapping("/empresa/dashboard")
    public String listarEmpresas(Model model) {
        List<Empresa> listaEmpresas = empresaRepository.buscarATodos();
        model.addAttribute("empresas", listaEmpresas);
        return "empresa/dashboard"; // Thymeleaf renderiza la vista
    }

    // ver puestos en vista Thymeleaf
    @GetMapping("/empresa/puestos")
    public String puestos(Model model) {
        List<Puesto> listaPuestos = puestoRepository.buscarATodos();
        model.addAttribute("puestos", listaPuestos);
        return "empresa/puestos"; // Thymeleaf renderiza la vista
    }

    // vista para publicar (formulario)
    @GetMapping("/empresa/publicar")
    public String publicar() {
        return "empresa/publicar";
    }
}
