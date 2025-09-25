package petlog;

import petlog.io.CsvAnimalStorage;
import petlog.model.Animal;
import petlog.model.AnimalStatus;
import petlog.model.Species;
import petlog.repo.InMemoryAnimalRepository;
import petlog.report.Reports;
import petlog.service.AnimalService;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
    private final AnimalService animalService;
    private final Scanner console;


    public ConsoleMenu() {
        animalService = new AnimalService(new InMemoryAnimalRepository());
        console = new Scanner(System.in);
    }

    public void run() {
        String action;
        System.out.println("===PetLog-консольное меню===");
        printHelp();
        do {
            action = console.nextLine();
            try {
                switch (action) {
                    case "help" -> printHelp();
                    case "add" -> add();
                    case "list" -> list();
                    case "adopt" -> adopt();
                    case "stats" -> statsFlow();
                    case "species" -> speciesFlow();
                    case "oldest" -> oldestFlow();
                    case "search" -> searchFlow();
                    case "born-after" -> bornAfterFlow();
                    case "import" -> importFromFile();
                    case "export" -> exportToFile();
                    case "exit" -> console.close();
                    case "" -> {
                    }
                    default -> System.out.println("Неизвестная команда. help - показать список команд");
                }
            } catch (Exception e) {
                System.out.println("Ошибка! " + e);
            }
        } while (!action.equalsIgnoreCase("exit"));
    }

    public static void printHelp() {
        System.out.printf(
                "\nКоманды: " +
                        "\nhelp - показать список команд" +
                        "\nadd - добавить животное" +
                        "\nlist - показать список животных" +
                        "\nadopt - усыновить животное" +
                        "\nexport - сохранить список в CSV" +
                        "\nimport - загрузить список из CSV" +
                        "\nstats - сводка: по статусу и возрастная статистика" +
                        "\nspecies - распределение по видам" +
                        "\noldest - топ-N самых старших (по дате рождения)" +
                        "\nsearch - поиск по подстроке имени" +
                        "\nborn-after - родившиеся после даты (ГГГГ-ММ-ДД)" +
                        "\nexit - выйти");
    }

    private void add() {
        System.out.println("Введите данные:");
        System.out.println("Id(целое положительное):");
        long id = console.nextLong();
        System.out.println("Имя:");
        String name = console.next();

        System.out.println("Введите дату рождения в формате dd.MM.yyyy (например, 25.12.2024):");
        String dateString = console.next();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = null;
        try {
            date = LocalDate.parse(dateString, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Ошибка: Неправильный формат даты. Пожалуйста, введите в формате dd.MM.yyyy.");
        }
        System.out.println("Вид животного:");
        Species species = Species.valueOf(console.next());
        System.out.println("Статус животного(усыновленный/бездомный):");
        AnimalStatus status = AnimalStatus.valueOf(console.next());
        Animal newAnimal = new Animal(id, name, date, status, species);
        animalService.save(newAnimal);
        System.out.println("Добавлено " + newAnimal);
    }

    private void list() {
        print(animalService.getAll());
    }

    private void importFromFile() {
        Path path = Path.of("C:\\Users\\79582\\Desktop\\Csv.txt");
        try {
            List<Animal> animals = CsvAnimalStorage.importFromFile(path, new ArrayList<>());
            for (Animal a : animals) {
                animalService.save(a);
            }
            System.out.println("Успешно!");
        } catch (IOException e) {
            System.out.println("Ошибка!");
            throw new RuntimeException(e);
        }
    }

    private void exportToFile() {
        List<Animal> animals = animalService.getAll();
        Path path = Path.of("C:\\Users\\79582\\Desktop\\Csv.txt");
        try {
            CsvAnimalStorage.exportToFile(path, animals);
            System.out.println("Успешно!");
        } catch (IOException e) {
            System.out.println("Ошибка!");
            throw new RuntimeException(e);
        }
    }

    private void adopt() {
        System.out.println("Введите id животного:");
        Long id = console.nextLong();
        System.out.println("Введите имя усыновителя:");
        String adopterName = console.next();
        animalService.adopt(id, adopterName);
        System.out.println("Усыновлено.");
    }

    private void statsFlow() {
        System.out.println(Reports.ageStats(animalService.getAll()));
    }

    private void speciesFlow() {
        System.out.println(Reports.countBySpecies(animalService.getAll()));
    }

    private void oldestFlow() {
        System.out.println("Введите лимит: ");
        int limit = console.nextInt();
        if (limit <= 0) {
            System.out.println("Введите целое положительное число");
        }
        print(Reports.topOldest(animalService.getAll(), limit));
    }

    private void searchFlow() {
        System.out.println("Введите подстроку имени: ");
        String searchName = console.nextLine().toLowerCase();
        print(Reports.searchByName(animalService.getAll(), searchName));
    }

    private void bornAfterFlow() {
        System.out.println("Введите дату в формате dd.MM.yyyy (например, 21.01.2020):");
        String dateSearch = console.next();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = null;
        try {
            date = LocalDate.parse(dateSearch, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Ошибка: Неправильный формат даты. Пожалуйста, введите в формате dd.MM.yyyy.");
        }
        print(Reports.bornAfter(animalService.getAll(), date));
    }

    private void print(List<Animal> animals) {
        for (Animal animal : animals) {
            System.out.println(animal);
        }
    }
}


