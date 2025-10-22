package estrategias;

import estrategias.Orden;
import org.springframework.stereotype.Component;

@Component("listar_hechos")
public class ListarHechos implements Orden {
    @Override
    public String procesarMensaje(String mensaje) {
        return "";
    }
}
