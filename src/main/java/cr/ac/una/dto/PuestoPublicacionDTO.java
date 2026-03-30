package cr.ac.una.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PuestoPublicacionDTO {

    @NotBlank(message = "El título es obligatorio.")
    private String titulo;

    @NotBlank(message = "La descripción es obligatoria.")
    private String descripcion;

    @NotNull(message = "El salario es obligatorio.")
    @DecimalMin(value = "0.0", inclusive = false, message = "El salario debe ser mayor a 0.")
    private BigDecimal salario;

    @NotBlank(message = "El tipo de publicación es obligatorio.")
    @Pattern(regexp = "publico|privado", message = "El tipo debe ser 'publico' o 'privado'.")
    private String tipoPublicacion;

    @Valid
    private List<HabilidadDTO> habilidades;
}
