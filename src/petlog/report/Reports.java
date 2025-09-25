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

public class Reports {

    public static Map<AnimalStatus, Long> countByStatus(List<Animal> animals) {
        return animals.stream()
                .collect(groupingBy(Animal::getAnimalStatus, Collectors.counting()));
    }

    public static Map<Species, Long> countBySpecies(List<Animal> animals) {
        return animals.stream()
                .collect(Collectors.groupingBy(Animal::getSpecies, Collectors.counting()));
    }

    public static List<Animal> searchByName(List<Animal> animals, String term) {
        return animals.stream()
                .filter(e -> e.getPetName().toLowerCase().contains(term.toLowerCase()))
                .collect(Collectors.toList());
    }

    public static List<Animal> topOldest(List<Animal> animals, int limit) {
        return animals.stream()
                .sorted(Comparator.comparing(Animal::getDateOfBirth))
                .limit(limit)
                .toList();
    }

    public static List<Animal> bornAfter(List<Animal> animals, LocalDate dateExclusive) {
        return animals.stream()
                .filter(e -> e.getDateOfBirth().isAfter(dateExclusive))
                .collect(Collectors.toList());
    }

    public static int ageYears(Animal a) {
        return Period.between(a.getDateOfBirth(), LocalDate.now()).getYears();
    }

    public static IntSummaryStatistics ageStats(List<Animal> animals) {
        return animals.stream()
                .mapToInt(Reports::ageYears)
                .summaryStatistics();
    }

    public static Map<String, Long> nameFrequencies(List<Animal> animals) {
        return animals.stream()
                .collect(groupingBy(Animal::getPetName, Collectors.counting()));
    }
}