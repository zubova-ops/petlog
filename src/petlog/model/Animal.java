package petlog.model;

import java.time.LocalDate;
import java.util.Objects;

public class Animal {
    private long id;
    private final String petName;
    private final LocalDate dateOfBirth;
    private AnimalStatus animalStatus;
    private final Species species;

    public Animal(String petName, LocalDate dateOfBirth, AnimalStatus animalStatus, Species species) {
        this.petName = petName;
        this.dateOfBirth = dateOfBirth;
        this.animalStatus = animalStatus;
        this.species = species;
    }

    public Animal(long id, String petName, LocalDate dateOfBirth, AnimalStatus animalStatus, Species species) {
        this.id = id;
        this.petName = petName;
        this.dateOfBirth = dateOfBirth;
        this.animalStatus = animalStatus;
        this.species = species;
    }

    public long getId() {
        return id;
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

    public void setAnimalStatus(AnimalStatus animalStatus) {
        this.animalStatus = animalStatus;
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
