package cr.ac.una.repository;

import cr.ac.una.model.PuestoHabilidad;
import cr.ac.una.model.PuestoHabilidadId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PuestoHabilidadRepository extends JpaRepository<PuestoHabilidad, PuestoHabilidadId> {

    List<PuestoHabilidad> findByPuestoId(Long puestoId);

    List<PuestoHabilidad> findByCaracteristicaId(Long caracteristicaId);
}
