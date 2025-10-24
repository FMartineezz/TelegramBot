package botTelegram.estrategias;

import org.springframework.stereotype.Component;

@Component("visualizar_un_hecho")
public class VisualizarUnHecho implements Orden {
    @Override
    public String procesarMensaje(String mensaje) {
        return "";
    }
}
