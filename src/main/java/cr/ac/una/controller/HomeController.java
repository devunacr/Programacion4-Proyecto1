package cr.ac.una.controller;

import cr.ac.una.repository.CaracteristicaRepository;
import cr.ac.una.service.PuestoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PuestoService puestoService;
    private final CaracteristicaRepository caracteristicaRepository;

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

    @GetMapping("/puestos/buscar-por-caracteristicas")
    public String buscarPorCaracteristicas(
            @RequestParam(required = false) List<Long> caracteristicas,
            Model model) {
        model.addAttribute("todasCaracteristicas", caracteristicaRepository.findAll());
        model.addAttribute("seleccionadas", caracteristicas != null ? caracteristicas : List.of());
        model.addAttribute("resultados", puestoService.buscarPuestosPorCaracteristicas(caracteristicas));
        return "puestos-buscar";
    }
}
