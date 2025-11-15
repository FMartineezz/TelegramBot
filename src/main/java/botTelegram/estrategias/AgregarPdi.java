package botTelegram.estrategias;
import botTelegram.clients.FuenteProxy;
import botTelegram.dtos.Fuente.PdiDTO;
import botTelegram.mapper.PDIMapper;

import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import botTelegram.clients.FuenteProxy;
import botTelegram.dtos.Fuente.PdiDTO;
import botTelegram.mapper.PDIMapper;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component("agregar_pdi")
public class AgregarPdi implements Orden {

    @Autowired
    private FuenteProxy fuenteProxy;

    @Override
    public String procesarMensaje(String mensaje) {
        try {
            String[] comandoYDatos = mensaje.split(" ", 2);

            if (comandoYDatos.length < 2) {
                return """
                        Formato: agregar_pdi hechoId|descripcion|[lugar]|[urlImagen]|[contenido]
                        Ejemplo: agregar_pdi 1|Foto del incendio|Avellaneda|https://...|foto tomada desde el dron
                        """;
            }

            String[] partes = comandoYDatos[1].split("\\|");

            String hechoId     = partes[0].trim();
            String descripcion = partes.length > 1 ? partes[1].trim() : "";
            String lugar       = partes.length > 2 ? partes[2].trim() : "Sin lugar";
            String urlImagen   = partes.length > 3 ? partes[3].trim() : null;
            String contenido   = (partes.length > 4 && !partes[4].isBlank())
                    ? partes[4].trim()
                    : "contenido generado desde bot";

            PdiDTO pdi = new PdiDTO(
                    null,               // id
                    hechoId,
                    descripcion,
                    lugar,
                    LocalDateTime.now(),
                    contenido,
                    List.of(),          // etiquetas vacías: las generará el procesador
                    null,               // resultadoOcr (lo completa el procesador)
                    urlImagen
            );

            PdiDTO creado = fuenteProxy.agregarPdi(pdi);

            return PDIMapper.mapHechoPDI(creado);

        } catch (Exception e) {
            e.printStackTrace();
            return " Error al crear PDI: " + e.getMessage();
        }
    }
}
