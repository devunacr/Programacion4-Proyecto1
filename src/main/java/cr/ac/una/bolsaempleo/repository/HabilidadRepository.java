package cr.ac.una.bolsaempleo.repository;
import cr.ac.una.bolsaempleo.model.Habilidad;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



public class HabilidadRepository implements IrepositoryMethods<Habilidad>{

    @Override
    public List<Habilidad> buscarATodos() {
        return List.of();
    }

    @Override
    public List<Habilidad> buscarActivo() {
        return List.of();
    }

    @Override
    public Optional<Habilidad> buscarPorNombre(String nombre) {
        return Optional.empty();
    }

    @Override
    public Optional<Habilidad> buscarPorId(String id) {
        return Optional.empty();
    }

    @Override
    public Habilidad crearObjeto(Habilidad obj) {
        return null;
    }

    @Override
    public Habilidad actualizarObjeto(Habilidad obj) {
        return null;
    }

    @Override
    public void eliminarObjeto(String id) {

    }
}