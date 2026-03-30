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
public class OferenteHabilidadId implements Serializable {

    @Column(name = "oferente_id", length = 50)
    private String oferenteId;

    @Column(name = "caracteristica_id")
    private Long caracteristicaId;
}
