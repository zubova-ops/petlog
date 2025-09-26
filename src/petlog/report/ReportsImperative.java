package petlog.report;

import petlog.model.Animal;
import petlog.model.AnimalStatus;
import petlog.model.Species;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

public class ReportsImperative {
    /**
     * Сводка по статусу: сколько SHELTERPET и сколько ADOPTEDPET.
     */
    public static Map<AnimalStatus, Long> countByStatus(List<Animal> animals) {
        Map<AnimalStatus, Long> map = new HashMap<>();
        for (Animal item : animals) {
            AnimalStatus status = item.getAnimalStatus();
            Long value = map.get(status);
            if (value == null) {
                value = 0L;
            }
            map.put(status, ++value);
        }
        return map;
    }

    /**
     * Распределение по видам: DOG/CAT/OTHER -> количество.
     */
    public static Map<Species, Long> countBySpecies(List<Animal> animals) {
        Map<Species, Long> mapSpecies = new HashMap<>();
        for (Animal item : animals) {
            Species species = item.getSpecies();
            Long value = mapSpecies.get(species);
            if (value == null) {
                value = 0L;
            }
            mapSpecies.put(species, ++value);
        }
        return mapSpecies;
    }

    /**
     * Поиск по подстроке имени (без учета регистра).
     */
    public static List<Animal> searchByName(List<Animal> animals, String term) {
        if (term == null || animals == null) {
            return new ArrayList<>();
        }
        String ternCopy = term.trim();
        if (ternCopy.isEmpty()) {
            return new ArrayList<>();
        }
        List<Animal> result = new ArrayList<>();
        for (Animal item : animals) {
            boolean isSubstring = item.getPetName().toLowerCase().contains(ternCopy.toLowerCase().trim());
            if (isSubstring) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * Топ-N самых старших по дате рождения (старые -> первые).
     */
    public static List<Animal> topOldest(List<Animal> animals, int limit) {
        if (animals == null || limit < 1) {
            return new ArrayList<>();
        }
        List<Animal> result = new ArrayList<>();
        return result;
    }

    /**
     * Фильтр: родились ПОСЛЕ указанной даты (строго после).
     */
    public static List<Animal> bornAfter(List<Animal> animals, LocalDate dateExclusive) {
        if (animals == null) return new ArrayList<>();
        if (dateExclusive == null || dateExclusive.isAfter(LocalDate.now())) return new ArrayList<>();
        List<Animal> result = new ArrayList<>();
        for (Animal item : animals) {
            if (item.getDateOfBirth().isAfter(dateExclusive)) {
                result.add(item);
            }
        }
        return result;
    }

    /**
     * Возраст в полных годах на сегодня.
     */
    public static int ageYears(Animal a) {
        ;
        return Period.between(a.getDateOfBirth(), LocalDate.now()).getYears();
    }

    /**
     * Статистика по возрастам (min/avg/max).
     */
    public static IntSummaryStatistics ageStats(List<Animal> animals) {
        return null;
    }

    /**
     * Частота имён: name -> сколько раз встречается.
     */
    public static Map<String, Long> nameFrequencies(List<Animal> animals) {
        Map<String, Long> mapName = new HashMap<>();
        for (Animal item : animals) {
            String petName = item.getPetName();
            Long value = mapName.get(petName);
            if (value == null) {
                value = 0L;
            }
            mapName.put(petName, ++value);
        }
        return mapName;
    }
}
