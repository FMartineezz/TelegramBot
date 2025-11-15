package botTelegram.estrategias;

import botTelegram.clients.PdiProxy;
import botTelegram.clients.FuenteProxy;
import botTelegram.dtos.Fuente.HechoDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import botTelegram.dtos.Fuente.PdiDTO;
import botTelegram.mapper.PDIMapper;
import botTelegram.mapper.hechoMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("visualizar_hecho")
public class VisualizarUnHecho implements Orden {

    @Autowired private FuenteProxy fuenteProxy;
    @Autowired private PdiProxy pdiProxy;
    @Autowired private ObjectMapper mapper;

    @Override
    public String procesarMensaje(String mensaje) {
        try {

            String[] tokens = mensaje.trim().split("\\s+");
            if (tokens.length < 2) return "Uso: visualizar_hecho <hechoId>";
            String hechoId = tokens[1];

            HechoDTO hecho = fuenteProxy.getHecho(hechoId);
            List<PdiDTO> pdis = pdiProxy.getPdisPorHecho(hechoId);

            StringBuilder sb = new StringBuilder();
            sb.append(hechoMapper.mapearHecho(hecho));
            //sb.append(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(hecho)).append("\n");
            //sb.append("Pdis:\n");

            if (pdis == null || pdis.isEmpty()) {
                //sb.append("\"No hay Pdis para el hecho ").append(hechoId).append("\"");
            } else {
                
                for (PdiDTO p : pdis) {
                    sb.append(PDIMapper.mapHechoPDI(p)).append("\n");
                }
            }

            return sb.toString().trim();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error al consultar hecho y PDIs: " + e.getMessage();
        }
    }
}
