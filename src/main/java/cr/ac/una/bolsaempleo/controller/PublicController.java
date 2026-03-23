package cr.ac.una.bolsaempleo.controller;
import cr.ac.una.bolsaempleo.model.Administrador;
import cr.ac.una.bolsaempleo.model.Oferente;
import cr.ac.una.bolsaempleo.model.Puesto;
import cr.ac.una.bolsaempleo.repository.AdminRepository;
import cr.ac.una.bolsaempleo.repository.OferenteRepository;
import cr.ac.una.bolsaempleo.repository.PuestoRepository;
import cr.ac.una.bolsaempleo.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Optional;

import java.util.List;

@Controller
public class PublicController {

    @Autowired
    private PuestoRepository puestoRepository;

    @Autowired
    private OferenteRepository oferenteRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AuthRepository authRepository;

    @GetMapping("/")
    public String index(Model model) {
        List<Puesto> ultimosPuestos = puestoRepository.buscarUltimosCinco();
        model.addAttribute("puestos", ultimosPuestos);
        model.addAttribute("mensaje", "Bienvenido a la Bolsa de Empleo");
        return "index";
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam("correo") String correo,
                                @RequestParam("password") String password,
                                Model model) {
        // Ahora sí, con la instancia inyectada
        return authRepository.autenticar(correo, password, (Model) model);
    }
}