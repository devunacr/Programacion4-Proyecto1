package cr.ac.una.bolsaempleo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/admin/empresas")
    public String empresas() {
        return "admin/empresas";
    }

    @GetMapping("/admin/oferentes")
    public String oferentes() {
        return "admin/oferentes";
    }

    @GetMapping("/admin/caracteristicas")
    public String caracteristicas() {
        return "admin/caracteristicas";
    }
}
