package botTelegram;

import botTelegram.estrategias.Orden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Service
@SuppressWarnings("deprecation")
public class TelegramBot extends TelegramLongPollingBot {
    @Autowired
    private Map<String, Orden> estrategias;

    private final Set<Long> saludados = new HashSet<>();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            final String messageTextReceived = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            SendMessage responseMsg = new SendMessage();
            responseMsg.setChatId(chatId);

            if (!saludados.contains(chatId)) {
                saludados.add(chatId);
                String comandosDisponibles = String.join(", ", estrategias.keySet());
                responseMsg.setText("""
                        Hola! Soy el bot del grupo 10\s
                        Comandos disponibles:
                       \s
                       \s""" + comandosDisponibles);
                enviarMensaje(responseMsg);
                return;
            }

            Orden orden = obtenerOrden(messageTextReceived);

            if (orden != null) {
                String respuesta = orden.procesarMensaje(messageTextReceived);
                responseMsg.setText(respuesta);
            } else {
                String comandosDisponibles = String.join(", ", estrategias.keySet());
                responseMsg.setText(" Comando no disponible.\nIntentá con alguno de los siguientes:\n" + comandosDisponibles);
            }

            enviarMensaje(responseMsg);
        }
    }

    private void enviarMensaje(SendMessage msg) {
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

    public Orden obtenerOrden(String mensaje) {
        String[] mensajeTokenizado = mensaje.split(" ");
        if (mensajeTokenizado.length == 0) return null;

        String comando = mensajeTokenizado[0].toLowerCase();
        return estrategias.get(comando);
    }



    @Override
    public String getBotUsername() {
        return System.getenv().getOrDefault("NOMBRE_BOT", "grupo10_bot");
    }

    @Override
    public String getBotToken() {
        return System.getenv().getOrDefault("TOKEN_BOT", "8485872797:AAHlGPgw3l2J1aFScxy3tddhCnPLIEm4aIM");
    }

}
