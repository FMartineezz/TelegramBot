package botTelegram.dtos.Fuente;

import lombok.Data;

@Data
public class PdiEnqueueResponseDTO {
    private String hechoId;
    private String messageId;
    private String status;
}
