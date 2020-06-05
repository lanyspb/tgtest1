
package sg.tg;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.ActionType;
import org.telegram.telegrambots.meta.api.methods.send.SendChatAction;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import sg.tg.keyboards.ReplyKeyboards;
import sg.tg.keyboards.InlineKeyboards;

import java.util.Date;


public class MainBot extends TelegramLongPollingBot {

    String botUsername = "SoftGearTestBot";
    String botToken = "1170323770:AAEBbdmqSvvZ8-Yl5KnSsBkdYArceKoRF1E";


    // макс кол-во кнопок-заявок в инлайн-клавиатуре
    int inlinePageSize = 5;

    // основная клавиатура
    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    // объект с методами отрисовки клавиатуры
    ReplyKeyboards replyKeyboards = new ReplyKeyboards();

    // инлайн-клавиатура
    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
    // объекты-клавиатуры
    InlineKeyboards inlineKeyboards = new InlineKeyboards(inlinePageSize);

    // методы работы с заявками
    RequestProcess req = new RequestProcess();

    Integer currentInlineMessageId;


    private static SendMessage sendMessage = new SendMessage();
    private static EditMessageText editMessageText = new EditMessageText();
    SendChatAction sendChatAction = new SendChatAction();

    Long chatId = null;
    String contactPhone = null;
    // последняя выбранная заявка
    Long currentRequestId = null;
    // флаг ожидания ввода данных
    String fixInput = "";
    String[] newRequest = new String[3];

    // список заявок в работе
    private static Long[] requestList;
    // список выполненных действий по заявке
    private static String actionHistoryList[][];
    // список доступных действий по заявке
    private static String actionAwayList[];


