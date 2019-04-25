/*
toDo:
    -Обеспечить логирование, тестирование
    -Доп. задание:
        -Импорт/Экспорт таблиц с помощью аннотаций
        -Валидация таблиц
        -Очистка таблицы
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
