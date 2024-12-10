package com.javarush.zhuravlev.island;

import com.javarush.zhuravlev.entities.Animal;
import com.javarush.zhuravlev.entities.Plant;
import com.javarush.zhuravlev.entities.herbivores.*;
import com.javarush.zhuravlev.entities.predators.*;
import com.javarush.zhuravlev.utilit.Text;
import lombok.Getter;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

@Getter
public class CellZone implements Runnable {

    private static Text text = new Text();
    public CopyOnWriteArrayList<Animal> animalsListOnCell = new CopyOnWriteArrayList<>();
    public CopyOnWriteArrayList<Plant> plantsListOnCell = new CopyOnWriteArrayList<>();
    private final Integer coordinateX;
    private final Integer coordinateY;

    //Конструктор для координат ячейки
    public CellZone(Integer coordinateX, Integer coordinateY) {
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
    }

    {
        fillCollectionWithAnimals(new Wolf());
        fillCollectionWithAnimals(new Fox());
        fillCollectionWithAnimals(new Boa());
        fillCollectionWithAnimals(new Bear());
        fillCollectionWithAnimals(new Eagle());
        fillCollectionWithAnimals(new Boar());
        fillCollectionWithAnimals(new Buffalo());
        fillCollectionWithAnimals(new Caterpillar());
        fillCollectionWithAnimals(new Deer());
        fillCollectionWithAnimals(new Duck());
        fillCollectionWithAnimals(new Goat());
        fillCollectionWithAnimals(new Horse());
        fillCollectionWithAnimals(new Mouse());
        fillCollectionWithAnimals(new Rabbit());
        fillCollectionWithAnimals(new Sheep());
        addPlantsOnCell();
    }

    //Метод устанавливает случайное количество объектов на ячейку в диапазоне от 1 до maxPerCell
    private static Integer entitiesOnCell(Animal animal) {
        return ThreadLocalRandom.current().nextInt(1, animal.maxPerCell + 1);
    }

    // Этот метод получает тип объекта и заполняет коллекцию новыми экземплярами этого объекта
    // Для всех новых объектов используйтся reflection и подсчитается сколько животных находится в одной ячейке
    private void fillCollectionWithAnimals(Animal animal) {
        for (int i = 0; i < entitiesOnCell(animal); i++) {
            try {
                animalsListOnCell.add(animal.getClass().getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(text.getINSTANCE_ERROR());
            }
        }
    }

    //Метод добавляет в ячейку случайное количество растений (в диапазоне от 1 до максимального кол-ва)
    public void addPlantsOnCell() {
        int plantsNumberOnCell = ThreadLocalRandom.current().nextInt(1, new Plant().maxPerCell + 1);
        for (int i = 0; i < plantsNumberOnCell; i++) {
            plantsListOnCell.add(new Plant());
        }
    }

    // Запуск всеч методов для каждого животного
    @Override
    public void run() {
        synchronized (this) {
            for (int i = 0; i < animalsListOnCell.size() - 1; i++) {
                animalsListOnCell.get(i).dailyWorker(animalsListOnCell, i);
                animalsListOnCell.get(i).eat(this);
                animalsListOnCell.get(i).reproduce(animalsListOnCell);
                animalsListOnCell.get(i).moveToOtherCell(animalsListOnCell.get(i)
                        .choseDirection(coordinateX, coordinateY), coordinateX, coordinateY, animalsListOnCell, i);
                animalsListOnCell.get(i).die(i, animalsListOnCell);
            }
        }
    }
}
