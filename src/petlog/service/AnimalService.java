package petlog.service;

import petlog.model.Animal;
import petlog.model.AnimalStatus;
import petlog.repo.AnimalRepository;

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
        } else {
            throw new IllegalArgumentException("такой id уже существует");
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
            throw new IllegalArgumentException("Имя усыновителя не должно быть пустым");
        }
        if (!isExist) {
            throw new IllegalArgumentException("такого животного не существует");
        }
        if (animal.getAnimalStatus().equals(AnimalStatus.ADOPTEDPET)) {
            throw new IllegalArgumentException("Животное уже усыновлено");
        }
        animal.adoptPet();
        repository.save(animal);

    }
}

