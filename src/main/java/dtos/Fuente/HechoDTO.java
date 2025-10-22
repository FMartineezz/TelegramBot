package dtos.Fuente;

import java.time.LocalDateTime;
import java.util.List;

public record HechoDTO(
        String id,
        String nombreColeccion,
        String titulo,
        List<String> etiquetas,
        String categoria,
        String ubicacion,
        LocalDateTime fecha,
        String origen
) {}
