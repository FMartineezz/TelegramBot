package botTelegram.estrategias;

import botTelegram.clients.BusquedaProxy;
import botTelegram.clients.BusquedaRetrofitClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

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
        List<Map<String, Object>> lista = (List<Map<String, Object>>) documentos.get("items");

        for (Map<String, Object> doc : lista) {

            String id = String.valueOf(doc.get("id"));
            String nombre = String.valueOf(doc.get("titulo"));
            String tipo = String.valueOf(doc.get("tipo"));

            List<String> tags = (List<String>) doc.get("tags");
            String tagsString = tags != null ? String.join(", ", tags) : "Sin tags";


            sb.append("ID: ").append(id).append("\n")
                    .append("Nombre: ").append(nombre).append("\n")
                    .append("Tipo: ").append(tipo).append("\n")
                    .append("Tags: ").append(tagsString).append("\n")
                    .append("-------------------------\n");
        }

        return sb.toString();
    }




}
