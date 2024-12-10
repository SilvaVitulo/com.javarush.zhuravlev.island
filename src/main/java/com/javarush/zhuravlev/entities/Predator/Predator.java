package com.javarush.zhuravlev.entities.Predator;

import main.Entities.Animal;
import main.Island.CellZone;

import java.util.concurrent.ThreadLocalRandom;

public class Predator extends Animal {
    public Predator(String animalName){
        super(animalName);
    }

    //Метод "есть" для хищников. Все хищники едят только других животных.
    //Жертва для хищника случайна, и вероятность поедания также случайна
    @Override
    public void eat(CellZone cell) {
        if (cell.animalsListOnCell.size() == 0) {
            return;
        }
        int randomIndex = ThreadLocalRandom.current().nextInt(0, cell.animalsListOnCell.size());
        Animal animal = cell.animalsListOnCell.get(randomIndex);
        int eatingTodayProbability = ThreadLocalRandom.current().nextInt(0, 100);

        if (this.eatingProbability.get(animal.simpleNameLowerCase) != null) {
            if (this.eatingProbability.get(animal.simpleNameLowerCase) >= eatingTodayProbability) {
                if (this.actualSatiety + (int)(animal.weight / (this.foodNeededForMaxSatiety / 100)) >= 100) {
                    this.actualSatiety = 100;
                    cell.animalsListOnCell.remove(randomIndex);
                } else {
                    this.actualSatiety += (int)(animal.weight / (this.foodNeededForMaxSatiety / 100));
                    cell.animalsListOnCell.remove(randomIndex);
                }
            }
        }
    }
}
