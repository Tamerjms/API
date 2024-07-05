package med.voll.API.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.API.domain.consulta.ConsultaRepository;
import med.voll.API.domain.consulta.DatosAgendarConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoConConsulta implements ValidadorDeConsultas {

    @Autowired
    private ConsultaRepository repository;

    public void validar(DatosAgendarConsulta datos){
        if (datos.idMedico()==null){
            return;
        }

        var medicoConConsulta = repository.existByMedicoIdAndData(datos.idMedico(), datos.fecha());

        if (medicoConConsulta){
            throw new ValidationException("Este medico ya tiene una consulta en ese horario");
        }

    }
}
