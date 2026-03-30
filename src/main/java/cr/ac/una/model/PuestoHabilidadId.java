package cr.ac.una.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PuestoHabilidadId implements Serializable {

    @Column(name = "puesto_id")
    private Long puestoId;

    @Column(name = "caracteristica_id")
    private Long caracteristicaId;
}
