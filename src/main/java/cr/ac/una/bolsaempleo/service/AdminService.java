package cr.ac.una.bolsaempleo.service;

import cr.ac.una.bolsaempleo.model.*;
import cr.ac.una.bolsaempleo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdminService {
    @Autowired private AdminRepository r;

    public List<Administrador> listar() {
        return r.findAll();
    }
}