package cr.ac.una.bolsaempleo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OferenteController {

    @GetMapping("/oferente/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("oferente", "Oferente Demo");
        return "oferente/dashboard";
    }

    @GetMapping("/oferente/habilidades")
    public String habilidades() {
        return "oferente/habilidades";
    }

    @GetMapping("/oferente/cv")
    public String cv() {
        return "oferente/cv";
    }
}
