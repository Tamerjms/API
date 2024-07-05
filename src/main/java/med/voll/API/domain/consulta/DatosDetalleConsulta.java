package med.voll.API.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosDetalleConsulta(Long id,
                                   Long idPaciente,
                                   Long idMedico,
                                   LocalDateTime fecha) {
}
