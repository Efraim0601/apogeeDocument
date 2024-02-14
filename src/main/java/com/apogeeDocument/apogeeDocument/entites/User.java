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
public class User {
    @Id
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private Integer numero;
    private String numero_employe;
    private String password;
    private boolean isActive;


}
