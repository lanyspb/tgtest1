package sg.tg;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Bot extends TelegramLongPollingBot {

    String botUsername;
    String botToken;


    public Bot(String botUsername, String bBotToken) {
        this.botUsername = botUsername;
        this.botToken = botToken;


    }

    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }



    public void sendMsg(Long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableMarkdown(false);
        sendMessage.setText(text);

        try {
            execute(sendMessage);
        } catch (Exception ex) {
            System.out.println("error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {

            Message msg = update.getMessage();

            if (msg.hasText()) {

                sendMsg(msg.getChatId(), msg.getText());

            } else {
                if (msg.getContact() != null && msg.getContact().getPhoneNumber() != null)
                    sendMsg(msg.getChatId(), "phone: " + msg.getContact().getPhoneNumber());
                if (msg.getLocation() != null)
                    sendMsg(msg.getChatId(), "location: " + msg.getLocation().toString());
            }

        } else {
            if (update.hasCallbackQuery()) {

                sendMsg(update.getCallbackQuery().getMessage().getChatId(), "Callback: " + update.getCallbackQuery().getData());

            }
        }
    }

}
