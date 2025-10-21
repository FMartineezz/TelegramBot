package estrategias;

import org.springframework.stereotype.Component;

@Component("cambiar_estado_solicitud")
public class CambiarEstadoSolicitud implements Orden {

    @Override
    public String procesarMensaje(String mensaje) {
        return "";
    }
}
