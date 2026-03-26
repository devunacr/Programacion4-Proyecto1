package cr.ac.una.controller;

import cr.ac.una.service.PuestoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PuestoService puestoService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("puestos", puestoService.listarUltimosPublicos());
        return "index";
    }

    @GetMapping("/puesto/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        model.addAttribute("puesto", puestoService.buscarPorId(id));
        return "puesto-detalle";
    }
}
