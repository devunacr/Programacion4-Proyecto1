package cr.ac.una.bolsaempleo.repository;

import cr.ac.una.bolsaempleo.model.Oferente;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OferenteRepository implements IrepositoryMethods<Oferente> {

    private List<Oferente> listaOferentes = new ArrayList<>();

    @Override
    public List<Oferente> buscarATodos() {
        return listaOferentes;
    }

    @Override
    public Optional<Oferente> buscarPorNombre(String nombre) {

        for (Oferente oferente : listaOferentes) {

            if (oferente.getNombre().equalsIgnoreCase(nombre)) {
                return Optional.of(oferente);
            }
        }
        return Optional.empty();
    }


    @Override
    public Optional<Oferente> buscarPorId(String id) {

        for (Oferente oferente : listaOferentes) {

            if (oferente.getId().equals(id)) {
                return Optional.of(oferente);
            }

        }

        return Optional.empty();
    }

    @Override
    public Oferente crearObjeto(Oferente oferenteNuevo) {

        listaOferentes.add(oferenteNuevo);
        return oferenteNuevo;

    }

    @Override
    public Oferente actualizarObjeto(Oferente oferenteActualizado) {

        for (int indice = 0; indice < listaOferentes.size(); indice++) {

            Oferente oferenteActual = listaOferentes.get(indice);

            if (oferenteActual.getId().equals(oferenteActualizado.getId())) {

                listaOferentes.set(indice, oferenteActualizado);
                return oferenteActualizado;

            }

        }

        return null;
    }

    @Override
    public void eliminarObjeto(String id) {

        listaOferentes.removeIf(oferente -> oferente.getId().equals(id));

    }
}