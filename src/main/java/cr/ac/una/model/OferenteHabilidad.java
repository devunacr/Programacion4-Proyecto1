package cr.ac.una.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "oferente_habilidad")
public class OferenteHabilidad {

    @EmbeddedId
    private OferenteHabilidadId id = new OferenteHabilidadId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("oferenteId")
    @JoinColumn(name = "oferente_id", nullable = false)
    private Oferente oferente;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("caracteristicaId")
    @JoinColumn(name = "caracteristica_id", nullable = false)
    private Caracteristica caracteristica;

    @Column(nullable = false)
    private int nivel;
}
