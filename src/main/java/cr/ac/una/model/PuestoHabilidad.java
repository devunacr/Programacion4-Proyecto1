package cr.ac.una.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "puesto_habilidad")
public class PuestoHabilidad {

    @EmbeddedId
    private PuestoHabilidadId id = new PuestoHabilidadId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("puestoId")
    @JoinColumn(name = "puesto_id", nullable = false)
    private Puesto puesto;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("caracteristicaId")
    @JoinColumn(name = "caracteristica_id", nullable = false)
    private Caracteristica caracteristica;

    @Column(nullable = false)
    private int nivel;
}
