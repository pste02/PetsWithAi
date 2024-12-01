package com.example.petswithai.entity;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Household {
    @Id
    @NotNull
    private String eircode;

    @NotNull
    private int numberOfOccupants;

    @NotNull
    private int maxNumberOfOccupants;

    @NotNull
    private boolean ownerOccupied;

    @OneToMany(mappedBy = "household", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    private List<Pet> pets;
    public Household(String eircode, int numberOfOccupants, int maxNumberOfOccupants, boolean ownerOccupied) {
        this.eircode = eircode;
        this.numberOfOccupants = numberOfOccupants;
        this.maxNumberOfOccupants = maxNumberOfOccupants;
        this.ownerOccupied = ownerOccupied;
    }
}