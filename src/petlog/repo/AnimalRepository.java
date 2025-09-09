package petlog.repo;

import petlog.model.Animal;

import java.util.List;
import java.util.Optional;

public interface AnimalRepository {
    void save(Animal animal);

    Optional<Animal> findById(Long id);

    List<Animal> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);
}
