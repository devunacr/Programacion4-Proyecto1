package cr.ac.una.bolsaempleo.repository;

import cr.ac.una.bolsaempleo.model.Habilidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabilidadRepository extends JpaRepository<Habilidad, Long> {
}