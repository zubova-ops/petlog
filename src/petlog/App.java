package petlog;

import petlog.model.Animal;
import petlog.model.AnimalStatus;
import petlog.model.Species;
import petlog.repo.InMemoryAnimalRepository;
import petlog.service.AnimalService;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

public class App {
    public static void main(String[] args) {
        ConsoleMenu console = new ConsoleMenu();
        console.run();
        System.out.println("Это мой приют:");
        Animal cat = new Animal(1, "Missa", LocalDate.of(2018, Month.JUNE, 01),
                AnimalStatus.SHELTERPET, Species.CAT);

        Animal dog = new Animal(2, "Grey", LocalDate.of(2010, Month.APRIL, 20),
                AnimalStatus.SHELTERPET, Species.DOG);

        Animal mouse = new Animal(3, "Minnie", LocalDate.of(2025, Month.AUGUST, 15),
                AnimalStatus.SHELTERPET, Species.OTHER);


        AnimalService animalService = new AnimalService(new InMemoryAnimalRepository());
        animalService.save(cat);
        System.out.println("Добавлено новое животное id= " + cat.getId());
        animalService.save(dog);
        System.out.println("Добавлено новое животное id= " + dog.getId());
        animalService.save(mouse);
        System.out.println("Добавлено новое животное id= " + mouse.getId());

        Optional<Animal> animal = animalService.get(1L);
        System.out.println("Данный номер соответствует питомцу:  " + animal);
        animal = animalService.get(2L);
        System.out.println("Данный номер соответствует питомцу:  " + animal);
        animal = animalService.get(3L);
        System.out.println("Данный номер соответствует питомцу:  " + animal);
        animalService.adopt(2L, "null");
        animalService.adopt(2L, "null");


        List<Animal> animals = animalService.getAll();
        for (Animal item : animals) {
            System.out.println("\n" + item);
        }
    }
}