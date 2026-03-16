package cr.ac.una.bolsaempleo.repository;

import cr.ac.una.bolsaempleo.model.Empresa;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmpresaRepository implements IrepositoryMethods<Empresa> {

    private List<Empresa> empresas = new ArrayList<>();

    @Override
    public List<Empresa> findAll() {
        return empresas;
    }

    @Override
    public List<Empresa> findAllActive() {
        List<Empresa> activas = new ArrayList<>();

        for (Empresa e : empresas) {
            // Aquí podrías tener un atributo activo en Empresa
            // if(e.isActivo())
            activas.add(e);
        }

        return activas;
    }

    @Override
    public Optional<Empresa> findByName(String nombre) {

        for (Empresa e : empresas) {
            if (e.getNombre().equalsIgnoreCase(nombre)) {
                return Optional.of(e);
            }
        }

        return Optional.empty();
    }

    @Override
    public Empresa crearObjeto(Empresa obj) {
        empresas.add(obj);
        return obj;
    }

    @Override
    public Empresa actualizarObjeto(Empresa obj) {

        for (int i = 0; i < empresas.size(); i++) {

            if (empresas.get(i).getNombre().equalsIgnoreCase(obj.getNombre())) {
                empresas.set(i, obj);
                return obj;
            }
        }

        return null;
    }

    @Override
    public void eliminarObjeto(String nombre) {

        empresas.removeIf(e -> e.getNombre().equalsIgnoreCase(nombre));

    }
}