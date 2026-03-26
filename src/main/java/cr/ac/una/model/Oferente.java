package cr.ac.una.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "oferente")
public class Oferente {

    @Id
    @Column(length = 50)
    private String id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellidos;

    @Column(nullable = false)
    private String nacionalidad;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false, unique = true)
    private String correopersonal;

    @Column(nullable = false)
    private String residencia;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "BYTEA")
    private byte[] cv;

    @Column(nullable = false)
    private Boolean aprobado = false;

    @OneToMany(mappedBy = "oferente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OferenteHabilidad> habilidades = new ArrayList<>();
}
