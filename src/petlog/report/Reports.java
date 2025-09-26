package petlog.report;

import petlog.model.Animal;
import petlog.model.AnimalStatus;
import petlog.model.Species;

import java.time.LocalDate;
import java.time.Period;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * Набор «читаемых» отчётов на Stream API.
 * Все методы pure: на вход получаем список, на выход — готовые данные/DTO.
 */

public class Reports {
    /** Сводка по статусу: сколько SHELTERPET и сколько ADOPTEDPET. */
    public static Map<AnimalStatus, Long> countByStatus(List<Animal> animals) {
        return animals.stream()
                .collect(groupingBy(Animal::getAnimalStatus, Collectors.counting()));
    }

    /** Распределение по видам: DOG/CAT/OTHER -> количество. */
    public static Map<Species, Long> countBySpecies(List<Animal> animals) {
        return animals.stream()
                .collect(Collectors.groupingBy(Animal::getSpecies, Collectors.counting()));
    }

    /** Поиск по подстроке имени (без учета регистра). */
    public static List<Animal> searchByName(List<Animal> animals, String term) {
        return animals.stream()
                .filter(e -> e.getPetName().toLowerCase().contains(term.toLowerCase()))
                .collect(Collectors.toList());
    }

    /** Топ-N самых старших по дате рождения (старые -> первые). */
    public static List<Animal> topOldest(List<Animal> animals, int limit) {
        return animals.stream()
                .sorted(Comparator.comparing(Animal::getDateOfBirth))
                .limit(limit)
                .toList();
    }

    /** Фильтр: родились ПОСЛЕ указанной даты (строго после). */
    public static List<Animal> bornAfter(List<Animal> animals, LocalDate dateExclusive) {
        return animals.stream()
                .filter(e -> e.getDateOfBirth().isAfter(dateExclusive))
                .collect(Collectors.toList());
    }

    /** Возраст в полных годах на сегодня. */
    public static int ageYears(Animal a) {
        return Period.between(a.getDateOfBirth(), LocalDate.now()).getYears();
    }

    /** Статистика по возрастам (min/avg/max). */
    public static IntSummaryStatistics ageStats(List<Animal> animals) {
        return animals.stream()
                .mapToInt(Reports::ageYears)
                .summaryStatistics();
    }

    /** Частота имён: name -> сколько раз встречается (полезно для примеров Collectors.groupingBy + mapping). */
    public static Map<String, Long> nameFrequencies(List<Animal> animals) {
        return animals.stream()
                .collect(groupingBy(Animal::getPetName, Collectors.counting()));
    }
}