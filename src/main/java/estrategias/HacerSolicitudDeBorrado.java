package estrategias;

import clients.SolicitudProxy;
import dtos.solicitud.EstadoSolicitudBorradoEnum;
import dtos.solicitud.SolicitudDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static dtos.solicitud.EstadoSolicitudBorradoEnum.CREADA;

@Component("hacer_solicitud_de_borrado")
public class HacerSolicitudDeBorrado implements Orden {

    @Autowired
    private final SolicitudProxy client;

    @Override
    public String procesarMensaje(String mensaje) {
        String[] mensajeTokenizado = mensaje.split(" ");
        String hechoId = mensajeTokenizado[1];

        SolicitudDTO dto = new SolicitudDTO(" ", "Solicitud De Borrado", CREADA, hechoId);
        SolicitudDTO response =client.agregar(dto);

        //TODO COMPLETAR





        return "";
    }


}
