package petlog.model;

import java.time.LocalDate;
import java.util.Objects;

public class Animal {
    private long id;
    private String petName;
    private final LocalDate dateOfBirth;
    private AnimalStatus animalStatus;
    private final Species species;

    public Animal(long id, String petName, LocalDate dateOfBirth, AnimalStatus animalStatus, Species species) {
        if (id > 0) {
            this.id = id;
        } else throw new IllegalArgumentException("id < 0");
        if (petName == null) {
            throw new IllegalArgumentException("Имя пользователя не может быть null.");
        }
        if (!petName.trim().isEmpty()) {
            this.petName = petName;
        } else throw new IllegalArgumentException("имя не может быть пустым");
        boolean isNotFuture = !dateOfBirth.isAfter(LocalDate.now());
        if (isNotFuture) {
            this.dateOfBirth = dateOfBirth;
        } else throw new IllegalArgumentException("дата рождения не может быть позднее текущей даты");
        this.animalStatus = animalStatus;
        this.species = species;
    }

    public long getId() {
        return id;
    }

    public void adoptPet() {
        this.animalStatus = AnimalStatus.ADOPTEDPET;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetName() {
        return petName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public AnimalStatus getAnimalStatus() {
        return animalStatus;
    }

    public Species getSpecies() {
        return species;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return id == animal.id && Objects.equals(petName, animal.petName) && Objects.equals(dateOfBirth, animal.dateOfBirth) && animalStatus == animal.animalStatus && species == animal.species;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, petName, dateOfBirth, animalStatus, species);
    }

    @Override
    public String toString() {
        return "Animal{" +
                "id=" + id +
                ", petName='" + petName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", animalStatus=" + animalStatus +
                ", species=" + species +
                '}';
    }
}
