package cr.ac.una.bolsaempleo.controller;

import cr.ac.una.bolsaempleo.model.Empresa;
import cr.ac.una.bolsaempleo.model.Habilidad;
import cr.ac.una.bolsaempleo.model.Puesto;
import cr.ac.una.bolsaempleo.model.Oferente;
import cr.ac.una.bolsaempleo.repository.EmpresaRepository;
import cr.ac.una.bolsaempleo.repository.PuestoRepository;
import cr.ac.una.bolsaempleo.repository.OferenteRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {

    @Autowired private EmpresaRepository empresaRepository;
    @Autowired private PuestoRepository puestoRepository;
    @Autowired private OferenteRepository oferenteRepository;
    private static final List<String> LISTA_HABILIDADES = List.of(
            "Java", "Spring Boot", "SQL", "HTML", "CSS", "JavaScript", "Python", "React", "Angular", "Vue.js",
            "Node.js", "Express", "Docker", "Kubernetes", "AWS", "Azure", "Git", "GitHub", "MongoDB", "PostgreSQL",
            "Hibernate", "JPA", "Microservicios", "REST API", "Unit Testing", "JUnit", "Maven", "Gradle", "C#", ".NET",
            "PHP", "Laravel", "Swift", "Kotlin", "Android Studio", "Unity", "Data Science", "Machine Learning", "Excel", "Power BI",
            "Tableau", "Scrum", "Agile", "Linux", "Bash", "Cybersecurity", "Blockchain", "NoSQL", "Firebase", "Redux"
    );

    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        Empresa empresaLogueada = (Empresa) session.getAttribute("usuarioLogueado");

        if (empresaLogueada == null || !"empresa".equals(session.getAttribute("rol"))) {
            return "redirect:/login?rol=Empresa";
        }

        List<Puesto> listaPuestos = puestoRepository.buscarATodos();

        model.addAttribute("puestos", listaPuestos);
        model.addAttribute("sessionActive", true);
        model.addAttribute("nombreEmpresa", empresaLogueada.getNombre());

        return "empresa/dashboard";
    }

    @GetMapping("/crear_puesto")
    public String mostrarFormularioPublicar(Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) return "redirect:/login";

        Puesto p = new Puesto();
        p.setHabilidades(new ArrayList<>());
        model.addAttribute("puesto", p);
        model.addAttribute("todasLasHabilidades", LISTA_HABILIDADES);
        return "empresa/crear_puesto";
    }

    @PostMapping("/eliminar_puesto")
    public String eliminarPuesto(@RequestParam("id") String id) {
        puestoRepository.eliminarObjeto(id);
        return "redirect:/empresa/dashboard";
    }

    @GetMapping("/candidato_detalle/{id}")
    public String verDetalleCandidato(@PathVariable("id") String id, Model model, HttpSession session) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }

        Optional<Oferente> oferenteOpt = oferenteRepository.buscarPorId(id);
        if (oferenteOpt.isPresent()) {
            model.addAttribute("candidato", oferenteOpt.get());
            model.addAttribute("sessionActive", true);
            return "empresa/candidato_detalle";
        }

        return "redirect:/empresa/buscar_candidatos";
    }

    @PostMapping("/puestos")
    public String procesarPuesto(@ModelAttribute("puesto") Puesto puesto,
                                 @RequestParam(value="action") String action,
                                 @RequestParam(value="habilidadNombre", required=false) String habNombre,
                                 Model model, HttpSession session) {

        if ("agregarHabilidad".equals(action)) {
            if (habNombre != null && !habNombre.isEmpty()) {
                Habilidad nuevaHabilidad = new Habilidad();
                nuevaHabilidad.setNombre(habNombre);

                puesto.getHabilidades().add(nuevaHabilidad);
            }
            model.addAttribute("todasLasHabilidades", LISTA_HABILIDADES);
            return "empresa/crear_puesto";
        }

        if ("guardar".equals(action)) {
            puestoRepository.crearObjeto(puesto);
            return "redirect:/empresa/dashboard";
        }
        return "redirect:/empresa/dashboard";
    }

    @GetMapping("/buscar_candidatos")
    public String mostrarBusquedaSimple(Model model, HttpSession session) {
        if (!validarSesion(session)) return "redirect:/login";

        model.addAttribute("candidatos", oferenteRepository.buscarATodos());
        model.addAttribute("sessionActive", true);

        return "empresa/buscar_candidatos";
    }

    @GetMapping("/busqueda_avanzada_candidatos")
    public String filtrarCandidatos(
            @RequestParam(value = "texto", required = false) String texto,
            @RequestParam(value = "habilidad", required = false) String habilidad,
            @RequestParam(value = "nivelMin", required = false, defaultValue = "1") int nivelMin,
            @RequestParam(value = "verDetalle", required = false) String verDetalle,
            Model model, HttpSession session) {

        if (!validarSesion(session)) return "redirect:/login";

        String habilidadBusqueda = (habilidad != null && !habilidad.isEmpty()) ? habilidad : null;

        List<Oferente> listaFiltrada = oferenteRepository.buscarConFiltros(habilidadBusqueda, nivelMin);

        if (texto != null && !texto.isEmpty()) {
            listaFiltrada = listaFiltrada.stream()
                    .filter(o -> o.getNombre().toLowerCase().contains(texto.toLowerCase()))
                    .toList();
        }

        model.addAttribute("todasLasHabilidades", LISTA_HABILIDADES);

        model.addAttribute("candidatos", listaFiltrada);

        if (verDetalle != null) {
            Optional<Oferente> o = oferenteRepository.buscarPorId(verDetalle);
            o.ifPresent(value -> model.addAttribute("candidatoSeleccionado", value));
        }

        return "empresa/busqueda_candidatos_avanzada";
    }

    private boolean validarSesion(HttpSession session) {
        return session.getAttribute("usuarioLogueado") != null &&
                "empresa".equals(session.getAttribute("rol"));
    }
}