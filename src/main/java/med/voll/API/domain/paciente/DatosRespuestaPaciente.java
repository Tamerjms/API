package med.voll.API.domain.paciente;

import med.voll.API.domain.direccion.DatosDireccion;

public record DatosRespuestaPaciente(Long id, String nombre, String email, String telefono, String documento,
                                     DatosDireccion direccion) {
}
