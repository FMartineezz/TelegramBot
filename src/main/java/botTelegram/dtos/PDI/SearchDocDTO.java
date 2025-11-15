package botTelegram.dtos.PDI;

import java.time.LocalDateTime;
import java.util.List;

public record SearchDocDTO(
    String id,            // "HECHO:<id>" o "PDI:<id>"
    String tipo,          // HECHO | PDI
    String origenId,
    String hechoId,
    String coleccion,
    String titulo,
    String descripcion,
    List<String> tags,
    String texto,
    boolean oculto,
    LocalDateTime fecha
) {} 