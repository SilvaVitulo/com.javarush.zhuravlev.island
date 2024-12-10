package com.javarush.zhuravlev.params;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class EntitiesParamsInitialization {
    private static Texts text = new Texts();

    //Заполняем эту карту параметрами из yaml с ключом animal name и значением AnimalParams
    private static final Map<String, AnimalParams> mapWithPatams = new HashMap<>();
    static {
        //Считывание байтов из yaml с помощью InputStream
        try (InputStream inputStream = EntitesParamsInitialization.class
                .getClassLoader()
                .getResourceAsStream("Params_all_entities.yaml")) {
            if (inputStream == null) {
                System.out.println(text.getFAILED_INPUT_READING());
                throw new RuntimeException();
            }
            Yaml yaml = new Yaml();
            Map<String, Map<String, Object>> yamlData = yaml.load(inputStream);

            //Распаковка даннх из yaml
            for (Map.Entry<String, Map<String, Object>> stringMapEntry : yamlData.entrySet()) {
                String entityName = stringMapEntry.getKey();

                //Для всех названий животных эта карта содержит персональные параметры
                Map<String, Object> values = stringMapEntry.getValue();
                AnimalParams params = new AnimalParams();

                //Получить параметры для всех персональных переменных
                params.setEmoji((String) values.get("emoji"));
                params.setSimpleNameLowerCase((String) values.get("simple_name_to_lower_case"));
                params.setWeigth((Double) values.get("weight"));
                params.setMaxPerCell((Integer) values.get("max_per_cell"));
                params.setMaxSpeed((Integer) values.get("max_speed"));
                params.setFoodNeededForMaxSatiety((Double) values.get("food_needed_for_max_satiety"));
                params.setEatingProbability((Map<String, Integer>) values.get("eating_probability"));

                mapWithPatams.put(entityName.toLowerCase(), params);
            }
        } catch (Exception e) {
            System.out.println(text.getFAILED_UNPACK());
            throw new RuntimeException(e);
        }
    }
    public static AnimalParams getParams(String animalName) {
        return mapWithPatams.get(animalName.toLowerCase());
    }
}
