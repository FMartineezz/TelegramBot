package botTelegram.mapper;

import java.util.List;

import botTelegram.dtos.Fuente.PdiDTO;

public class PDIMapper {
        public static String mapHechoPDI(PdiDTO PdiDTO,List<String> etiquetas) {
        return "PDI agregado al hecho : " + PdiDTO.hechoId() + "\n" +
               "Descripción: " + PdiDTO.descripcion() + "\n" +
               "Imagen: " + PdiDTO.urlImagen()+"\n" +
               "Etiquetas: " + String.join(", ", etiquetas) + "\n" +
                "Lugar: " + PdiDTO.lugar();
    }
}
