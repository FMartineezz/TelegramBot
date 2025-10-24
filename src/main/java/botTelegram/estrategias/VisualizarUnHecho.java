package botTelegram.estrategias;

import clients.PdiProxy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dtos.Fuente.PdiDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("visualizar_hecho")
public class VisualizarUnHecho implements Orden {

    @Autowired
    private PdiProxy pdiProxy;

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String procesarMensaje(String mensaje) {
        try {
            String[] tokens = mensaje.split(" ");
            if (tokens.length < 2) {
                return "Uso: visualizar_hecho <hechoId>";
            }
            String hechoId = tokens[1];

            List<PdiDTO> pdis = pdiProxy.getPdisPorHecho(hechoId);

            return mapper.writeValueAsString(pdis);

        } catch (JsonProcessingException e) {
            return "Error serializando PDIs a JSON";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al obtener PDIs por hecho: " + e.getMessage();
        }
    }
}
