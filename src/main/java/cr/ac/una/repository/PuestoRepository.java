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

    // puestos públicos que coincidan con al menos una de las características dadas
    @Query("SELECT DISTINCT ph.puesto FROM PuestoHabilidad ph WHERE ph.puesto.tipoPublicacion = 'publico' AND ph.puesto.activo = true AND ph.caracteristica.id IN :ids")
    List<Puesto> findPublicosByCaracteristicas(@Param("ids") List<Long> ids);
}
