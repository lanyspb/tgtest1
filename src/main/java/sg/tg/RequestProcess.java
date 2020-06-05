package sg.tg;

public class RequestProcess {

    public RequestProcess() {
    }

// инфо по заявке
    public String getRequestInfo(Long requestId) {
        String result = "<b>Заявка №</b> " + requestId;
        result += "\n<b>Клиент</b>: ООО Рога и Копыта";
        result += "\n<b>Адрес</b>: Санкт-Петербург, ул.Садовая, д.23";
        result += "\n<b>Описание</b>: установить осветительное оборудование kenwood 2233 на 2-м этаже.";
        result += "\n<b>Дата создания</b>: 01.06.2020";
        result += "\n<b>Тек.состояние</b>: в работе у менеджера";
        return result;
    }

// история действий указанной страницы
    public String getActionHistoryPage(Long requestId, String[][] actionHistoryList, int pageId) {

        int pageSize = 5;

        String result = "<b>Стр. " + (pageId+1) + "</b>";

        // отрисовка в обратном порядке
        int startIndex = pageId * pageSize;
        int endIndex = startIndex + pageSize;
        if (endIndex > actionHistoryList.length)
            endIndex = actionHistoryList.length;

        result += " (" + (actionHistoryList.length-startIndex) + " - " + (actionHistoryList.length-endIndex+1) + ")\n";

//        if (pageId > 0)
//            result += "...\n";

        for(int i=startIndex; i < endIndex; i++) {
            result += "\n(" + (actionHistoryList.length - i) + ") ";
            result += "<u>" + actionHistoryList[i][0] + "</u>";
            result += " " + actionHistoryList[i][3];
            result += " / " + actionHistoryList[i][4];
            result += "\n        <b>" + actionHistoryList[i][1] + "</b>";
            if (actionHistoryList[i][2].length() > 0)
                result += "\n        " + actionHistoryList[i][2];
            result += "\n";
        }

//        if (endIndex < actionHistoryList.length)
//            result += "\n...";

        return result;
    }


}
