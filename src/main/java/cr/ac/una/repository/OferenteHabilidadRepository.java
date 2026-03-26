package cr.ac.una.repository;

import cr.ac.una.model.OferenteHabilidad;
import cr.ac.una.model.OferenteHabilidadId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OferenteHabilidadRepository extends JpaRepository<OferenteHabilidad, OferenteHabilidadId> {

    List<OferenteHabilidad> findByOferenteId(String oferenteId);

    List<OferenteHabilidad> findByCaracteristicaId(Long caracteristicaId);
}
