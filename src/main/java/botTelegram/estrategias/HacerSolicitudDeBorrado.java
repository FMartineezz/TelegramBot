package botTelegram.estrategias;

import botTelegram.clients.SolicitudProxy;
import botTelegram.dtos.solicitud.SolicitudDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.NoSuchElementException;

import static botTelegram.dtos.solicitud.EstadoSolicitudBorradoEnum.CREADA;

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
            return "Solicitud creada correctamente con ID: " + creada.id() + "\n"+
                    "Descripcion : " + creada.descripcion() + "\n" +
                    "Estado : "+ creada.estado() + "\n" +
                    "HechoId : "+ creada.hechoId();
        }catch (InvalidParameterException | NoSuchElementException | KeyAlreadyExistsException e ){
            return e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            return "Ocurrió un error al conectar con el servicio. Por favor, inténtelo más tarde.";
        }
    }


}