    public static void main(String[] args) {
        ApiContextInitializer.init(); // Инициализируем апи
        TelegramBotsApi botapi = new TelegramBotsApi();
        try {
            botapi.registerBot(new MainBot());
            init();
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    private static void init() {
        clearSendMessage();
        clearExitMessageText();

        requestList = new Long[]{500150L, 5463453L, 556789L, 678676L, 689889L, 699998L, 726252L, 734332L, 734567L, 500150L, 5463453L, 556789L, 678676L, 689889L, 699998L, 726252L, 734332L, 734567L, 500150L, 5463453L, 556789L, 678676L, 689889L, 699998L, 726252L, 734332L, 734567L, 500150L, 5463453L, 556789L, 678676L, 689889L, 699998L, 726252L, 734332L, 734567L,};
//        Long[] requestList = {500150L, 5463453L, 556789L};
//        Long[] requestList = {1l, 2l, 3l, 4l, 5l, 6l, 7l, 8l, 9l, 10l, 11l, 12l, 13l, 14l, 15l, 16l, 17l, 18l, 19l, 20l, 21l, 22l, 23l, 24l, 25l, 26l, 27l, 28l, 29l, 30l, 31l, 32l, 33l, 34l, 35l, 36l, 37l, 38l, 39l, 40l, 41l, 42l, 43l, 44l, 45l, 46l, 47l, 48l, 49l, 50l, 51l};

        actionHistoryList = new String[][] {
                {"14.09.2020 9:23", "Выезд бригады", "", "Инженер", "Петров П.П."},
                {"13.09.2020 15:22", "Подтверждение установки", "14.09.2020 15:00", "Инженер", "Петров П.П."},
                {"13.09.2020 15:20", "Бронирование времени установки", "на завтра", "Инженер", "Петров П.П."},
                {"13.09.2020 15:12", "Приемка инженером", "", "Инженер", "Петров П.П."},
                {"13.09.2020 15:11", "Передача выездному инженеру", "", "Менеджер", "Иванов И.И."},
                {"13.09.2020 15:11", "Распечатка накладной", "№23", "Менеджер", "Иванов И.И."},
                {"13.09.2020 15:11", "Распечатка документов", "договор в комплекте", "Менеджер", "Иванов И.И."},
                {"13.09.2020 15:10", "Получение упаковки", "гофрокартон 3 коробки", "Менеджер", "Иванов И.И."},
                {"13.09.2020 15:00", "Заказ упаковки", "объем №4", "Менеджер", "Иванов И.И."},
                {"13.09.2020 11:00", "Проверка комплектности", "полный комплект", "Менеджер", "Иванов И.И."},
                {"12.09.2020 16:00", "Получение заказа в офисе", "доставлено", "Менеджер", "Иванов И.И."},
                {"10.09.2020 15:30", "Контроль выполнения заказа", "в обработке", "Менеджер", "Иванов И.И."},
                {"10.09.2020 15:01", "Заказ доставки со склада в офис", "заказ №234", "Менеджер", "Иванов И.И."},
                {"10.09.2020 15:01", "Проверка наличия товара", "есть на складе", "Менеджер", "Иванов И.И."},
                {"10.09.2020 15:00", "Уточнение адреса", "всё корректно", "Менеджер", "Иванов И.И."},
                {"10.09.2020 14:20", "Создание заявки", "", "Менеджер", "Иванов И.И."},
        };

        actionAwayList = new String[] {
            "Выполнение работ",
            "Перенос выполнения работ",
            "Отказ клиента",
            "Заказ дополнительного оборудования",
            "Передача другому исполнителю",
            "Внесение комментария",
            "Прикрепление файла"
        };

    }


    public static void clearSendMessage() {
        sendMessage = new SendMessage();
        sendMessage.enableMarkdown(false);
        sendMessage.enableHtml(true);
    }

    public static void clearExitMessageText() {
        editMessageText = new EditMessageText();
        editMessageText.enableMarkdown(false);
        editMessageText.enableHtml(true);
    }

    public void sendMsg(Long chatId, String text) {
//        SendMessage sendMessage = new SendMessage();

        sendMessage.setChatId(chatId);
        sendMessage.setText(text);

        try {
            execute(sendMessage);
        } catch (Exception ex) {
            System.out.println("error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void sendTyping(Long chatId) {

        sendChatAction.setChatId(chatId);
        sendChatAction.setAction(ActionType.TYPING);

        try {
            execute(sendChatAction);
        } catch (Exception ex) {
            System.out.println("error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }



    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage()) {
                Message msg = update.getMessage();

// Если получен контакт - сохраняем данные чата и инициализируем стартовое меню
                if (msg.getContact() != null && msg.getContact().getPhoneNumber() != null) {
                    this.chatId = msg.getChatId();
                    this.contactPhone = msg.getContact().getPhoneNumber();
                    sendMessage.setReplyMarkup(replyKeyboards.getTopMenuReplyKeyboard(replyKeyboardMarkup));
                    sendMsg(this.chatId, "Выберите режим работы с помощью меню");
                }

// Если нет инициализации - показываем меню регистрации
                if (this.chatId == null) {
                    sendMessage.setReplyMarkup(replyKeyboards.getLoginReplyKeyboard(replyKeyboardMarkup));
                    sendMsg(msg.getChatId(), "Зарегистирируйтесь");
                } else {


                    if (msg.hasText()) {


                        boolean isCommand = false;

                        // Обработка сообщений от пользователя (от меню)

                        // Главное меню
                        // "Выход"
                        if (replyKeyboards.exitLabel.equals(msg.getText())) {
                            this.contactPhone = null;
                            this.chatId = null;
                            sendMessage.setReplyMarkup(replyKeyboards.getLoginReplyKeyboard(replyKeyboardMarkup));
                            sendMsg(msg.getChatId(), "Зарегистирируйтесь");
                            isCommand = true;
                        }
                        // "Новая заявка"
                        if (replyKeyboards.newRequestLabel.equals(msg.getText())) {
                            sendMessage.setReplyMarkup(replyKeyboards.getToTopMenuReplyKeyboard(replyKeyboardMarkup));
                            sendMsg(msg.getChatId(), "Введите название клиента");
                            this.fixInput = "client";
                            isCommand = true;
                        }

                        // "В работе"
                        if (replyKeyboards.inWorkLabel.equals(msg.getText()) || replyKeyboards.toInWorkMenu.equals(msg.getText())) {
                            sendMessage.setReplyMarkup(replyKeyboards.getInWorkReplyKeyboard(replyKeyboardMarkup, requestList.length));
                            sendMsg(msg.getChatId(), "Какие заявки показать?");
                            isCommand = true;
                        }

                        // Обработка
                        // В главное меню
                        if (replyKeyboards.toHomeMenu.equals(msg.getText())) {
                            this.fixInput = "";
                            sendMessage.setReplyMarkup(replyKeyboards.getTopMenuReplyKeyboard(replyKeyboardMarkup));
                            sendMsg(this.chatId, "Выберите режим работы с помощью меню");
                            isCommand = true;
                        }

                        // Назначенне
                        if (replyKeyboards.awardRequestLabel.equals(msg.getText())) {

                            sendTyping(msg.getChatId());
                            try {

                                // назначаем типа первую из списка, якобы она была не назначена
                                this.currentRequestId = requestList[0];
                                // вызвать меню обработки заявки
                                execute(
                                        sendMessage
                                                .setReplyMarkup(replyKeyboards.getRequestMenuReplyKeyboard(replyKeyboardMarkup))
                                                .setText(req.getRequestInfo(this.currentRequestId))
                                );

                                isCommand = true;

                            } catch (Exception ex) {
                            }

                            isCommand = true;
                        }

                        // В работе
                        if (msg.getText().startsWith(replyKeyboards.myRequestLabel)) {
                            // список заявок через инлайн-клавиатуру
                            execute(
                                    sendMessage
                                            .setChatId(chatId)
                                            .setReplyMarkup(inlineKeyboards.getRequestListInlineKeyboard(inlineKeyboardMarkup, requestList, 0))
                                            .setText("В работе заявок: " + requestList.length)
                            );
                            this.currentInlineMessageId = msg.getMessageId();
                            inlineKeyboardMarkup.getKeyboard().clear();
                            isCommand = true;
                        }


                        // Заявка
                        // к меню выбранной заявки
                        if (replyKeyboards.toRequestMenu.equals(msg.getText())) {

                            // вызвать меню обработки заявки
                            execute(
                                    sendMessage
                                            .setReplyMarkup(replyKeyboards.getRequestMenuReplyKeyboard(replyKeyboardMarkup))
                                            .setText(req.getRequestInfo(this.currentRequestId))
                            );

                            isCommand = true;
                        }

                        // История
                        if (replyKeyboards.requestHistoryActionsLabel.equals(msg.getText())) {

                            // основную клавиатуру меняем на "Назад"
                            execute(
                                    sendMessage
                                            .setChatId(this.chatId)
                                            .setReplyMarkup(replyKeyboards.getBackToRequestMenuReplyKeyboard(replyKeyboardMarkup))
                                            .setText("В заявке <b>" + currentRequestId + "</b> выполено действий: " + actionHistoryList.length)
                            );

                            // список выполненных действий через инлайн-клавиатуру
                            execute(
                                    sendMessage
                                            .setChatId(chatId)
                                            .setReplyMarkup(inlineKeyboards.getHistoryActionListInlineKeyboard(inlineKeyboardMarkup, actionHistoryList, 0))
                                            .setText(req.getActionHistoryPage(currentRequestId, actionHistoryList, 0))
                            );
                            this.currentInlineMessageId = msg.getMessageId();
                            inlineKeyboardMarkup.getKeyboard().clear();
                            isCommand = true;
                        }


                        // Выполнить
                        if (replyKeyboards.requestAwayActionsLabel.equals(msg.getText())) {
                            // основную клавиатуру меняем на "Назад"
                            execute(
                                    sendMessage
                                            .setReplyMarkup(replyKeyboards.getBackToRequestMenuReplyKeyboard(replyKeyboardMarkup))
                                            .setText("Доступные действия по заявке <b>" + currentRequestId + "</b>:")
                            );

                            // список доступных действий через инлайн-клавиатуру
                            execute(
                                    sendMessage
                                            .setReplyMarkup(inlineKeyboards.getAwayActionListInlineKeyboard(inlineKeyboardMarkup, actionAwayList, 0))
                                            .setText("text")
                            );
                            this.currentInlineMessageId = msg.getMessageId();
                            inlineKeyboardMarkup.getKeyboard().clear();

                            isCommand = true;
                        }


// если есть ожидание ввода
                        if (!isCommand && fixInput.length() > 0) {

                            if ("description".equals(fixInput)) {
                                this.newRequest[2] = msg.getText();

                                // добавляе новую заявку в конец
                                Long[] newRequestList = new Long[requestList.length + 1];
                                for (int i = 0; i < requestList.length; i++) {
                                    newRequestList[i] = requestList[i];
                                }
                                newRequestList[requestList.length] = requestList[requestList.length - 1] + 1;
                                requestList = newRequestList;

                                this.currentRequestId = requestList[requestList.length - 1];

                                sendMsg(msg.getChatId(), "Создана заявка № <b>" + this.currentRequestId + "</b>");

                                // вызвать меню обработки заявки
                                execute(
                                        sendMessage
                                                .setReplyMarkup(replyKeyboards.getRequestMenuReplyKeyboard(replyKeyboardMarkup))
                                                .setText(req.getRequestInfo(this.currentRequestId))
                                );
                                // сброс ожидания ввода
                                this.fixInput = "";
                            }
                            if ("address".equals(fixInput)) {
                                this.newRequest[1] = msg.getText();
                                sendMsg(msg.getChatId(), "Теперь описание");
                                this.fixInput = "description";
                            }
                            if ("client".equals(fixInput)) {
                                this.newRequest[0] = msg.getText();
                                sendMsg(msg.getChatId(), "Введите адрес");
                                this.fixInput = "address";
                            }

                            isCommand = true;

                        }


// может напрямую ввели номер заявки
                        if (!isCommand) {
                            try {
                                this.currentRequestId = new Long(msg.getText());
                                for (Long request : requestList) {
                                    if (request.intValue() == this.currentRequestId.intValue()) {
                                        // вызвать меню обработки заявки
                                        execute(
                                                sendMessage
                                                        .setReplyMarkup(replyKeyboards.getRequestMenuReplyKeyboard(replyKeyboardMarkup))
                                                        .setText(req.getRequestInfo(this.currentRequestId))
                                        );

                                        isCommand = true;

                                        break;
                                    }
                                }
                            } catch (Exception ex) {
                            }

                        }





// команда не распознана
                        if (!isCommand) {
                            System.out.println("Команда не распознана: " + msg.getText());
                            sendMsg(msg.getChatId(), "Команда не распознана: " + msg.getText());
                        }






                    }
                }

            } else {


// сообщения от инлайн-клавиатур
                if (update.hasCallbackQuery()) {

                    Long currentChatId = update.getCallbackQuery().getMessage().getChatId();
                    Integer currentMessageId = update.getCallbackQuery().getMessage().getMessageId();

                    String[] command = update.getCallbackQuery().getData().split(":");

                    if (command.length > 0) {

System.out.println("command: " + update.getCallbackQuery().getData());

////////////////////////////////
// RequestList
                        if (command[0].equals("RequestList")) {
                            // формат команды: ("requestList":"page"/requestId:pageId)

                                                if (command.length == 3 && command[1].equals("page")) {
                                                    // если сообщение от пагинатора - перерисовываем меню на указанную страницу
                                                    int page = new Long(command[2]).intValue();

                                                    try {
                                                        execute(
                                                                editMessageText
                                                                        .setChatId(currentChatId)
                                                                        .setMessageId(currentMessageId)
                                                                        .setReplyMarkup(inlineKeyboards.getRequestListInlineKeyboard(inlineKeyboardMarkup, requestList, page))
                                                                        .setText("В работе заявок: " + requestList.length + "\nСтр. " + (page + 1))
                                                        );
                                                        inlineKeyboardMarkup.getKeyboard().clear();

                                                    } catch (Exception ex) {
                                                        System.out.println("Ничего нового не выбрано");
                                                    }

                                                } else {
                                                    // если выбрана заявка:
                                                    this.currentRequestId = new Long(command[1]);

                                                    // стереть клавиатуру
                                                    inlineKeyboardMarkup.getKeyboard().clear();
                                                    execute(
                                                            editMessageText
                                                                    .setChatId(currentChatId)
                                                                    .setMessageId(currentMessageId)
                                                                    .setReplyMarkup(inlineKeyboardMarkup)
                                                                    .setText("Выбрана заявка № <b>" + this.currentRequestId + "</b>")
                                                    );

                                                    // вызвать меню обработки заявки
                                                    sendMessage.setReplyMarkup(replyKeyboards.getRequestMenuReplyKeyboard(replyKeyboardMarkup));

                                                    // показать данные по заявке
                                                    sendMsg(update.getCallbackQuery().getMessage().getChatId(), req.getRequestInfo(this.currentRequestId));

                                                }
                        }
// RequestList
////////////////////////////////

////////////////////////////////
// ActionHistoryList
                        if (command[0].equals("ActionHistoryList")) {
                            // формат команды: ("ActionHistoryList":"index":index)

                            if (command.length == 3 && command[1].equals("index")) {
                                // если сообщение от пагинатора - перерисовываем меню на указанную страницу
                                int page = new Long(command[2]).intValue();
                                try {
                                    execute(
                                            editMessageText
                                                    .setChatId(currentChatId)
                                                    .setMessageId(currentMessageId)
                                                    .setReplyMarkup(inlineKeyboards.getHistoryActionListInlineKeyboard(inlineKeyboardMarkup, actionHistoryList, page))
                                                    .setText(req.getActionHistoryPage(currentRequestId, actionHistoryList, page))
                                    );
                                    inlineKeyboardMarkup.getKeyboard().clear();

                                } catch (Exception ex) {
//                                    System.out.println("Ничего нового не выбрано");
                                    ex.printStackTrace();
                                }
                            }
                        }
// ActionHistoryList
////////////////////////////////

////////////////////////////////
// ActionAwayList
                        if (command[0].equals("ActionAwayList")) {
                            // формат команды: ("ActionHistoryList":"index"/"action"/"exit":indexId/actionId)

                            if (command.length == 3 && command[1].equals("index")) {
                                // если сообщение от пагинатора - перерисовываем меню на указанную страницу
                                int page = new Long(command[2]).intValue();
                                try {
                                    execute(
                                            editMessageText
                                                    .setChatId(currentChatId)
                                                    .setMessageId(currentMessageId)
                                                    .setReplyMarkup(inlineKeyboards.getAwayActionListInlineKeyboard(inlineKeyboardMarkup, actionAwayList, page))
                                                    .setText("Доступных действий: " + actionAwayList.length + "\nСтр. " + (page + 1))
                                    );
                                    inlineKeyboardMarkup.getKeyboard().clear();

                                } catch (Exception ex) {
//                                    System.out.println("Ничего нового не выбрано");
                                    ex.printStackTrace();
                                }
                            }
                            if (command.length == 3 && command[1].equals("action")) {
                                // если выбрано действие - выполняем его
                                int action = new Long(command[2]).intValue();
                                // стираем клавиатуру
                                inlineKeyboardMarkup.getKeyboard().clear();
                                execute(
                                        editMessageText
                                                .setChatId(currentChatId)
                                                .setMessageId(currentMessageId)
                                                .setReplyMarkup(inlineKeyboardMarkup)
                                                .setText("...")
                                );

                                // вызвать меню обработки заявки
                                execute(
                                    sendMessage
                                            .setReplyMarkup(replyKeyboards.getRequestMenuReplyKeyboard(replyKeyboardMarkup))
                                            .setText("Выполнено действие: <b>" + actionAwayList[action] + "</b>")
                                );

                                // добавляем выполненное действие в начало
                                String newActionHistoryList[][] = new String[actionHistoryList.length+1][5];
                                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm");
                                Date date = new Date();
                                newActionHistoryList[0] = new String[] {sdf.format(date), actionAwayList[action], "", "Инженер", "Петров П.П."};
                                for (int i=0; i< actionHistoryList.length; i++) {
                                    newActionHistoryList[i+1] = actionHistoryList[i];
                                }
                                actionHistoryList = newActionHistoryList;
                            }
                        }
// ActionAwayList
////////////////////////////////


                    }


                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }




}
