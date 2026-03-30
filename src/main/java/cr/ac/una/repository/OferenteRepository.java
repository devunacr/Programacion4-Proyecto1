package cr.ac.una.repository;

import cr.ac.una.model.Oferente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OferenteRepository extends JpaRepository<Oferente, String> {

    Optional<Oferente> findByCorreopersonal(String correopersonal);

    // oferentes que poseen una habilidad con nivel >= nivelMinimo
    @Query("SELECT DISTINCT oh.oferente FROM OferenteHabilidad oh WHERE oh.caracteristica.id = :caracteristicaId AND oh.nivel >= :nivelMinimo AND oh.oferente.aprobado = true")
    List<Oferente> findByHabilidadYNivelMinimo(@Param("caracteristicaId") Long caracteristicaId, @Param("nivelMinimo") Integer nivelMinimo);
}
