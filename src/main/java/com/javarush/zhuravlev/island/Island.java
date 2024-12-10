package com.javarush.zhuravlev.island;

public class Island {
    //Размер острова по умолчанию
    private static final Integer ISLAND_HEIGHT = 80;
    private static final Integer ISLAND_WIDTH = 30;
    @Getter
    public static CellZone[][] arrayWithAllCells = new CellZone[ISLAND_HEIGHT][ISLAND_WIDTH]; //Массив територии острова

    //Метод заполняющий территорию острова в arrayWithAllCells разными экземплярами ячейки
    static void fillArrayWithCells() {
        for (int i = 0; i < arrayWithAllCells.length; i++) {
            for (int j = 0; j < arrayWithAllCells[i].length; j++) {
                arrayWithAllCells[i][j] = new CellZone(i, j);
            }
        }
    }

    public static Integer getISLAND_HEIGT() {
        return ISLAND_HEIGHT;
    }
    public static Integer getISLAND_WIDTH() {
        return ISLAND_WIDTH;
    }
}
