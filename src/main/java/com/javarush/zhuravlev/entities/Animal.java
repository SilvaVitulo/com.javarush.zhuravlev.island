package com.javarush.zhuravlev.entities;

import com.javarush.zhuravlev.entities.herbivores.Caterpillar;
import com.javarush.zhuravlev.island.CellZone;
import com.javarush.zhuravlev.island.Island;
import com.javarush.zhuravlev.params.AnimalParams;
import com.javarush.zhuravlev.params.EntitiesParamsInitialization;
import com.javarush.zhuravlev.utilit.Directions;
import com.javarush.zhuravlev.utilit.Texts;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal {
    private static Texts text = new Texts();
    public String emoji;
    public String simpleNameLowerCase;
    public double weight;
    public int maxPerCell;
    public int maxSpeed;
    public double foodNeededForMaxSatiety;
    public int actualSatiety;
    public Map<String, Integer> eatingProbability;

    //Общий конструктор для всех сущностей и полей
    public Animal(String animalName) {
        AnimalParams params = EntitiesParamsInitialization.getParams(animalName);
        if (params != null) {
            this.emoji = params.getEmoji();
            this.simpleNameLowerCase = params.getSimpleNameLowerCase();
            this.weight = params.getWeight();
            this.maxPerCell = params.getMaxPerCell();
            this.maxSpeed = params.getMaxSpeed();
            this.foodNeededForMaxSatiety = params.getFoodNeededForMaxSatiety();
            this.actualSatiety = 100;
            this.eatingProbability = params.getEatingProbabylity();
        } else {
            throw new IllegalArgumentException(text.getNAME_NOT_FOUND());
        }
    }

    public abstract void eat(CellZone cell);

    //Каждый день этот метод проверяет поле actualSatiety для каждого животного в коллекции
    //Если значение поля меньше 0 или равно 0, животное удаляется из коллекции и животное считается погибшим.
    public void die(int index, List<Animal> animalList) {
        if (this.actualSatiety <= 0) {
            animalList.remove(index);
        }
    }

    public void reproduce(List<Animal> animals) {
        if (this.getClass().equals(Caterpillar.class)) {
            return;
        }
        if (animals.size() == 0 || this.actualSatiety <= 0) {
            return;
        }
        //Случайный выбор одного животного из коллекции
        int randomIndex = ThreadLocalRandom.current().nextInt(0, animals.size());
        Animal animal = animals.get(randomIndex);
        if (animal == this) { //не клоны
            return;
        }
        //Проверка, есть ли у нас в коллекции больше места для этого вида животных
        int countOfInstanceOnCell = 0;
        if (this.getClass().equals(animal.getClass())) {
            for (Animal animal1 : animals) {
                if (animal1.getClass().equals(this.getClass())) {
                    countOfInstanceOnCell++;
                }
            }
        }
        //Если есть свободное место, помещаем новый экземпляр этого типа animal в коллекцию
        if (this.getClass().equals(animal.getClass()) && countOfInstanceOnCell < this.maxPerCell) {
            try {
                animals.add(this.getClass().getDeclaredConstructor().newInstance());
                this.actualSatiety -= 25;
                animal.actualSatiety -= 25;
            } catch (Exception e) {
                throw new RuntimeException(text.getCONSTRUCTOR_NOT_FIND());
            }
        }
    }

    // Метод определяет где находится объект, и возвращает в каких направлениях можно двигаться (все возможные сценарии)
    public Enum choseDirection(int cellCoordinateX, int cellCoordinateY) {
        if (this.actualSatiety <= 0 || this.actualSatiety <= 25) {
            return null;
        }
        this.actualSatiety -= 25;

        if (cellCoordinateX > 0 && cellCoordinateY > 0 && cellCoordinateX < Island.getISLAND_HEIGT() - 1
            && cellCoordinateY < Island.getISLAND_WIDTH() - 1) {
            int randomNumberOfDirection = ThreadLocalRandom.current().nextInt(0, 4);
            if (randomNumberOfDirection == 0) {
                return Directions.UP;
            } else if (randomNumberOfDirection == 1) {
                return Directions.RIGHT;
            } else if (randomNumberOfDirection == 2) {
                return Directions.DOWN;
            } else if (randomNumberOfDirection == 3) {
                return Directions.LEFT;
            }
        } else if (cellCoordinateX == 0 && cellCoordinateY == 0) {
            int randomNumberOfDirection = ThreadLocalRandom.current().nextInt(0, 2);
            return randomNumberOfDirection == 0 ? Directions.RIGHT : Directions.DOWN;
        } else if (cellCoordinateX == 0 && cellCoordinateY > 0 && cellCoordinateY < Island.getISLAND_WIDTH() - 1) {
            int randomNumberOfDirection = ThreadLocalRandom.current().nextInt(0, 3);
            if (randomNumberOfDirection == 0) {
                return Directions.RIGHT;
            } else if (randomNumberOfDirection == 1) {
                return Directions.DOWN;
            } else if (randomNumberOfDirection == 2) {
                return Directions.LEFT;
            }
        } else if (cellCoordinateX == 0 && cellCoordinateY == Island.getISLAND_WIDTH() - 1) {
            int randomNumberOfDirection = ThreadLocalRandom.current().nextInt(0, 2);
            return randomNumberOfDirection == 0 ? Directions.DOWN : Directions.LEFT;
        } else if (cellCoordinateX > 0 && cellCoordinateY == 0 && cellCoordinateX < Island.getISLAND_HEIGT()) {
            int randomNumberOfDirection = ThreadLocalRandom.current().nextInt(0, 3);
            if (randomNumberOfDirection == 0) {
                return Directions.UP;
            } else if (randomNumberOfDirection == 1) {
                return Directions.RIGHT;
            } else if (randomNumberOfDirection == 2) {
                return Directions.DOWN;
            }
        } else if (cellCoordinateX > 0 && cellCoordinateX < Island.getISLAND_HEIGT() - 1
                && cellCoordinateY == Island.getISLAND_WIDTH() - 1) {
            int randomNumberOfDirection = ThreadLocalRandom.current().nextInt(0, 3);
            if (randomNumberOfDirection == 0) {
                return Directions.UP;
            } else if (randomNumberOfDirection == 1) {
                return Directions.DOWN;
            } else if (randomNumberOfDirection == 2) {
                return Directions.LEFT;
            }
        } else if (cellCoordinateX == Island.getISLAND_HEIGT() - 1 && cellCoordinateY == 0) {
            int randomNumberOfDirection = ThreadLocalRandom.current().nextInt(0, 2);
            return randomNumberOfDirection == 0 ? Directions.UP : Directions.RIGHT;
        } else if (cellCoordinateX == Island.getISLAND_HEIGT() - 1 && cellCoordinateY > 0
                && cellCoordinateY < Island.getISLAND_WIDTH() - 1) {
            int randomNumberOfDirection = ThreadLocalRandom.current().nextInt(0, 3);
            if (randomNumberOfDirection == 0) {
                return Directions.UP;
            } else if (randomNumberOfDirection == 1) {
                return Directions.RIGHT;
            } else if (randomNumberOfDirection == 2) {
                return Directions.LEFT;
            }
        } else if (cellCoordinateX == Island.getISLAND_HEIGT() - 1 && cellCoordinateY == Island.getISLAND_WIDTH() - 1) {
            int randomNumberOfDirection = ThreadLocalRandom.current().nextInt(0, 2);
            return randomNumberOfDirection == 0 ? Directions.UP : Directions.LEFT;
        }
        return null;
    }

    //Способ перемещения животного в другую зону / клетку
    public void moveToOtherCell(Enum direction, int cellCoordinateX, int cellCoordinateY, List<Animal> list, int index) {
        if (cellCoordinateX % 2 == 0) {
            return;
        }
        if (this.actualSatiety <= 0) {
            this.die(index, list);
        }
        if (direction == null || this.actualSatiety <= 25) {
            return;
        }
        this.actualSatiety -= 25;

        int randomMovingDistance = ThreadLocalRandom.current().nextInt(0, this.maxSpeed + 1);
        if (direction == Directions.UP) {
            if (cellCoordinateX - randomMovingDistance >= 0) {
                int count = (int) Island.arrayWithAllCells[cellCoordinateX - randomMovingDistance][cellCoordinateY]
                        .animalsListOnCell.stream().filter(x -> x.equals(this.getClass())).count();
                if (this.maxPerCell < count) {
                    Island.arrayWithAllCells[cellCoordinateX - randomMovingDistance][cellCoordinateY]
                            .animalsListOnCell.add(this);
                    list.remove(index);
                }
            } else if (cellCoordinateX - randomMovingDistance < 0) {
                int cout = (int) Island.arrayWithAllCells[0][cellCoordinateY]
                        .animalsListOnCell.stream().filter(x -> x.equals(this.getClass())).count();
                if (this.maxPerCell < cout) {
                    Island.arrayWithAllCells[0][cellCoordinateY].animalsListOnCell.add(this);
                    list.remove(index);
                }
            }
        } else if (direction == Directions.RIGHT) {
            if (cellCoordinateY + randomMovingDistance <= Island.getISLAND_WIDTH() - 1) {
                int count = (int) Island.arrayWithAllCells[cellCoordinateX][cellCoordinateY + randomMovingDistance]
                        .animalsListOnCell.stream().filter(x -> x.equals(this.getClass())).count();
                if (this.maxPerCell < count) {
                    Island.arrayWithAllCells[cellCoordinateX][cellCoordinateY + randomMovingDistance]
                            .animalsListOnCell.add(this);
                    list.remove(index);
                }
            } else if (cellCoordinateY + randomMovingDistance > Island.getISLAND_WIDTH() - 1) {
                int count = (int) Island.arrayWithAllCells[cellCoordinateX][Island.getISLAND_WIDTH() - 1]
                        .animalsListOnCell.stream().filter(x -> x.equals(this.getClass())).count();
                if (this.maxPerCell < count) {
                    Island.arrayWithAllCells[cellCoordinateX][Island.getISLAND_WIDTH()-1].animalsListOnCell.add(this);
                    list.remove(index);
                }
            }
        } else if (direction == Directions.DOWN) {
            if (cellCoordinateX + randomMovingDistance <= Island.getISLAND_HEIGT() - 1) {
                int count = (int) Island.arrayWithAllCells[cellCoordinateX + randomMovingDistance][cellCoordinateY]
                        .animalsListOnCell.stream().filter(x -> x.equals(this.getClass())).count();
                if (this.maxPerCell < count) {
                    Island.arrayWithAllCells[cellCoordinateX + randomMovingDistance][cellCoordinateY].animalsListOnCell.add(this);
                    list.remove(index);
                }
            } else if (cellCoordinateX + randomMovingDistance > Island.getISLAND_HEIGT() - 1) {
                int count = (int) Island.arrayWithAllCells[Island.getISLAND_HEIGT() - 1][cellCoordinateY]
                        .animalsListOnCell.stream().filter(x -> x.equals(this.getClass())).count();
                if (this.maxPerCell < count) {
                    Island.arrayWithAllCells[Island.getISLAND_HEIGT() - 1][cellCoordinateY].animalsListOnCell.add(this);
                    list.remove(index);
                }
            }
        } else if (direction == Directions.LEFT) {
            if (cellCoordinateY - randomMovingDistance >= 0) {
                int count = (int) Island.arrayWithAllCells[cellCoordinateX][cellCoordinateY - randomMovingDistance]
                        .animalsListOnCell.stream().filter(x -> x.equals(this.getClass())).count();
                if (this.maxPerCell < count) {
                    Island.arrayWithAllCells[cellCoordinateX][cellCoordinateY - randomMovingDistance].animalsListOnCell.add(this);
                    list.remove(index);
                }
            } else if (cellCoordinateY - randomMovingDistance < 0) {
                int count = (int) Island.arrayWithAllCells[cellCoordinateX][0].animalsListOnCell.stream()
                        .filter(x -> x.equals(this.getClass())).count();
                if (this.maxPerCell < count) {
                    Island.arrayWithAllCells[cellCoordinateX][0].animalsListOnCell.add(this);
                    list.remove(index);
                }
            }
        }
    }

    //Каждый день этот метод - уменьшает фактическое насыщения для каждого животного в коллекции
    public void dailyWorker(List<Animal> list, int index) {
        if (this.actualSatiety <= 0) {
            list.remove(index);
        }
        this.actualSatiety -= 50;
    }
}
