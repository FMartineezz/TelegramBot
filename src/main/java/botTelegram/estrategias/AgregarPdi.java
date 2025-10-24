package botTelegram.estrategias;
import botTelegram.clients.FuenteProxy;
import botTelegram.dtos.Fuente.PdiDTO;
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
                return "Formato: agregar_pdi hechoId|descripcion|[lugar]|[urlImagen]|[contenido]|[etiquetas]";
            }

            String[] partes = comandoYDatos[1].split("\\|");
            String hechoId = partes[0].trim();
            String descripcion = partes.length > 1 ? partes[1].trim() : "";
            String lugar = partes.length > 2 ? partes[2].trim() : "Sin lugar";
            String urlImagen = partes.length > 3 ? partes[3].trim() : null;
            String contenido = partes.length > 4 ? partes[4].trim() : "contenido generado desde bot";

            List<String> etiquetas = partes.length > 5
                    ? Arrays.stream(partes[5].split(","))
                    .map(String::trim)
                    .filter(e -> !e.isEmpty())
                    .toList()
                    : List.of();

            PdiDTO pdi = new PdiDTO(
                    null,
                    hechoId,
                    descripcion,
                    lugar,
                    LocalDateTime.now(),
                    contenido,
                    etiquetas,
                    null,
                    urlImagen
            );

            PdiDTO creado = fuenteProxy.agregarPdi(hechoId, pdi);

            StringBuilder respuesta = new StringBuilder();
            respuesta.append(" PDI agregado al hecho ").append(hechoId)
                    .append("\n Descripción: ").append(creado.descripcion());
            if (urlImagen != null) respuesta.append("\n📷 Imagen: ").append(urlImagen);
            if (!etiquetas.isEmpty())
                respuesta.append("\n Etiquetas: ").append(String.join(", ", etiquetas));
            respuesta.append("\n Lugar: ").append(lugar);

            return respuesta.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return " Error al crear PDI: " + e.getMessage();
        }
    }
}
