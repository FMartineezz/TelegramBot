package botTelegram.dtos.Fuente;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PdiDTO(
        String id,
        String hechoId,
        String descripcion,
        String lugar,
        LocalDateTime momento,
        String contenido,
        List<String> etiquetas,
        String resultadoOcr,
        String urlImagen
) {}