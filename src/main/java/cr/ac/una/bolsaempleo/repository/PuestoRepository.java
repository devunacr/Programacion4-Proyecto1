package cr.ac.una.bolsaempleo.repository;

import cr.ac.una.bolsaempleo.model.Puesto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PuestoRepository implements IrepositoryMethods<Puesto> {

    private List<Puesto> listaPuestos = new ArrayList<>();

    @Override
    public List<Puesto> buscarATodos() {
        return listaPuestos;
    }

    @Override
    public Optional<Puesto> buscarPorNombre(String nombre) {

        for (Puesto puesto : listaPuestos) {

            if (puesto.getTitulo().equalsIgnoreCase(nombre)) {
                return Optional.of(puesto);
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<Puesto> buscarPorId(String id) {

        for (Puesto puesto : listaPuestos) {

            if (puesto.getId().equals(id)) {
                return Optional.of(puesto);
            }

        }

        return Optional.empty();
    }

    @Override
    public Puesto crearObjeto(Puesto nuevo) {

        listaPuestos.add(nuevo);
        return nuevo;

    }

    @Override
    public Puesto actualizarObjeto(Puesto puestoAc) {

        for (int indice = 0; indice < listaPuestos.size(); indice++) {

            Puesto puestoActual = listaPuestos.get(indice);

            if (puestoActual.getId().equals(puestoAc.getId())) {

                listaPuestos.set(indice, puestoAc);
                return puestoAc;

            }

        }

        return null;
    }

    @Override
    public void eliminarObjeto(String id) {

        listaPuestos.removeIf(puesto -> puesto.getId().equals(id));

    }
}