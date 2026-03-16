package cr.ac.una.bolsaempleo.repository;

import cr.ac.una.bolsaempleo.model.Empresa;

import java.util.List;
import java.util.Optional;

public interface IrepositoryMethods<T> {

    List<T> findAll();
    List<T> findAllActive();
    Optional<T> findByName(String nombre);

    T crearObjeto(T obj);
    T actualizarObjeto(T obj);
    void eliminarObjeto(String nombre);
}
