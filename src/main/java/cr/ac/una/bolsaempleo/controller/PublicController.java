package cr.ac.una.bolsaempleo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicController {

    @GetMapping("/")
    public String index(Model model) {
        // Aquí va la logica para traer los ultimos 5 puestos
        model.addAttribute("mensaje", "Bienvenido a la Bolsa de Empleo");
        return "Index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
