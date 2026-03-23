package cr.ac.una.bolsaempleo.repository;

import cr.ac.una.bolsaempleo.model.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Administrador, Long> {
}