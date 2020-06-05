package sg.tg.keyboards;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class ReplyKeyboards {

// Регистрация
    public String loginLabel = EmojiParser.parseToUnicode(":airplane:") + " Регистрация (отправка номера телефона)";

// Главное меню
    public String newRequestLabel = EmojiParser.parseToUnicode(":heavy_plus_sign:") + " Новая заявка";
    public String inWorkLabel = EmojiParser.parseToUnicode(":construction_worker:") + " Обработка";
    public String exitLabel = EmojiParser.parseToUnicode(":door:") + " Выход";

// Обработка
    public String awardRequestLabel = EmojiParser.parseToUnicode(":file_folder:") + " Назначенные";
    public String myRequestLabel = EmojiParser.parseToUnicode(":open_file_folder:") + " В работе";
    public String toHomeMenu = EmojiParser.parseToUnicode(":triangular_flag_on_post:") + " Главное меню";

// Работа с заявкой
    public String requestHistoryActionsLabel = EmojiParser.parseToUnicode(":lock:") + " История";
    public String requestAwayActionsLabel = EmojiParser.parseToUnicode(":hammer:") + " Обработать";
    public String toInWorkMenu = EmojiParser.parseToUnicode(":construction_worker:") + " Назад";
    public String toRequestMenu = EmojiParser.parseToUnicode(":construction_worker:") + " К меню заявки";

// кнопка логина
    public ReplyKeyboardMarkup getLoginReplyKeyboard(ReplyKeyboardMarkup replyKeyboardMarkup) {

        replyKeyboardMarkup.setOneTimeKeyboard(false); // если true - после отправки сообщения кнопка пропадет, иначе - останется
        replyKeyboardMarkup.setResizeKeyboard(true); // если true - кнопка будет маленькой, иначе большой

        List<KeyboardRow> keyboardRowList = new ArrayList<KeyboardRow>();
        KeyboardRow row = new KeyboardRow();

        KeyboardButton button = new KeyboardButton();
        button.setText(loginLabel);
        button.setRequestContact(true);
        button.setRequestLocation(false);
        row.add(button);

        keyboardRowList.add(row);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

        return replyKeyboardMarkup;
    }

// главное меню - новую заявку или обработку
    public ReplyKeyboardMarkup getTopMenuReplyKeyboard(ReplyKeyboardMarkup replyKeyboardMarkup) {

        replyKeyboardMarkup.setOneTimeKeyboard(false); // если true - после отправки сообщения кнопка пропадет, иначе - останется
        replyKeyboardMarkup.setResizeKeyboard(true); // если true - кнопка будет маленькой, иначе большой

        List<KeyboardRow> keyboardRowList = new ArrayList<KeyboardRow>();
        KeyboardRow row = new KeyboardRow();

        // новая заявка
        KeyboardButton button = new KeyboardButton();
        button.setText(newRequestLabel);
        row.add(button);

        // в работе
        KeyboardButton button2 = new KeyboardButton();
        button2.setText(inWorkLabel);
        row.add(button2);

        // Выход
        KeyboardButton button4 = new KeyboardButton();
        button4.setText(exitLabel);
        row.add(button4);

        keyboardRowList.add(row);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        return replyKeyboardMarkup;

    }

// меню если выбрана обработка - назначенные или в работе
    public ReplyKeyboardMarkup getInWorkReplyKeyboard(ReplyKeyboardMarkup replyKeyboardMarkup, int count) {

        replyKeyboardMarkup.setOneTimeKeyboard(false); // если true - после отправки сообщения кнопка пропадет, иначе - останется
        replyKeyboardMarkup.setResizeKeyboard(true); // если true - кнопка будет маленькой, иначе большой

        List<KeyboardRow> keyboardRowList = new ArrayList<KeyboardRow>();
        KeyboardRow row = new KeyboardRow();

        KeyboardButton button1 = new KeyboardButton();
        button1.setText(awardRequestLabel);
        row.add(button1);

        KeyboardButton button2 = new KeyboardButton();
        button2.setText(myRequestLabel + " (" + count + ")");
        row.add(button2);

        KeyboardButton button3 = new KeyboardButton();
        button3.setText(toHomeMenu);
        row.add(button3);

        keyboardRowList.add(0, row);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

        return replyKeyboardMarkup;
    }

// меню если выбрано создание новой заявки - только в главное
    public ReplyKeyboardMarkup getToTopMenuReplyKeyboard(ReplyKeyboardMarkup replyKeyboardMarkup) {

        replyKeyboardMarkup.setOneTimeKeyboard(false); // если true - после отправки сообщения кнопка пропадет, иначе - останется
        replyKeyboardMarkup.setResizeKeyboard(true); // если true - кнопка будет маленькой, иначе большой

        List<KeyboardRow> keyboardRowList = new ArrayList<KeyboardRow>();
        KeyboardRow row = new KeyboardRow();

        KeyboardButton button3 = new KeyboardButton();
        button3.setText(toHomeMenu);
        row.add(button3);

        keyboardRowList.add(0, row);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        return replyKeyboardMarkup;
    }

// меню если выбрана заявка для обработки
    public ReplyKeyboardMarkup getRequestMenuReplyKeyboard(ReplyKeyboardMarkup replyKeyboardMarkup) {

        replyKeyboardMarkup.setOneTimeKeyboard(false); // если true - после отправки сообщения кнопка пропадет, иначе - останется
        replyKeyboardMarkup.setResizeKeyboard(true); // если true - кнопка будет маленькой, иначе большой

        List<KeyboardRow> keyboardRowList = new ArrayList<KeyboardRow>();
        KeyboardRow row = new KeyboardRow();

        KeyboardButton button1 = new KeyboardButton();
        button1.setText(requestHistoryActionsLabel);
        row.add(button1);

        KeyboardButton button2 = new KeyboardButton();
        button2.setText(requestAwayActionsLabel);
        row.add(button2);

        KeyboardButton button3 = new KeyboardButton();
        button3.setText(toInWorkMenu);
        row.add(button3);

        keyboardRowList.add(0, row);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

        return replyKeyboardMarkup;
    }

// в обрабтке заявки на истории и на выполнении доступна кнопка "Назад"
    public ReplyKeyboardMarkup getBackToRequestMenuReplyKeyboard(ReplyKeyboardMarkup replyKeyboardMarkup) {

        replyKeyboardMarkup.setOneTimeKeyboard(false); // если true - после отправки сообщения кнопка пропадет, иначе - останется
        replyKeyboardMarkup.setResizeKeyboard(true); // если true - кнопка будет маленькой, иначе большой

        List<KeyboardRow> keyboardRowList = new ArrayList<KeyboardRow>();
        KeyboardRow row = new KeyboardRow();

        KeyboardButton button3 = new KeyboardButton();
        button3.setText(toRequestMenu);
        row.add(button3);

        keyboardRowList.add(0, row);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

        return replyKeyboardMarkup;
    }

}
