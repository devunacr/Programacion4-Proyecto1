package cr.ac.una.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HabilidadDTO {

    @NotNull(message = "El id de la característica es obligatorio.")
    private Long caracteristicaId;

    private String nombreCaracteristica;

    @Min(value = 0, message = "El nivel mínimo es 0.")
    @Max(value = 5, message = "El nivel máximo es 5.")
    private Integer nivel;
}
