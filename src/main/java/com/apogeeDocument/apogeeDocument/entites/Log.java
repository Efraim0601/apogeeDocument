package com.apogeeDocument.apogeeDocument.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "LOG")
@Entity
public class Log {
    @Id
    private Long id;
    private String nom;
    private String date_creation;
}