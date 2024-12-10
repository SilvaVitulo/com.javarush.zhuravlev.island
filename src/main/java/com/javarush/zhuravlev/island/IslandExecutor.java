package com.javarush.zhuravlev.island;

import com.javarush.zhuravlev.entities.Animal;
import com.javarush.zhuravlev.entities.Plant;
import com.javarush.zhuravlev.utilit.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class IslandExecutor extends Thread {
    private static Text text = new Text();
    private static final int POOL_SIZE = 5;
    private static final int SCHEDULED_POOL_SIZE = 1;
    private static final int SIMULATION_DAYS = 100; //кол-во дней симуляции

    /* Метод execute для всех животных методов из класса Animal
    * Scheduled executor используется для печати статистики за каждый день из 100
    * и добавления дополнительных растений во все зоны / ячейки.
    */
    public void runIsland() {
        // Инициализация острова
        Island.fillArrayWithCells();
        CellZone[][] arrayWithAllCells = Island.arrayWithAllCells;

        //Использование executors
        ExecutorService executor = Executors.newFixedThreadPool(POOL_SIZE);
        ScheduledExecutorService scheduledExecuror = Executors.newScheduledThreadPool(SCHEDULED_POOL_SIZE);

        // Статистика животных
        for (int day = 1; day <= SIMULATION_DAYS; day++) {
            AtomicInteger integer = new AtomicInteger(day);
            Map<String, Integer> entitylStatistics = new HashMap<>();
            for (int i = 0; i < arrayWithAllCells.length; i++) {
                for (int j = 0; j < arrayWithAllCells[i].length; j++) {
                    for (Animal animal : arrayWithAllCells[i][j].getAnimalsListOnCell()) {
                        String animalClassName = animal.emoji + " " + animal.getClass().getSimpleName().toLowerCase() + " ";
                        if (entitylStatistics.containsKey(animalClassName)) {
                            entitylStatistics.put(animalClassName, entitylStatistics.get(animalClassName) + 1);
                        } else {
                            entitylStatistics.put(animalClassName, 1);
                        }
                    }
                }
            }

            // Статистика по растениям
            for (int i = 0; i < arrayWithAllCells.length; i++) {
                for (int j = 0; j < arrayWithAllCells[i].length; j++) {
                    for (Plant plant : arrayWithAllCells[i][j].plantsListOnCell) {
                        String plantsCount = plant.emoji + " " + plant.getClass().getSimpleName().toLowerCase() + " ";
                        if (entitylStatistics.containsKey(plantsCount)) {
                            entitylStatistics.put(plantsCount, entitylStatistics.get(plantsCount) + 1);
                        } else {
                            entitylStatistics.put(plantsCount, 1);
                        }
                    }
                }
            }

            // Финальный вывод на консоль статистики
            String result = entitylStatistics.entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(Collectors.joining(", "));

            Runnable task = () -> {
                System.out.println("DAY " + integer);
                System.out.println(text.getANIMALS_ON_ISLAND() + result);
                for (int i = 0; i < arrayWithAllCells.length; i++) {
                    for (int j = 0; j < arrayWithAllCells[i].length; j++) {
                        arrayWithAllCells[i][j].addPlantsOnCell();
                    }
                }
            };

            //Executor для задачи
            scheduledExecuror.scheduleAtFixedRate(task, 0, 100, TimeUnit.SECONDS);

            //Executor для острова
            for (int i = 0; i < arrayWithAllCells.length; i++) {
                for (int j = 0; j < arrayWithAllCells[i].length; j++) {
                    executor.execute(arrayWithAllCells[i][j]);
                }
            }
        }
        scheduledExecuror.shutdown();
        executor.shutdown();
    }
}
