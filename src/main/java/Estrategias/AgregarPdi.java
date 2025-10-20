package Estrategias;
import org.springframework.stereotype.Component;

@Component("agregar_pdi")
public class AgregarPdi implements Orden {
    @Override
    public String procesarMensaje(String mensaje) {
        return "";
    }
}
