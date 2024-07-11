package med.voll.API.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosCancelamientoConsulta(Long idConsulta,
                                         @NotNull
                                         Long motivo,
                                         @NotNull
                                         @Future
                                         LocalDateTime fecha,
                                         @NotNull
                                         Long motivoCancelamiento) {

}
