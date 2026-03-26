package cr.ac.una.repository;

import cr.ac.una.model.Caracteristica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaracteristicaRepository extends JpaRepository<Caracteristica, Long> {

    // raices del árbol
    List<Caracteristica> findByPadreIsNull();

    // hijos directos de un nodo
    List<Caracteristica> findByPadreId(Long padreId);
}
