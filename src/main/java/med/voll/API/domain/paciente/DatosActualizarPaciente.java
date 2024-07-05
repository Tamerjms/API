package med.voll.API.domain.paciente;

import jakarta.validation.constraints.NotNull;
import med.voll.API.domain.direccion.DatosDireccion;

public record DatosActualizarPaciente(@NotNull Long id, String nombre, String documento, DatosDireccion direccion) {
}
