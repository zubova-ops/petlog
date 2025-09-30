package petlog;

import petlog.io.CsvAnimalStorage;
import petlog.model.Animal;
import petlog.model.AnimalStatus;
import petlog.model.Species;
import petlog.repo.InMemoryAnimalRepository;
import petlog.report.Reports;
import petlog.report.ReportsImperative;
import petlog.service.AnimalService;
import petlog.util.Log;

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
                    case "status-count" -> statusFlow();
                    case "name-frequencies" -> nameFrequenciesFlow();
                    case "born-after" -> bornAfterFlow();
                    case "import" -> importFromFile();
                    case "export" -> exportToFile();
                    case "exit" -> console.close();
                    case "" -> {
                    }
                    default -> System.out.println("Неизвестная команда. help - показать список команд");
                }
            } catch (Exception e) {
                Log.error("Ошибка! " + e);
            }
        } while (!action.equalsIgnoreCase("exit"));
    }

    public static void printHelp() {
        System.out.println(
                "\nКоманды: " +
                        "\nhelp - показать список команд" +
                        "\nadd - добавить животное" +
                        "\nlist - показать список животных" +
                        "\nadopt - усыновить животное" +
                        "\nexport - сохранить список в CSV" +
                        "\nimport - загрузить список из CSV" +
                        "\nstats - сводка: по статусу и возрастная статистика" +
                        "\nstatus-count - сводка питомцев по их статусу в приюте" +
                        "\nspecies - распределение по видам" +
                        "\nname-frequencies - name -> сколько раз встречается" +
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
            Log.error("Ошибка: Неправильный формат даты");
            System.out.println("Ошибка: Неправильный формат даты. Пожалуйста, введите в формате dd.MM.yyyy.");
        }
        System.out.println("Вид животного:");
        Species species = Species.valueOf(console.next());
        System.out.println("Статус животного(усыновленный/бездомный):");
        AnimalStatus status = AnimalStatus.valueOf(console.next());
        Animal newAnimal = new Animal(id, name, date, status, species);
        animalService.save(newAnimal);
        Log.info("Добавлено новое животное id= " + newAnimal.getId());
        System.out.println("Животное добавлено!");
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
            Log.info("Успешно! Файл импортирован!");
            System.out.println("Успешно! Файл импортирован!");
        } catch (IOException e) {
            Log.error("Ошибка!" + e);
            System.out.println("Ошибка импорта!");
            throw new RuntimeException(e);
        }
    }

    private void exportToFile() {
        List<Animal> animals = animalService.getAll();
        Path path = Path.of("C:\\Users\\79582\\Desktop\\Csv.txt");
        try {
            CsvAnimalStorage.exportToFile(path, animals);
            Log.info("Успешно! Файл экспортирован!");
            System.out.println("Успешно! Файл экспортирован!");
        } catch (IOException e) {
            Log.error("Ошибка!" + e);
            System.out.println("Ошибка экспорта!");
            throw new RuntimeException(e);
        }
    }

    private void adopt() {
        System.out.println("Введите id животного:");
        Long id = console.nextLong();
        System.out.println("Введите имя усыновителя:");
        String adopterName = console.next();
        animalService.adopt(id, adopterName);
        Log.info("Животное усыновлено");
        System.out.println("Животное усыновлено");
    }

    private void statsFlow() {
        System.out.println(Reports.ageStats(animalService.getAll()));
    }

    private void speciesFlow() {
        System.out.println(Reports.countBySpecies(animalService.getAll()));
    }

    private void nameFrequenciesFlow() {
        System.out.println(ReportsImperative.nameFrequencies(animalService.getAll()));
    }

    private void statusFlow() {
        System.out.println(ReportsImperative.countByStatus(animalService.getAll()));
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
        print(ReportsImperative.searchByName(animalService.getAll(), searchName));
    }

    private void bornAfterFlow() {
        System.out.println("Введите дату в формате dd.MM.yyyy (например, 21.01.2020):");
        String dateSearch = console.next();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = null;
        try {
            date = LocalDate.parse(dateSearch, formatter);
        } catch (DateTimeParseException e) {
            Log.error("Ошибка: Неправильный формат даты." + e);
            System.out.println("Ошибка: Неправильный формат даты. Введите дату в формате dd.MM.yyyy!");
        }
        print(Reports.bornAfter(animalService.getAll(), date));
    }

    private void print(List<Animal> animals) {
        for (Animal animal : animals) {
            System.out.println(animal);
        }
    }
}


