package cr.ac.una.bolsaempleo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmpresaController {

    @GetMapping("/empresa/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("empresa", "Empresa Demo");
        return "empresa/dashboard";
    }

    @GetMapping("/empresa/puestos")
    public String puestos() {
        return "empresa/puestos";
    }

    @GetMapping("/empresa/publicar")
    public String publicar() {
        return "empresa/publicar";
    }
}
