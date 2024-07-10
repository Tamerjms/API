package med.voll.API.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.API.domain.consulta.AgendaDeConsultaService;
import med.voll.API.domain.consulta.DatosAgendarConsulta;
import med.voll.API.domain.consulta.DatosCancelamientoConsulta;
import med.voll.API.domain.consulta.DatosDetalleConsulta;
import med.voll.API.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
class ConsultaController {

    @Autowired
    private AgendaDeConsultaService service;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DatosAgendarConsulta datos) throws ValidacionDeIntegridad {
        //System.out.println(datos);

        var response = service.agendar(datos);

        return ResponseEntity.ok(response);
    }

//    @DeleteMapping
//    @Transactional
//    public ResponseEntity cancelar(@RequestBody @Valid DatosCancelamientoConsulta datos) throws ValidacionDeIntegridad{
//        service.cancelar(datos);
//        return ResponseEntity.noContent().build();
//    }
}
