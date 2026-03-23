package cr.ac.una.bolsaempleo.controller;

import cr.ac.una.bolsaempleo.model.Empresa;
import cr.ac.una.bolsaempleo.model.Oferente;
import cr.ac.una.bolsaempleo.service.EmpresaService;
import cr.ac.una.bolsaempleo.service.OferenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PublicController {
    @Autowired
    private EmpresaService empresaService;
    @Autowired
    private OferenteService oferenteService;

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("puestos", new java.util.ArrayList<>());

        return "public/index";
    }

    @GetMapping("/login")
    public String login(@RequestParam(name = "rol", defaultValue = "Oferente") String rol, Model model) {
        model.addAttribute("rolSeleccionado", rol);
        return "public/login";
    }

    @GetMapping("/registro/empresa")
    public String registroEmpresa(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "public/registro_empresa";
    }

    @PostMapping("/registro/empresa")
    public String procesarRegistroEmpresa(@ModelAttribute Empresa empresa) {
        empresaService.guardar(empresa);
        return "redirect:/login?rol=Empresa&exito=true";
    }

    @GetMapping("/registro/oferente")
    public String registroOferente(Model model) {
        model.addAttribute("sessionActive", false);
        model.addAttribute("oferente", new Oferente()); // Objeto vacío para el formulario
        return "public/registro_oferente";
    }

    @PostMapping("/registro/oferente")
    public String procesarRegistroOferente(@ModelAttribute Oferente oferente) {
        oferenteService.guardar(oferente);
        return "redirect:/login?rol=Oferente&exito=true";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam(name="username") String email,
                                @RequestParam(name="password") String password,
                                @RequestParam(name="rol") String rol,
                                Model model) {

        if ("Empresa".equals(rol)) {
            Empresa e = empresaService.buscarPorEmail(email);

            if (e != null && e.getPassword().equals(password)) {
                return "redirect:/empresa/dashboard";
            }
        }

        if ("Oferente".equals(rol)) {
            Oferente o = oferenteService.buscarPorEmail(email);
            if (o != null && o.getPassword().equals(password)) {
                return "redirect:/oferente/dashboard"; // O la ruta que tengas para ellos
            }
        }

        model.addAttribute("mensajeError", "Credenciales incorrectas");
        return "public/login";
    }
}
