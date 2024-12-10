package com.javarush.zhuravlev.params;

import java.util.Scanner;

public class IslandAreaInitialization {

    // Если вам нужен редактируемый размер острова, добавьте методы из этого класса в ISLAND_HEIGHT и ISLAND_WIDTH
    @Deprecated //Смотри аннотацию
    public class IslandAreaInitialization {
        private static Texts text = new Texts();

        // Инициализация ISLAND_HEIGHT
        public static Integer areaHeightInput() {
            Scanner scanner = new Scanner(System.in);
            int number = 0;
            boolean valid = false;

            while (!valid) {
                System.out.println(text.getAREA_HEIGHT_REQUEST());
                String input = scanner.nextLine();

                try {
                    number = Integer.parseInt(input);
                    if (number > 0) {
                        valid = true;
                    } else {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    System.out.println(text.getWRONG_INTEGER_INPUT());
                }
            }
            return number;
        }
        // Инициализация ISLAND_WIDTH
        public static Integer areaWidthInput() {
            Scanner scanner = new Scanner(System.in);
            int number = 0;
            boolean valid = false;

            while (!valid) {
                System.out.println(text.getAREA_WIDTH_REQUEST());
                String input = scanner.nextLine();

                try {
                    number = Integer.parseInt(input);

                    if(number > 0) {
                        valid = true;
                    } else {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    System.out.println(text.getWRONG_INTEGER_INPUT());
                }
            }
            return number;
        }
    }
}
