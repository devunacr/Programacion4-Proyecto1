package cr.ac.una.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidatoResultadoDTO {

    private String oferenteId;
    private String nombre;
    private String apellidos;
    private String correopersonal;
    private String residencia;
    private List<HabilidadDTO> habilidades;
    private Double porcentajeCoincidencia; // 0.0 - 100.0
    private boolean tieneCv;
}
