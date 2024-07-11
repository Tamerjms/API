package med.voll.API.domain.medico;

import med.voll.API.domain.consulta.Consulta;
import med.voll.API.domain.direccion.DatosDireccion;
import med.voll.API.domain.paciente.DatosRegistroPaciente;
import med.voll.API.domain.paciente.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {

    Page<Medico> findByActivoTrue(Pageable paginacion);

    @Query("""
            SELECT m FROM Medico m
            WHERE m.activo = true and
            m.especialidad = :especialidad and
            m.id not in (
            SELECT c.medico.id FROM Consulta c
            WHERE c.data = :data
            )
            ORDER BY rand()
            LIMIT 1
            """)
    Medico seleccionarMedicoConEspecialidadEnFecha(Especialidad especialidad, LocalDateTime data);

    @Query("""
            SELECT m.activo
            FROM Medico m
            WHERE m.id = :idMedico
            """)
    Boolean findActivoById(Long idMedico);

}
