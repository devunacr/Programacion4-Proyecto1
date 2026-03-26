package cr.ac.una.repository;

import cr.ac.una.model.Puesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PuestoRepository extends JpaRepository<Puesto, Long> {

    // ultimos 5 puestos, descendente
    List<Puesto> findTop5ByTipoPublicacionAndActivoTrueOrderByFechaPublicacionDesc(String tipoPublicacion);

    // puestos que requieren una característica específica
    @Query("SELECT DISTINCT ph.puesto FROM PuestoHabilidad ph WHERE ph.caracteristica.id = :caracteristicaId AND ph.puesto.activo = true")
    List<Puesto> findByCaracteristica(@Param("caracteristicaId") Long caracteristicaId);
}
