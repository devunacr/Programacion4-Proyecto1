package cr.ac.una.bolsaempleo.repository;
import cr.ac.una.bolsaempleo.model.Puesto;

import java.util.List;
import java.util.Optional;

public class PuestoRepository implements IrepositoryMethods<Puesto> {


    @Override
    public List<Puesto> buscarATodos() {
        return List.of();
    }

    @Override
    public List<Puesto> buscarActivo() {
        return List.of();
    }

    @Override
    public Optional<Puesto> buscarPorNombre(String nombre) {
        return Optional.empty();
    }

    @Override
    public Optional<Puesto> buscarPorId(String id) {
        return Optional.empty();
    }

    @Override
    public Puesto crearObjeto(Puesto obj) {
        return null;
    }

    @Override
    public Puesto actualizarObjeto(Puesto obj) {
        return null;
    }

    @Override
    public void eliminarObjeto(String id) {

    }
}