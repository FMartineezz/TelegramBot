package botTelegram;

import botTelegram.estrategias.Orden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class OrdenTester {

    @Autowired
    private Map<String, Orden> estrategias;

    public void testReal(String mensaje) {
        String comando = mensaje.split(" ")[0];
        Orden orden = estrategias.get(comando);

        if (orden == null) {
            System.out.println("Comando no encontrado: " + comando);
            return;
        }

        String respuesta = orden.procesarMensaje(mensaje);
        System.out.println("RESPUESTA REAL:\n" + respuesta);
    }


}

