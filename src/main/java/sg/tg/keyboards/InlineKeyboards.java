package sg.tg.keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineKeyboards {

    public int inlinePageSize = 5;

    public InlineKeyboards(int inlinePageSize) {
        this.inlinePageSize = inlinePageSize;
    }

// клавиатура выбора заявки из списка с пагинатором
    public InlineKeyboardMarkup getRequestListInlineKeyboard(InlineKeyboardMarkup inlineKeyboardMarkup, Long[] requestList, int page) {
        String command = "RequestList";

        if (requestList.length > 0) {
            List<List<InlineKeyboardButton>> rowList = new ArrayList<List<InlineKeyboardButton>>();

            // если заявок больше: чем помещается на страницу - нужно добавить строку пагинатора
            if (requestList.length > this.inlinePageSize) {
                List<InlineKeyboardButton> paginatorRow = new ArrayList<InlineKeyboardButton>();

                // сколько всего страниц
                int pageCount = (requestList.length / this.inlinePageSize * this.inlinePageSize == requestList.length)
                        ? requestList.length / this.inlinePageSize
                        : requestList.length / this.inlinePageSize + 1;

                // какая страница начальная в пагинаторе, с учетом переданной текущей страницы
                int startPage = page / this.inlinePageSize * this.inlinePageSize;

                // строим пагинатор
                InlineKeyboardButton buttonPage;
                for (int i = startPage; i < pageCount; i++) {
                    buttonPage = new InlineKeyboardButton();

                    // если для отображаемых текущих страниц уже достигнут максимум, а страницы еще остались - делаем кнопку перемотки >> и останавливаемся
                    if (paginatorRow.size() == 5) {
                        buttonPage.setText(">>").setCallbackData(command + ":page:" + i);
                        paginatorRow.add(buttonPage);
                        break;
                    }

                    // сам пагинатор
                    int start = i * this.inlinePageSize + 1;
                    int end = i * this.inlinePageSize + this.inlinePageSize;
                    if (end > requestList.length)
                        end = requestList.length;

                    String prefix = "";
                    if (i == page)
                        prefix = "* ";
                    buttonPage.setText(prefix + start + "-" + end).setCallbackData(command + ":page:" + i);
                    paginatorRow.add(buttonPage);

                }

                // если текущая страница не "из первых" - добавляем перемотку назад
                if (startPage > 0) {
                    buttonPage = new InlineKeyboardButton();
                    buttonPage.setText("<<").setCallbackData(command + ":page:" + (startPage - 1));
                    paginatorRow.add(0, buttonPage);
                }

                rowList.add(paginatorRow);
            }

            // список заявок
            List<InlineKeyboardButton> keyboardRequestRow = new ArrayList<InlineKeyboardButton>();

            for (int i = page * this.inlinePageSize; i < requestList.length; i++) {

                Long request = requestList[i];

                InlineKeyboardButton buttonReq = new InlineKeyboardButton();
                buttonReq.setText("" + request).setCallbackData(command + ":" + request);
                keyboardRequestRow.add(buttonReq);

                // если число кнопок-заявок достигло максимального числа для инлайн-клавиатуры - останавливаемся
                if (keyboardRequestRow.size() == this.inlinePageSize) {
                    break;
                }
            }
            rowList.add(keyboardRequestRow);

            inlineKeyboardMarkup.setKeyboard(rowList);
            return inlineKeyboardMarkup;
        } else {
            return null;
        }
    }

// клавиатура просмотра истории выполненных действий с перемоткой
    public InlineKeyboardMarkup getHistoryActionListInlineKeyboard(InlineKeyboardMarkup inlineKeyboardMarkup, String[][] actionList, int page) {
        String command = "ActionHistoryList";

        if (actionList.length > inlinePageSize) {

            List<List<InlineKeyboardButton>> rowList = new ArrayList<List<InlineKeyboardButton>>();
            List<InlineKeyboardButton> paginatorRow = new ArrayList<InlineKeyboardButton>();

            // строим пагинатор
            InlineKeyboardButton buttonPage;

            // если текущее действие не первое - добавляем перемотку назад
            if (page > 0) {
                buttonPage = new InlineKeyboardButton();
                buttonPage.setText("< Стр. " + (page)).setCallbackData(command + ":index:" + (page-1));
                paginatorRow.add(buttonPage);
            }

            // если текущее действие не первое - добавляем перемотку назад
            if (page < actionList.length / inlinePageSize) {
                buttonPage = new InlineKeyboardButton();
                buttonPage.setText("Стр. " + (page+2) + " >").setCallbackData(command + ":index:" + (page+1));
                paginatorRow.add(buttonPage);
            }

            rowList.add(paginatorRow);

            inlineKeyboardMarkup.setKeyboard(rowList);
            return inlineKeyboardMarkup;

        } else {
            return null;
        }

    }


    // клавиатура просмотра списка доступных действий с перемоткой
    public InlineKeyboardMarkup getAwayActionListInlineKeyboard(InlineKeyboardMarkup inlineKeyboardMarkup, String[] actionList, int page) {
        String command = "ActionAwayList";

        if (actionList.length > inlinePageSize) {

            List<List<InlineKeyboardButton>> rowList = new ArrayList<List<InlineKeyboardButton>>();

            int startIndex = page * inlinePageSize;
            int endIndex = startIndex + inlinePageSize;
            if (endIndex >= actionList.length)
                endIndex = actionList.length;


            for (int i=startIndex; i < endIndex; i++) {
                InlineKeyboardButton button = new InlineKeyboardButton();
                button
                        .setText(actionList[i])
                        .setCallbackData(command + ":action:" + i);
                List<InlineKeyboardButton> buttonRow = new ArrayList<InlineKeyboardButton>();
                buttonRow.add(button);
                rowList.add(buttonRow);
            }

            // строим пагинатор
            List<InlineKeyboardButton> paginatorRow = new ArrayList<InlineKeyboardButton>();
            InlineKeyboardButton buttonPage;

            // если текущее действие не первое - добавляем перемотку назад
            if (page > 0) {
                buttonPage = new InlineKeyboardButton();
                buttonPage
                        .setText("< Стр. " + (page))
                        .setCallbackData(command + ":index:" + (page-1));
                paginatorRow.add(buttonPage);
            }

            // если текущее действие не первое - добавляем перемотку назад
            if (page < actionList.length / inlinePageSize) {
                buttonPage = new InlineKeyboardButton();
                buttonPage
                        .setText("Стр. " + (page+2) + " >")
                        .setCallbackData(command + ":index:" + (page+1));
                paginatorRow.add(buttonPage);
            }

            rowList.add(paginatorRow);

            inlineKeyboardMarkup.setKeyboard(rowList);
            return inlineKeyboardMarkup;

        } else {
            return null;
        }

    }


}
