package cr.ac.una.controller;

import cr.ac.una.dto.OferenteRegistroDTO;
import cr.ac.una.model.Caracteristica;
import cr.ac.una.model.Oferente;
import cr.ac.una.model.OferenteHabilidad;
import cr.ac.una.repository.CaracteristicaRepository;
import cr.ac.una.service.OferenteService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/oferente")
@RequiredArgsConstructor
public class OferenteController {

    private final OferenteService oferenteService;
    private final CaracteristicaRepository caracteristicaRepository;

    @GetMapping("/registro")
    public String registroForm(Model model) {
        model.addAttribute("oferenteDTO", new OferenteRegistroDTO());
        return "oferente/registro";
    }

    @PostMapping("/registro")
    public String registrar(@Valid @ModelAttribute("oferenteDTO") OferenteRegistroDTO dto,
                            BindingResult result,
                            @RequestParam(required = false) String telefono,
                            RedirectAttributes ra) {
        if (result.hasErrors()) return "oferente/registro";

        try {
            Oferente oferente = new Oferente();
            oferente.setNombre(dto.getNombre());
            oferente.setApellidos(dto.getApellidos());
            oferente.setNacionalidad(dto.getNacionalidad());
            oferente.setCorreopersonal(dto.getCorreopersonal());
            oferente.setResidencia(dto.getResidencia());
            oferente.setPassword(dto.getPassword());
            oferente.setTelefono(telefono != null ? telefono : "");
            oferenteService.registrarOferente(oferente);
            ra.addFlashAttribute("success", "Registro exitoso. Espera la aprobación del administrador.");
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/oferente/login";
    }


    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("rol", "Oferente");
        model.addAttribute("accion", "/oferente/login");
        model.addAttribute("labelUsuario", "Correo");
        model.addAttribute("linkRegistro", "/oferente/registro");
        model.addAttribute("textoRegistro", "¿No tienes cuenta? Registrarse");
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String usuario,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes ra) {
        try {
            Oferente oferente = oferenteService.buscarPorCorreo(usuario);
            if (!oferente.getPassword().equals(password)) throw new IllegalArgumentException("Contraseña incorrecta.");
            if (!oferente.getAprobado()) throw new IllegalStateException("Tu cuenta aún no ha sido aprobada.");
            session.setAttribute("oferenteId", oferente.getId()); // String
            return "redirect:/oferente/dashboard";
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/oferente/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }


    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        String oferenteId = (String) session.getAttribute("oferenteId");
        if (oferenteId == null) return "redirect:/oferente/login";

        Oferente oferente = oferenteService.buscarPorId(oferenteId);
        List<Caracteristica> caracteristicas = caracteristicaRepository.findAll();

        model.addAttribute("oferente", oferente);
        model.addAttribute("caracteristicas",
                caracteristicas != null ? caracteristicas : Collections.emptyList());
        return "oferente/dashboard";
    }


    @PostMapping("/habilidad/agregar")
    public String agregarHabilidad(@RequestParam Long caracteristicaId,
                                   @RequestParam Integer nivel,
                                   HttpSession session,
                                   RedirectAttributes ra) {
        String oferenteId = (String) session.getAttribute("oferenteId");
        if (oferenteId == null) return "redirect:/oferente/login";

        try {
            Oferente oferente = oferenteService.buscarPorId(oferenteId);
            Caracteristica caracteristica = caracteristicaRepository.findById(caracteristicaId)
                    .orElseThrow(() -> new IllegalArgumentException("Característica no encontrada."));

            OferenteHabilidad habilidad = new OferenteHabilidad();
            habilidad.setOferente(oferente);
            habilidad.setCaracteristica(caracteristica);
            habilidad.setNivel(nivel);
            oferenteService.agregarHabilidad(habilidad);
            ra.addFlashAttribute("success", "Habilidad agregada correctamente.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/oferente/dashboard";
    }


    @GetMapping("/cv")
    public String cvForm(HttpSession session, Model model) {
        String oferenteId = (String) session.getAttribute("oferenteId");
        if (oferenteId == null) return "redirect:/oferente/login";
        model.addAttribute("oferente", oferenteService.buscarPorId(oferenteId));
        model.addAttribute("caracteristicas", caracteristicaRepository.findAll());
        return "oferente/dashboard";
    }

    @PostMapping("/cv/subir")
    public String subirCv(@RequestParam MultipartFile cv,
                          HttpSession session,
                          RedirectAttributes ra) {
        String oferenteId = (String) session.getAttribute("oferenteId");
        if (oferenteId == null) return "redirect:/oferente/login";
        if (cv == null || cv.isEmpty()) {
            ra.addFlashAttribute("error", "Debes seleccionar un archivo PDF.");
            return "redirect:/oferente/dashboard";
        }
        try {
            Oferente oferente = oferenteService.buscarPorId(oferenteId);
            oferente.setCv(cv.getBytes());
            oferenteService.actualizarOferente(oferente);
            ra.addFlashAttribute("success", "CV subido correctamente.");
        } catch (IOException e) {
            ra.addFlashAttribute("error", "Error al leer el archivo.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/oferente/dashboard";
    }

    @PostMapping("/aplicar")
    public String aplicar(@RequestParam Long puestoId,
                          @RequestParam MultipartFile cv,
                          HttpSession session,
                          RedirectAttributes ra) {

        String oferenteId = (String) session.getAttribute("oferenteId");
        if (oferenteId == null) {
            ra.addFlashAttribute("error", "Debes iniciar sesión como oferente.");
            return "redirect:/oferente/login";
        }
        try {
            Oferente oferente = oferenteService.buscarPorId(oferenteId);

            if (oferente == null) {
                throw new RuntimeException("Oferente no válido.");
            }

            if (cv == null || cv.isEmpty()) {
                throw new RuntimeException("Debes subir un CV.");
            }

            oferente.setCv(cv.getBytes());
            oferenteService.actualizarOferente(oferente);

            ra.addFlashAttribute("success", "Aplicación enviada correctamente.");

        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/";
    }

}
