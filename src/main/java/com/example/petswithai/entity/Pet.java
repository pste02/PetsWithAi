package com.example.petswithai.entity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String name;

    @NotNull
    private String animalType;

    @NotNull
    private String breed;

    @Min(0)
    private int age;

    @ManyToOne
    @JoinColumn(name = "household_eircode", nullable = false)
    @JsonBackReference
    @NotNull
    private Household household;
    public Pet(String name, String animalType, String breed, int age, Household household) {
        this.name = name;
        this.animalType = animalType;
        this.breed = breed;
        this.age = age;
        this.household = household;
    }
}