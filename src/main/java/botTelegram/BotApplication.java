package botTelegram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class BotApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BotApplication.class, args);
        System.out.println("Bot iniciado y esperando mensajes...");

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            TelegramBot bot = context.getBean(TelegramBot.class);
            botsApi.registerBot(bot);
            System.out.println("Bot registrado correctamente en Telegram.");
        } catch (TelegramApiException e) {
            System.err.println("Error al registrar el bot en Telegram: " + e.getMessage());
        }
    }
}
