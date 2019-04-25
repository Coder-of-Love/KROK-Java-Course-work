/*
toDo:
    -Обеспечить обработку исключений, логирование, тестирование
    -Доп. задание:
        -Импорт/Экспорт таблиц с помощью аннотаций
        -Валидация таблиц
        -Чтение/Запись объектов по одному или списком
        -Очиста таблицы
        -...
);
*/
package apps;

import classes.UserInterface;

public class MainApp {
    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        ui.init();
    }
}
