package com.apogeeDocument.apogeeDocument.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Table(name = "DOCUMENT")
@Entity
public class Document {
    @Id
    private Long id;
    private String nom;
    private String emplacement;
    private String type;
}
