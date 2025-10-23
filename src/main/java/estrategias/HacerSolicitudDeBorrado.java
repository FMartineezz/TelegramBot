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
    private SolicitudProxy client;

    @Override
    public String procesarMensaje(String mensaje) {
        String[] mensajeTokenizado = mensaje.split(" ");

        if(mensajeTokenizado.length < 2){
            return "Falta el hecho id, intenete nuevamente escribiendo lo siguiente: hacer_solicitud_de_borrado <HechoId> ";
        }

        String hechoId = mensajeTokenizado[1];
        SolicitudDTO dto = new SolicitudDTO(null, "Solicitud De Borrado", CREADA, hechoId);

        try {
            SolicitudDTO creada = client.agregar(dto);
            return "Solicitud creada correctamente con ID: " + creada.id();
        } catch (IOException e) {
            e.printStackTrace();
            return "Ocurrió un error al conectar con el servicio. Por favor, inténtelo más tarde.";
        }
    }


}
