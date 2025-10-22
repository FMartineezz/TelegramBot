package estrategias;
import clients.FuenteProxy;
import dtos.Fuente.HechoDTO;
import org.springframework.stereotype.Component;

import estrategias.Orden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component("agregar_hecho")
public class AgregarHecho implements Orden {

    @Autowired
    private FuenteProxy fuenteProxy;

    @Override
    public String procesarMensaje(String mensaje) {
        try {
            String[] partes = mensaje.split("\\|");
            if (partes.length < 2) {
                return "Formato: /agregar_hecho nombreColeccion|titulo|[categoria]|[ubicacion]";
            }

            String coleccion = partes[0].replace("/agregar_hecho", "").trim();
            String titulo = partes[1].trim();
            String categoria = partes.length > 2 ? partes[2].trim() : "OTRO";
            String ubicacion = partes.length > 3 ? partes[3].trim() : "Sin ubicación";

            HechoDTO dto = new HechoDTO(
                    null,
                    coleccion,
                    titulo,
                    List.of("bot", "dds"),
                    categoria,
                    ubicacion,
                    LocalDateTime.now(),
                    "bot-telegram"
            );

            HechoDTO creado = fuenteProxy.crearHecho(dto);
            return " Hecho creado correctamente:\n" + creado.titulo() + "\nColección: " + creado.nombreColeccion();

        } catch (Exception e) {
            e.printStackTrace();
            return " Error al crear hecho: " + e.getMessage();
        }
    }
}
