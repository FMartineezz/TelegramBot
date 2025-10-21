package estrategias;
import org.springframework.stereotype.Component;

@Component("agregar_hecho")
public class AgregarHecho implements Orden{

    @Override
    public String procesarMensaje(String mensaje) {
        return "";
    }

}
