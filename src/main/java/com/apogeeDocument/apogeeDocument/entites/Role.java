package com.apogeeDocument.apogeeDocument.entites;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class Role {
    @Id
    private Long id;
    private String nom;
}
