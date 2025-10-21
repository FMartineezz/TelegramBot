package dtos.solicitud;

import dtos.solicitud.EstadoSolicitudBorradoEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SolicitudModificacionRequestDTO {
    private String descripcion;
    private EstadoSolicitudBorradoEnum estado;
}
