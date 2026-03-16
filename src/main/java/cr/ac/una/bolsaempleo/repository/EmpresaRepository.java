package cr.ac.una.bolsaempleo.repository;

import cr.ac.una.bolsaempleo.model.Empresa;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmpresaRepository implements IrepositoryMethods<Empresa> {

    private List<Empresa> listaEmpresas = new ArrayList<>();

    @Override
    public List<Empresa> buscarATodos() {
        return listaEmpresas;
    }

    @Override
    public List<Empresa> buscarActivo() {

        List<Empresa> empresasActivas = new ArrayList<>();

        for (Empresa empresa : listaEmpresas) {

            //************************************************* validar si la empresa está activa

            empresasActivas.add(empresa);
        }

        return empresasActivas;
    }

    @Override
    public Optional<Empresa> buscarPorNombre(String nombre) {

        for (Empresa empresa : listaEmpresas) {

            if (empresa.getNombre().equalsIgnoreCase(nombre)) {
                return Optional.of(empresa);
            }

        }

        return Optional.empty();
    }

    @Override
    public Optional<Empresa> buscarPorId(String id) {

        for (Empresa empresa : listaEmpresas) {

            if (empresa.getId().equals(id)) {
                return Optional.of(empresa);
            }

        }

        return Optional.empty();
    }

    @Override
    public Empresa crearObjeto(Empresa empresaNueva) {

        listaEmpresas.add(empresaNueva);
        return empresaNueva;

    }

    @Override
    public Empresa actualizarObjeto(Empresa empresaActualizada) {

        for (int indice = 0; indice < listaEmpresas.size(); indice++) {

            Empresa empresaActual = listaEmpresas.get(indice);

            if (empresaActual.getId().equals(empresaActualizada.getId())) {

                listaEmpresas.set(indice, empresaActualizada);
                return empresaActualizada;

            }

        }

        return null;
    }

    @Override
    public void eliminarObjeto(String id) {

        listaEmpresas.removeIf(empresa -> empresa.getId().equals(id));

    }
}