package com.javarush.zhuravlev.utilit;

@Getter
public class Texts {

    // Класс со всеми текстами для этого приложения
    private final String AREA_HEIGHT_REQUEST = "Пожалуйста, введите число больше 0 для обозначения высоты островной зоны!\n";
    private final String AREA_WIDTH_REQUEST = "Пожалуйста, введите число больше 0 для обозначения ширины островной зоны!\n";;
    private final String WRONG_INTEGER_INPUT = "Вы ввели неправильный символ, ноль или отрицательное число!";
    private final String FAILED_INPUT_READING = "Ошибка чтения входного потока из YAML!";
    private final String FAILED_UNPACK = "Ошибка чтения или распаковки параметров карты из YAML!";
    private final String NAME_NOT_FOUND = "Это имя объекта не найдено в файле YAML с параметрами!";
    private final String INSTANCE_ERROR = "Ошибка создания нового экземпляра!";
    private final String CONSTRUCTOR_NOT_FIND = "Неудачный доступ к конструктору!";
    private final String ANIMALS_ON_ISLAND = "Животные сейчас на острове: ";

}
