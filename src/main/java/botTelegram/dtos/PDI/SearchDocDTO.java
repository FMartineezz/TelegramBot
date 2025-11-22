package botTelegram.dtos.PDI;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public record SearchDocDTO(
        String id,
        String hechoId,
        String nombreColeccion,
        String titulo,
        Set<String>etiquetas,
        String categoria,
        String ubicacion,
        LocalDateTime fecha,
        String origen,
        Set<String> palabrasClaves,
        boolean oculto
) {} 