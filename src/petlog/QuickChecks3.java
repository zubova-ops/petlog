package petlog;

import petlog.model.Animal;
import petlog.model.AnimalStatus;
import petlog.model.Species;
import petlog.report.Reports;

import java.time.LocalDate;
import java.util.List;

public class QuickChecks3 {
    public static void main(String[] args) {
        var a = List.of(
                new Animal(1L, "Марта", LocalDate.of(2023, 3, 1), AnimalStatus.SHELTERPET,Species.DOG),
                new Animal(2L, "Барсик", LocalDate.of(2020, 1, 15), AnimalStatus.SHELTERPET, Species.CAT),
                new Animal(3L, "Маркиз", LocalDate.of(2019, 5, 10), AnimalStatus.SHELTERPET, Species.CAT)
        );

        System.out.println("Поиск 'мар': " + Reports.searchByName(a, "мар").size());
        System.out.println("Топ-2 старших: " + Reports.topOldest(a, 2));
        System.out.println("Статистика возраста: " + Reports.ageStats(a));
    }
}
