package med.voll.API.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.API.domain.direccion.DatosDireccion;
import med.voll.API.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController //se le indica al codigo que es un controller
@RequestMapping ("/medicos") //se le indica la http addres que se esta utilizando en el API
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

//    @PostMapping//Este postmaping funciona cargando a la base de datos pero solo muetra un 200 ok y no cumple las buenas practicas
//    public void registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico){//se debe poner el RequestBody para que el entienda que el mensaje que esta hciendo post es un objeto y que viene del cuerpo del api
//        //System.out.println(datosRegistroMedico);
//        medicoRepository.save(new Medico(datosRegistroMedico));
//    }

    //Por buenas practicas de codigo cuando se hace un post deberia mostrar lo sigueinte:
    // return 201 Created
    //URL donde encontrar al medico
    //GET http://localhost:8080/medicos

    @PostMapping
    public ResponseEntity<DatosRespuestaMedico> registroMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico,
                                                               UriComponentsBuilder uriComponentsBuilder){
        Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
                medico.getTelefono(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()));
        //URI url = "http://localhost:8080/medicos"+medico.getId();//esta es una posible forma de crear el url pero en caso de cambiar a un servidor cambiaria
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedico);
    }


//    @GetMapping
//    public List<DatosListadoMedico> listadoMedico(){
//        return medicoRepository.findAll().stream().map(DatosListadoMedico::new).toList();
//    }//con este metodo traiamos los datos de la base de datos y los mostramos en un json en forma de lista
//    @GetMapping
//    public Page<DatosListadoMedico> listadoMedico(@PageableDefault(size = 10) Pageable paginacion){
////        return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);//con este metodo se muestran todos los logs que esten en la base de datos
//        return medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new);
//    }//con este get mapping realizamos el metodo get pero no acorde a las buenas practicas del response entity

    @GetMapping
    public ResponseEntity<Page<DatosListadoMedico>> listadoMedico(@PageableDefault(size = 10) Pageable paginacion){
//        return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);//con este metodo se muestran todos los logs que esten en la base de datos
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new));
    }//aqui le estamos poniento un wraper al metodo

    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaMedico> actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico){
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
                medico.getTelefono(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento())));
    }

//    @DeleteMapping("/{id}")
//    @Transactional
//    public void eliminarMedico(@PathVariable Long id){
//        Medico medico = medicoRepository.getReferenceById(id);
//        medicoRepository.delete(medico);
//    }//con este metodo lo que se hace es eliminar por complet de la base de datos el id que se coloque en la url

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico> retornarDatosMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        var datosMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
                medico.getTelefono(), medico.getEspecialidad().toString(),
                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                        medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(),
                        medico.getDireccion().getComplemento()));
        return ResponseEntity.ok(datosMedico);
    }

}
