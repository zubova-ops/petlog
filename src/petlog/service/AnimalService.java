package petlog.service;

import petlog.model.Animal;
import petlog.repo.AnimalRepository;

import java.util.List;
import java.util.Optional;

public class AnimalService {
    private final AnimalRepository repository;

    public AnimalService(AnimalRepository repository) {
        this.repository = repository;
    }

    public void save(Animal animal) {
        repository.save(animal);
    }

    public Optional<Animal> get(Long id) {
        return repository.findById(id);
    }

    public List<Animal> getAll() {
        return repository.findAll();
    }
}

