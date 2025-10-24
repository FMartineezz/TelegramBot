package botTelegram;

import botTelegram.estrategias.Orden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Map;

@Service
@SuppressWarnings("deprecation")
public class TelegramBot extends TelegramLongPollingBot {
    @Autowired
    private Map<String, Orden> estrategias;

    @Override
    public void onUpdateReceived(Update update) {
           final String messageTextReceived = update.getMessage().getText();
           Long chatId = update.getMessage().getChatId();

           Orden orden = obtenerOrden(messageTextReceived);

           SendMessage responseMsg = new SendMessage();
           responseMsg.setChatId(chatId);

           if(orden != null) {
              String respuesta = orden.procesarMensaje(messageTextReceived);
              responseMsg.setText(respuesta);
           }else{
              String comandosDisponibles = String.join(", ",estrategias.keySet()) ;
              responseMsg.setText("comando no disponible, intente con alguno de los siguientes: " +comandosDisponibles);
           }

           try {
              execute(responseMsg);
           } catch (TelegramApiException e) {
              throw new RuntimeException(e);
           }

    }

    public Orden obtenerOrden(String mensaje) {
           String[] mensajeTokenizado = mensaje.split(" ");
           String comando = mensajeTokenizado[0];
           return estrategias.get(comando);
    }



    @Override
    public String getBotUsername() {
        return System.getenv().getOrDefault("NOMBRE_BOT", "MiBotDEV_bot");
    }

    @Override
    public String getBotToken() {
        return System.getenv().getOrDefault("TOKEN_BOT", "7601802023:AAH8YtUqv0_J7RgppmGpuEE_SuupkaJ52WM");
    }

}
