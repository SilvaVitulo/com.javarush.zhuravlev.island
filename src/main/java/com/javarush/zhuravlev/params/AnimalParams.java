package com.javarush.zhuravlev.params;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class AnimalParams {
    public String emoji;
    public String simpleNameLowerCase;
    public double weight;
    public int maxPerCell;
    public int maxSpeed;
    public double foodNeededForMaxSatiety;
    public Map<String, Integer> eatingProbabylity;
}
