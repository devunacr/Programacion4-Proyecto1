package cr.ac.una.bolsaempleo.controller;

import cr.ac.una.bolsaempleo.model.Oferente;
import cr.ac.una.bolsaempleo.repository.OferenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class OferenteController {

    @Autowired
    private OferenteRepository oferenteRepository;

    // ver oferentes en vista Thymeleaf
    @GetMapping("/oferente/dashboard")
    public String listarOferentes(Model model) {
        List<Oferente> listaOferentes = oferenteRepository.buscarATodos();
        model.addAttribute("oferentes", listaOferentes);
        return "oferente/dashboard";
    }


    // ver sus respectivas habilidades
    @GetMapping("/oferente/habilidades")
    public String habilidades() {
        return "oferente/habilidades";
    }

    // ver su respectivo cv
    @GetMapping("/oferente/cv")
    public String cv() {
        return "oferente/cv";
    }
}
