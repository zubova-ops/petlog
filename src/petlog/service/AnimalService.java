package petlog.service;

import petlog.model.Animal;
import petlog.model.AnimalStatus;
import petlog.repo.AnimalRepository;
import petlog.util.Log;

import java.util.List;
import java.util.Optional;

public class AnimalService {
    private final AnimalRepository repository;

    public AnimalService(AnimalRepository repository) {
        this.repository = repository;
    }

    public void save(Animal animal) {
        boolean isExist = repository.existsById(animal.getId());
        if (!isExist) {
            repository.save(animal);
            Log.info("Животное сохранено! id= " + animal.getId());
        } else {
            Log.warn("Животное с id = " + animal.getId() + " уже существует!");
            throw new ValidationException("такой id уже существует");
        }
    }

    public Optional<Animal> get(Long id) {
        return repository.findById(id);
    }

    public List<Animal> getAll() {
        return repository.findAll();
    }

    public void adopt(Long id, String adopterName) {
        boolean isExist = repository.existsById(id);
        Animal animal = repository.findById(id).get();
        if (adopterName == null || adopterName.trim().isEmpty()) {
            throw new ValidationException("Имя усыновителя не должно быть пустым");
        }
        if (!isExist) {
            Log.warn("Животное с id = " + id + " не существует!");
            throw new ValidationException("такого животного не существует");
        }
        if (animal.getAnimalStatus().equals(AnimalStatus.ADOPTEDPET)) {
            Log.warn("Животное с id = " + id + " уже усыновлено!");
            throw new ValidationException("Животное уже усыновлено");
        }
        animal.adoptPet();
        repository.save(animal);
        Log.info("Животное усыновлено! id= " + animal.getId());

    }
}

