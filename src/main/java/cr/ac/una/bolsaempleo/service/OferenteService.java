package cr.ac.una.bolsaempleo.service;

import cr.ac.una.bolsaempleo.model.*;
import cr.ac.una.bolsaempleo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OferenteService {
    @Autowired private OferenteRepository r;

    public List<Oferente> listar() {
        return r.findAll();
    }

    public void guardar(Oferente o) {
        r.save(o);
    }

    public Oferente buscarPorEmail(String email) {
        return r.findByCorreo(email);
    }
}