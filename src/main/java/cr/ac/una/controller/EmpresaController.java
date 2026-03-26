package cr.ac.una.controller;

import cr.ac.una.dto.EmpresaRegistroDTO;
import cr.ac.una.dto.PuestoPublicacionDTO;
import cr.ac.una.model.Empresa;
import cr.ac.una.model.Oferente;
import cr.ac.una.model.Puesto;
import cr.ac.una.service.EmpresaService;
import cr.ac.una.service.OferenteService;
import cr.ac.una.service.PuestoService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/empresa")
@RequiredArgsConstructor
public class EmpresaController {

    private final EmpresaService empresaService;
    private final PuestoService puestoService;
    private final OferenteService oferenteService;


    @GetMapping("/registro")
    public String registroForm(Model model) {
        model.addAttribute("empresaDTO", new EmpresaRegistroDTO());
        return "empresa/registro";
    }

    @PostMapping("/registro")
    public String registrar(@Valid @ModelAttribute("empresaDTO") EmpresaRegistroDTO dto,
                            BindingResult result,
                            RedirectAttributes ra) {
        if (result.hasErrors()) return "empresa/registro";

        try {
            Empresa empresa = new Empresa();
            empresa.setNombre(dto.getNombre());
            empresa.setCorreo(dto.getCorreo());
            empresa.setTelefono(dto.getTelefono());
            empresa.setDescripcion(dto.getDescripcion());
            empresa.setPassword(dto.getPassword());
            empresa.setDireccion("");
            empresaService.registrarEmpresa(empresa);
            ra.addFlashAttribute("success", "Registro exitoso. Espera la aprobación del administrador.");
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/empresa/login";
    }


    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("rol", "Empresa");
        model.addAttribute("accion", "/empresa/login");
        model.addAttribute("labelUsuario", "Correo");
        model.addAttribute("linkRegistro", "/empresa/registro");
        model.addAttribute("textoRegistro", "¿No tienes cuenta? Registrarse");
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String usuario,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes ra) {
        try {
            Empresa empresa = empresaService.buscarPorCorreo(usuario);
            if (!empresa.getPassword().equals(password)) throw new IllegalArgumentException("Contraseña incorrecta.");
            if (!empresa.getAprobado()) throw new IllegalStateException("Tu cuenta aún no ha sido aprobada.");
            session.setAttribute("empresaId", empresa.getId());
            return "redirect:/empresa/dashboard";
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/empresa/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }


    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Long empresaId = (Long) session.getAttribute("empresaId");
        if (empresaId == null) return "redirect:/empresa/login";

        Empresa empresa = empresaService.buscarPorId(empresaId);
        List<Puesto> puestos = empresa.getPuestos() != null
                ? empresa.getPuestos()
                : Collections.emptyList();
        model.addAttribute("empresa", empresa);
        model.addAttribute("puestos", puestos);
        return "empresa/dashboard";
    }


    @GetMapping("/puesto/nuevo")
    public String nuevoPuestoForm(HttpSession session, Model model) {
        if (session.getAttribute("empresaId") == null) return "redirect:/empresa/login";
        model.addAttribute("puestoDTO", new PuestoPublicacionDTO());
        return "empresa/nuevo-puesto";
    }

    @PostMapping("/puesto/publicar")
    public String publicarPuesto(@Valid @ModelAttribute("puestoDTO") PuestoPublicacionDTO dto,
                                 BindingResult result,
                                 HttpSession session,
                                 RedirectAttributes ra) {
        if (result.hasErrors()) return "empresa/nuevo-puesto";

        Long empresaId = (Long) session.getAttribute("empresaId");
        if (empresaId == null) return "redirect:/empresa/login";

        try {
            Puesto puesto = new Puesto();
            puesto.setTitulo(dto.getTitulo());
            puesto.setDescripcion(dto.getDescripcion());
            puesto.setSalario(dto.getSalario());
            puesto.setTipoPublicacion(dto.getTipoPublicacion());
            puestoService.publicarPuesto(puesto, empresaId);
            ra.addFlashAttribute("success", "Puesto publicado correctamente.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/empresa/dashboard";
    }


    @PostMapping("/puesto/desactivar/{id}")
    public String desactivarPuesto(@PathVariable Long id,
                                   HttpSession session,
                                   RedirectAttributes ra) {
        if (session.getAttribute("empresaId") == null) return "redirect:/empresa/login";
        try {
            puestoService.desactivarPuesto(id);
            ra.addFlashAttribute("success", "Puesto desactivado.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/empresa/dashboard";
    }


    @GetMapping("/puesto/candidatos/{id}")
    public String candidatos(@PathVariable Long id,
                             @RequestParam(defaultValue = "1") Integer nivelMinimo,
                             HttpSession session,
                             Model model) {
        if (session.getAttribute("empresaId") == null) return "redirect:/empresa/login";

        Puesto puesto = puestoService.buscarPorId(id);
        List<Oferente> candidatos = (puesto.getHabilidades() == null || puesto.getHabilidades().isEmpty())
                ? Collections.emptyList()
                : oferenteService.buscarCandidatos(
                        puesto.getHabilidades().get(0).getCaracteristica().getId(), nivelMinimo);

        model.addAttribute("puesto", puesto);
        model.addAttribute("candidatos", candidatos);
        return "empresa/candidatos";
    }
}
