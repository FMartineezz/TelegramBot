package botTelegram.estrategias;

import botTelegram.clients.BusquedaProxy;
import botTelegram.clients.BusquedaRetrofitClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("buscar_hecho_por_palabra_clave")
public class BusquedaHechosPalabraClave implements Orden{
    @Autowired
    private BusquedaProxy client;

    @Override
    public String procesarMensaje(String mensaje) {
        String[] tokenizado = mensaje.split(" ");
        if(tokenizado.length < 2){
            return "Falta la palabra clave, intenete nuevamente escribiendo lo siguiente: buscar_hecho_por_palabra_clave <palabraClave>" +
                    ";  Opcionales <Tag> <Page> <Size> ";
        }
        String palabraClave = tokenizado[1];
        String tag = tokenizado.length > 2 ? tokenizado[2] : null;
        Integer page;
        try {
            page = tokenizado.length > 3 ? Integer.parseInt(tokenizado[3]) : null;
        } catch (NumberFormatException e) {
            return "El número de página debe ser un entero.";
        }

        Integer size;
        try {
            size = tokenizado.length > 4 ? Integer.parseInt(tokenizado[4]) : null;
        } catch (NumberFormatException e) {
            return "El tamaño de página debe ser un entero.";
        }

        try{
            Map<String, Object> registros;
            if(page != null){
                registros = client.search(palabraClave, tag, page, size);
            }
            else{
                registros = client.search(palabraClave, tag, 0, 10);
            }
            return formatearListado(registros);
        }catch (NoSuchElementException e){
            return e.getMessage();
        }


    }

    public String formatearListado(Map<String, Object> documentos) {
        StringBuilder sb = new StringBuilder();

        List<Map<String, Object>> items =
                (List<Map<String, Object>>) documentos.get("items");

        if (items == null || items.isEmpty()) {
            return "No se encontraron resultados para esa búsqueda.";
        }

        for (Map<String, Object> item : items) {

            // ---- HECHO ----
            Map<String, Object> hecho = (Map<String, Object>) item.get("hecho");

            String hechoId     = hecho != null ? String.valueOf(hecho.get("id")) : "-";
            String titulo      = hecho != null ? String.valueOf(hecho.get("titulo")) : "-";
            String coleccion   = hecho != null ? String.valueOf(hecho.get("coleccion")) : null;

            sb.append("Hecho ID: ").append(hechoId).append("\n")
                    .append("Título: ").append(titulo).append("\n");

            if (coleccion != null && !"null".equals(coleccion)) {
                sb.append("Colección: ").append(coleccion).append("\n");
            }

            // ---- PDIS DEL HECHO ----
            List<Map<String, Object>> pdis =
                    (List<Map<String, Object>>) item.get("pdis");

            if (pdis == null || pdis.isEmpty()) {
                sb.append("PDIs: (no hay PDIs para este hecho)\n");
            } else {
                sb.append("PDIs:\n");
                for (Map<String, Object> pdi : pdis) {
                    String pdiId   = String.valueOf(pdi.get("id"));
                    String desc    = String.valueOf(pdi.get("descripcion"));
                    List<String> etiquetasList = (List<String>) pdi.get("tags");
                    Set<String> etiquetas = etiquetasList != null
                            ? new LinkedHashSet<>(etiquetasList)
                            : Set.of();

                    sb.append("  - ID: ").append(pdiId).append("\n");
                    if (desc != null && !"null".equals(desc)) {
                        sb.append("    Descripción: ").append(desc).append("\n");
                    }
                    if (!etiquetas.isEmpty()) {
                        sb.append("    Tags: ").append(etiquetas).append("\n");
                    }
                }
            }

            sb.append("-------------------------\n");
        }

        return sb.toString();
    }





}
