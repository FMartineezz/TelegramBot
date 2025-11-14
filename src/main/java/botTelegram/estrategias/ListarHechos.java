package botTelegram.estrategias;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import botTelegram.clients.AgregadorProxy;
import botTelegram.dtos.Fuente.HechoDTO;
import botTelegram.mapper.hechoMapper;

@Component("listar_hechos")
public class ListarHechos implements Orden {
     @Autowired
    private AgregadorProxy proxy;

    @Override
    public String procesarMensaje(String mensaje) {
        try {
            String[] comandoYDatos = mensaje.split(" ", 2);
            if (comandoYDatos.length < 2) {
                return "Formato: /listar_hechos nombreColeccion|pagina|tamaño";
            }

            String[] partes = comandoYDatos[1].split("\\|");
            String coleccion = partes[0].trim();
            int pagina =  0;
            int size =  0;
            if(partes.length >= 3){
                pagina = partes[1].trim().length() > 0 ? Integer.parseInt(partes[1].trim()) : 0;
                size = partes[2].trim().length() > 0 ? Integer.parseInt(partes[2].trim()) : 0;
            }
            

            List<HechoDTO> hechos;
            if(size >0){
                hechos = proxy.getHechos(coleccion, pagina, size);
            }else{
                hechos = proxy.getHechos(coleccion);
            }
            ObjectMapper mapper = new ObjectMapper();
            try {
                return hechos.stream()
                .map(hechoMapper::mapearHecho)
                .collect(Collectors.joining(",\n"));
            } catch (Exception e) {
                e.printStackTrace();
                return " Error en el mapeo: " + e.getMessage();
            }
        }catch (Exception e) {
            e.printStackTrace();
            return " Error al consultar hechos : " + e.getMessage();
        }
    }
}
