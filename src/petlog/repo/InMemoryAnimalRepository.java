package petlog.repo;

import petlog.model.Animal;

import java.util.*;

public class InMemoryAnimalRepository implements AnimalRepository {
    private final Map<Long, Animal> map = new HashMap<>();

    @Override
    public void save(Animal animal) {
        map.put(animal.getId(), animal);
    }

    @Override
    public Optional<Animal> findById(Long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public List<Animal> findAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public void deleteById(Long id) {
        map.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return map.containsKey(id);
    }
}
