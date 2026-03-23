package cr.ac.una.bolsaempleo.controller;
import cr.ac.una.bolsaempleo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller @RequestMapping("/admin")
public class AdminController {
    @GetMapping("/panel") public String panel() { return "admin/panel"; }
}