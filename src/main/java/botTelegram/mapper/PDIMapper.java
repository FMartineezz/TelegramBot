package botTelegram.mapper;

import botTelegram.dtos.Fuente.PdiDTO;

import java.util.List;

public class PDIMapper {

    public static String mapHechoPDI(PdiDTO pdiDTO) {
        List<String> etiquetas = pdiDTO.etiquetas() != null ? pdiDTO.etiquetas() : List.of();

        StringBuilder sb = new StringBuilder();
        sb.append("PDI agregado al hecho : ").append(pdiDTO.hechoId()).append("\n")
                .append("Descripción: ").append(pdiDTO.descripcion()).append("\n");

        if (pdiDTO.urlImagen() != null && !pdiDTO.urlImagen().isBlank()) {
            sb.append("Imagen: ").append(pdiDTO.urlImagen()).append("\n");
        }

        if (!etiquetas.isEmpty()) {
            sb.append("Etiquetas: ").append(String.join(", ", etiquetas)).append("\n");
        }

        sb.append("Lugar: ").append(pdiDTO.lugar());

        return sb.toString();
    }
}
