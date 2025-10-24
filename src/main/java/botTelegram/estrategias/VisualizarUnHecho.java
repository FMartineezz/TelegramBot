package botTelegram.estrategias;

import botTelegram.clients.PdiProxy;
import botTelegram.clients.FuenteProxy;
import botTelegram.dtos.Fuente.HechoDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import botTelegram.dtos.Fuente.PdiDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("visualizar_hecho")
public class VisualizarUnHecho implements Orden {

    @Autowired private FuenteProxy fuenteProxy;
    @Autowired private PdiProxy pdiProxy;
    @Autowired private ObjectMapper mapper; // ya configurado con JavaTime + snake_case

    @Override
    public String procesarMensaje(String mensaje) {
        try {
            // uso: "visualizar_hecho 1"
            String[] tokens = mensaje.trim().split("\\s+");
            if (tokens.length < 2) return "Uso: visualizar_hecho <hechoId>";
            String hechoId = tokens[1];

            HechoDTO hecho = fuenteProxy.getHecho(hechoId);
            List<PdiDTO> pdis = pdiProxy.getPdisPorHecho(hechoId);

            StringBuilder sb = new StringBuilder();

            // Encabezado: el Hecho (tal cual pediste)
            sb.append(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hecho)).append("\n");
            sb.append("Pdis:\n");

            // Lista de PDIs (tal cual pediste, con snake_case en campos)
            for (PdiDTO p : pdis) {
                // imprimimos con snake_case: el DTO ya mapea; para serializar en snake usamos el mapper global
                sb.append(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(p)).append("\n");
            }

            // Telegram limita ~4096 chars por mensaje: si excede, recortá o partilo.
            return sb.toString().trim();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error al consultar hecho y PDIs: " + e.getMessage();
        }
    }
}
