package botTelegram.estrategias;

import botTelegram.clients.SolicitudProxy;
import botTelegram.dtos.solicitud.EstadoSolicitudBorradoEnum;
import botTelegram.dtos.solicitud.SolicitudDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

@Component("cambiar_estado_solicitud")
public class CambiarEstadoSolicitud implements Orden {

    @Autowired
    private SolicitudProxy client;

    @Override
    public String procesarMensaje(String mensaje) {
        String[] tokenizado = mensaje.split(" ");
        if (tokenizado.length < 3) {
            return "Uso incorrecto. Debe ser: cambiar_estado_solicitud <ID> <NUEVO_ESTADO>";
        }

        String id = tokenizado[1];
        String nuevoEstadoString = tokenizado[2];

        EstadoSolicitudBorradoEnum nuevoEstado;
        try {
            nuevoEstado = EstadoSolicitudBorradoEnum.valueOf(nuevoEstadoString.toUpperCase());
        } catch (IllegalArgumentException e) {
            return "Estado inválido. Estados válidos: " + Arrays.toString(EstadoSolicitudBorradoEnum.values());
        }


        SolicitudDTO solicitudOriginal = client.buscarPorId(id);

        try {
            SolicitudDTO soliModificada = client.modificar(id, nuevoEstado, solicitudOriginal.descripcion());
            return "Solicitud con id : " + id + "fue modificada correctamente, y ahora su estado es : " + nuevoEstado;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
