package botTelegram.dtos.solicitud;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SolicitudModificacionRequestDTO {
    private String descripcion;
    private EstadoSolicitudBorradoEnum estado;
}
