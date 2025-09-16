package petlog.io;

import petlog.model.Animal;
import petlog.model.AnimalStatus;
import petlog.model.Species;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CsvAnimalStorage {
    private static final String SYMBOL = ";";

    public static void exportToFile(Path path, List<Animal> animals) throws IOException {
        try (BufferedWriter w = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            w.write("id;name;species;birthDate;status;");
            w.newLine();
            for (Animal a : animals) {
                String animalLine = joinCsv(
                        a.getId(),
                        a.getPetName(),
                        a.getSpecies(),
                        a.getDateOfBirth(),
                        a.getAnimalStatus());
                w.write(animalLine);
                w.newLine();
            }
        }
    }

    public static List<Animal> importFromFile(Path path, List<String> errorLines) throws IOException {
        List<Animal> result = new ArrayList<>();
        try (BufferedReader r = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            boolean first = true;
            while ((line = r.readLine()) != null) {
                if (first) {
                    first = false;
                    continue;
                }
                if (line.isBlank()) continue;
                try {
                    Animal a = parseLine(line);
                    result.add(a);
                } catch (Exception e) {
                    errorLines.add(line + "  // ошибка: " + e.getMessage());
                }
            }
        }
        return result;
    }


    private static Animal parseLine(String line) throws Exception {
        try {
            String[] linesArray = line.split(SYMBOL);
            long id = Long.parseLong(linesArray[0]);
            String name = linesArray[1];
            Species species = Species.valueOf(linesArray[2]);
            LocalDate birthDay = LocalDate.parse(linesArray[3]);
            AnimalStatus status = AnimalStatus.valueOf(linesArray[4]);
            return new Animal(id, name, birthDay, status, species);
        } catch (Exception e) {
            throw new Exception("Ошибка парсинга " + e.getMessage());
        }
    }

    private static String joinCsv(Object... parts) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (i > 0) sb.append(';');
            sb.append(parts[i] == null ? "" : parts[i]);
        }
        return sb.toString();
    }
}