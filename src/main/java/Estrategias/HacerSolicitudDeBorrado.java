package Estrategias;

import org.springframework.stereotype.Component;

@Component("hacer_solicitud_de_borrado")
public class HacerSolicitudDeBorrado implements Orden {

    @Override
    public String procesarMensaje(String mensaje) {
        return "";
    }
}
