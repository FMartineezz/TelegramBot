package estrategias;

import estrategias.Orden;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import clients.AgregadorProxy;
import dtos.Fuente.HechoDTO;

@Component("listar_hechos")
public class ListarHechos implements Orden {
     @Autowired
    private AgregadorProxy proxy;

    @Override
    public String procesarMensaje(String mensaje) {
        try {
            String[] mensajeTokenizado = mensaje.split(" ");
            String collectionName = mensajeTokenizado[1];
            List<HechoDTO> hechos = proxy.getHechos(collectionName);
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.writeValueAsString(hechos);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error al serializar los hechos a JSON", e);
            }
            } catch (Exception e) {
                e.printStackTrace();
                return " Error al obtener Hechos por coleccion en Agregador: " + e.getMessage();
            }
    }
}
