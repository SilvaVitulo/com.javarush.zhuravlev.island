import com.javarush.zhuravlev.entities.herbivores.*;
import com.javarush.zhuravlev.entities.*;
import com.javarush.zhuravlev.island.CellZone;
import java.util.concurrent.ThreadLocalRandom;

public class Herbivor extends Animal {

    public Herbivor(String animalName) {
        super(animalName);
    }

    /*
     *   Метод eat для травоядных.
     * Некоторые травоядные наполовину хищники.
     * Если животное наполовину хищник, то сначала пробует сеъесть другое животное, если первая попытка
     * съесть другое случайное животное не удалась , это животное поедает растения.
     */
    @Override
    public void eat(CellZone cell) {

        if (cell.animalsListOnCell.size() == 0 || cell.plantsListOnCell.size() == 0) { return;}

        int randomIndexForAnimals = ThreadLocalRandom.current().nextInt(0, cell.animalsListOnCell.size());
        int randomIndexForPlants = ThreadLocalRandom.current().nextInt(0, cell.plantsListOnCell.size());
        Animal animal = cell.animalsListOnCell.get(randomIndexForAnimals);
        Plant plant = cell.plantsListOnCell.get(randomIndexForPlants);

        if (this.getClass().equals(Duck.class)
                || this.getClass().equals(Boar.class)
                || this.getClass().equals(Mouse.class)) {
            //Пробуем съесть других животных
            int eatingTodayProbability = ThreadLocalRandom.current().nextInt(0 , 100);
            if ( this.eatingProbability.get(animal.simpleNameLowerCase) != null ) {
                if (this.eatingProbability.get(animal.simpleNameLowerCase) >= eatingTodayProbability) {
                    if (this.actualSatiety + (int)(animal.weight / (this.foodNeededForMaxSatiety / 100 )) >= 100 ) {
                        this.actualSatiety = 100;
                        cell.animalsListOnCell.remove(randomIndexForAnimals);
                    } else {
                        this.actualSatiety += (int)(animal.weight / (this.foodNeededForMaxSatiety / 100 ));
                        cell.animalsListOnCell.remove(randomIndexForAnimals);
                    }
                }
                // После неудачи поедает растения
            } else if (cell.plantsListOnCell.size() > 0) {
                if (this.actualSatiety + (int)(plant.weight / (this.foodNeededForMaxSatiety / 100 )) >= 100 ) {
                    this.actualSatiety = 100;
                    cell.plantsListOnCell.remove(randomIndexForPlants);
                } else {
                    this.actualSatiety += (int)(plant.weight / (this.foodNeededForMaxSatiety / 100 ));
                    cell.plantsListOnCell.remove(randomIndexForPlants);
                }
            }
            // Если животное наполовину хищник и есть растения
        } else if (cell.plantsListOnCell.size() > 0) {
            if (this.actualSatiety + (int)((plant.weight / (this.foodNeededForMaxSatiety / 100 ))) >= 100 ) {
                this.actualSatiety = 100;
                cell.plantsListOnCell.remove(randomIndexForPlants);
            } else {
                this.actualSatiety += (int)((plant.weight / (this.foodNeededForMaxSatiety / 100 )) / 4);
                cell.plantsListOnCell.remove(randomIndexForPlants);
            }
        }
    }
}
