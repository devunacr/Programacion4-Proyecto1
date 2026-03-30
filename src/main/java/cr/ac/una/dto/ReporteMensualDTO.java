package cr.ac.una.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteMensualDTO {

    private String mes;                         // formato "YYYY-MM"
    private int puestosPublicadosEnElMes;
    private long puestosActivos;
    private int totalEmpresas;
    private int empresasPendientesAprobacion;
    private int totalOferentes;
    private int oferentesPendientesAprobacion;
}
