package botTelegram.estrategias;
import botTelegram.clients.FuenteProxy;
import botTelegram.dtos.Fuente.PdiDTO;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
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
                return "Formato: agregar_pdi hechoId|descripcion|[lugar]|[urlImagen]";
            }

            String[] partes = comandoYDatos[1].split("\\|");
            String hechoId = partes[0].trim();
            String descripcion = partes.length > 1 ? partes[1].trim() : "";
            String lugar = partes.length > 2 ? partes[2].trim() : "Sin lugar";
            String urlImagen = partes.length > 3 ? partes[3].trim() : null;


            PdiDTO pdi = new PdiDTO(
                    null,
                    hechoId,
                    descripcion,
                    lugar,
                    LocalDateTime.now(),
                    "contenido generado desde bot",
                    List.of(),
                    null,
                    urlImagen
            );

            PdiDTO creado = fuenteProxy.agregarPdi(hechoId, pdi);
            return " PDI agregado al hecho " + hechoId + ": " + creado.descripcion();

        } catch (Exception e) {
            e.printStackTrace();
            return " Error al crear PDI: " + e.getMessage();
        }
    }
}
