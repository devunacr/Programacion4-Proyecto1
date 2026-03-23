package cr.ac.una.bolsaempleo.repository;
import org.springframework.ui.Model;
import cr.ac.una.bolsaempleo.model.Administrador;
import cr.ac.una.bolsaempleo.model.Empresa;
import cr.ac.una.bolsaempleo.model.Oferente;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthRepository {

    private final OferenteRepository oferenteRepository;
    private final EmpresaRepository empresaRepository;
    private final AdminRepository adminRepository;

    public AuthRepository(OferenteRepository oferenteRepository,
                       EmpresaRepository empresaRepository,
                       AdminRepository adminRepository) {
        this.oferenteRepository = oferenteRepository;
        this.empresaRepository = empresaRepository;
        this.adminRepository = adminRepository;
    }

    public String autenticar(String correo, String password, Model model) {
        // Oferente
        Optional<Oferente> oferente = oferenteRepository.buscarPorCorreo(correo);
        if (oferente.isPresent() && oferente.get().getPassword().equals(password)) {
            model.addAttribute("oferente", oferente.get());
            return "oferente/dashboard";
        }

        // Empresa
        Optional<Empresa> empresa = empresaRepository.buscarPorCorreo(correo);
        if (empresa.isPresent() && empresa.get().getPassword().equals(password)) {
            model.addAttribute("empresa", empresa.get());
            return "empresa/dashboard";
        }

        // Admin
        Optional<Administrador> admin = adminRepository.buscarPorNombre(correo);
        if (admin.isPresent() && admin.get().getPassword().equals(password)) {
            model.addAttribute("admin", admin.get());
            return "admin/dashboard";
        }

        // Fallo
        model.addAttribute("error", "Credenciales inválidas");
        return "login";
    }
}