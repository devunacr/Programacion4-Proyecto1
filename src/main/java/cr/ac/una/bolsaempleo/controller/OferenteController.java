package cr.ac.una.bolsaempleo.controller;
import cr.ac.una.bolsaempleo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller @RequestMapping("/oferente")
public class OferenteController {
    @GetMapping("/perfil") public String perfil() { return "oferente/perfil"; }
}