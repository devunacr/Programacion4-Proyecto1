package cr.ac.una.bolsaempleo.repository;

import java.util.List;
import java.util.Optional;

public interface IrepositoryMethods<T> {

    List<T> buscarATodos();
    List<T> buscarActivo();
    Optional<T> buscarPorNombre(String nombre);
    Optional<T> buscarPorId(String id);

    T crearObjeto(T obj);
    T actualizarObjeto(T obj);
    void eliminarObjeto(String id);
}
