package cr.ac.una.bolsaempleo.controller;

import cr.ac.una.bolsaempleo.model.Puesto;
import cr.ac.una.bolsaempleo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller @RequestMapping("/empresa")
public class EmpresaController {
    @Autowired private PuestoService puestoService;
    @Autowired private OferenteService oferenteService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("puestos", puestoService.listarTodos());
        model.addAttribute("sessionActive", true);
        model.addAttribute("nombreEmpresa", "Tech Corp");
        return "empresa/dashboard";
    }

    @PostMapping("/cambiar-estado")
    public String cambiarEstado(@RequestParam Long id) {
        puestoService.cambiarEstado(id);
        return "redirect:/empresa/dashboard";
    }

    @GetMapping("/crear_puesto")
    public String formularioCrearPuesto(Model model) {
        model.addAttribute("puesto", new Puesto());
        model.addAttribute("sessionActive", true);
        return "empresa/crear_puesto";
    }

    @PostMapping("/puestos")
    public String guardarPuesto(@ModelAttribute("puesto") Puesto puesto) {
        puesto.setFecha(new java.util.Date().toString());
        puesto.setPublico(true);
        puestoService.guardar(puesto);
        return "redirect:/empresa/dashboard";
    }

    @GetMapping("/buscar_candidatos")
    public String buscarCandidatos(Model model) {
        model.addAttribute("candidatos", oferenteService.listar());
        model.addAttribute("sessionActive", true);
        return "empresa/buscar_candidatos";
    }
}