package med.voll.API.domain.medico;

import med.voll.API.domain.direccion.DatosDireccion;

public record DatosRespuestaMedico(Long id,
                                   String nombre, String email,
                                   String telefono,
                                   String documento,
                                   DatosDireccion direccion) {
}
