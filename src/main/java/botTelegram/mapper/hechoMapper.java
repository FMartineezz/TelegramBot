package botTelegram.mapper;

import botTelegram.dtos.Fuente.HechoDTO;

public class hechoMapper {
    public static String mapearHecho(HechoDTO hecho) {
        return " Hecho : " + hecho.id() + "\n" +
               "Título: " + hecho.titulo() + "\n" +
               "Colección: " + hecho.nombreColeccion()+"\n" +
               "Categoría: " + hecho.categoria() + "\n" +
               "Etiquetas: " + String.join(", ", hecho.etiquetas()) + "\n";
    }
}
