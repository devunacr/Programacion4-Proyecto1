package cr.ac.una.bolsaempleo.service;

import cr.ac.una.bolsaempleo.model.*;
import cr.ac.una.bolsaempleo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PuestoService {
    @Autowired private PuestoRepository repository;

    public List<Puesto> listarTodos() { return repository.findAll(); }
    public void guardar(Puesto p) { repository.save(p); }
    public void cambiarEstado(Long id) {
        repository.findById(id).ifPresent(p -> {
            p.setPublico(!p.isPublico());
            repository.save(p);
        });
    }
}