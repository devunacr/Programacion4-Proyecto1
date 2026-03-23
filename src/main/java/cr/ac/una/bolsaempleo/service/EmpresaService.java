package cr.ac.una.bolsaempleo.service;

import cr.ac.una.bolsaempleo.model.*;
import cr.ac.una.bolsaempleo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmpresaService {
    @Autowired private EmpresaRepository r;
    
    public List<Empresa> listar() {
        return r.findAll();
    }

    public void guardar(Empresa empresa) {
        r.save(empresa);
    }

    public Empresa buscarPorEmail(String email) {
       return r.findByCorreo(email);
    }
}