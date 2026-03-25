package cr.ac.una.bolsaempleo.controller;

import cr.ac.una.bolsaempleo.model.*;
import cr.ac.una.bolsaempleo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

@Controller
public class PublicController {

    @Autowired private EmpresaRepository empresaRepository;
    @Autowired private OferenteRepository oferenteRepository;
    @Autowired private PuestoRepository puestoRepository;

    @GetMapping("/")
    public String inicio(Model model, HttpSession session) {
        Object usuario = session.getAttribute("usuarioLogueado");
        model.addAttribute("sessionActive", usuario != null);
        model.addAttribute("usuario", usuario);
        model.addAttribute("puestos", puestoRepository.buscarUltimosCinco());
        return "index";
    }

    @GetMapping("/login")
    public String login(@RequestParam(name = "rol", defaultValue = "Oferente") String rol, Model model) {
        model.addAttribute("rolSeleccionado", rol);
        return "login";
    }

    // --- MÉTODOS GET PARA MOSTRAR LOS FORMULARIOS ---

    @GetMapping("/registro/empresa")
    public String mostrarRegistroEmpresa(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "registro_empresa";
    }

    @GetMapping("/registro/oferente")
    public String mostrarRegistroOferente(Model model) {
        model.addAttribute("oferente", new Oferente());
        return "registro_oferente";
    }

    // --- MÉTODOS POST (PROCESAMIENTO) ---

    @PostMapping("/login")
    public String procesarLogin(@RequestParam("username") String username,
                                @RequestParam("password") String password,
                                @RequestParam("rol") String rol,
                                HttpSession session,
                                Model model) {

        if ("Admin".equals(rol)) {
            if ("admin".equals(username) && "admin".equals(password)) {
                session.setAttribute("rol", "admin");
                session.setAttribute("usuarioLogueado", "Administrador");
                return "redirect:/admin/dashboard";
            }
        }

        if ("Empresa".equals(rol)) {
            Optional<Empresa> empresaOpt = empresaRepository.buscarPorCorreo(username);
            if (empresaOpt.isPresent()) {
                Empresa e = empresaOpt.get();
                if (e.getPassword() != null && e.getPassword().equals(password)) {
                    session.setAttribute("usuarioLogueado", e);
                    session.setAttribute("rol", "empresa");
                    return "redirect:/empresa/dashboard";
                }
            }
        }

        if ("Oferente".equals(rol)) {
            Optional<Oferente> oferenteOpt = oferenteRepository.buscarPorCorreo(username);
            if (oferenteOpt.isPresent()) {
                Oferente o = oferenteOpt.get();
                if (o.getPassword() != null && o.getPassword().equals(password)) {
                    session.setAttribute("usuarioLogueado", o);
                    session.setAttribute("rol", "oferente");
                    return "redirect:/oferente/dashboard";
                }
            }
        }

        model.addAttribute("mensajeError", "Credenciales incorrectas para " + rol);
        model.addAttribute("rolSeleccionado", rol);
        return "login";
    }

    @PostMapping("/registro/empresa")
    public String procesarRegistroEmpresa(@ModelAttribute Empresa empresa) {
        empresaRepository.crearObjeto(empresa);
        return "redirect:/login?rol=Empresa&exito=true";
    }

    @PostMapping("/registro/oferente")
    public String procesarRegistroOferente(@ModelAttribute Oferente oferente) {
        oferenteRepository.crearObjeto(oferente);
        return "redirect:/login?rol=Oferente&exito=true";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/puestos")
    public String listarPuestos(
            @RequestParam(value = "texto", required = false) String texto,
            @RequestParam(value = "habilidad", required = false) String habilidad,
            @RequestParam(value = "salarioMin", required = false, defaultValue = "0") Double salarioMin,
            Model model, HttpSession session) {

        model.addAttribute("sessionActive", session.getAttribute("usuarioLogueado") != null);

        List<Puesto> resultados = puestoRepository.buscarConFiltros(texto, habilidad, salarioMin);

        model.addAttribute("puestos", resultados);
        return "empresa/puestos";
    }
}