package com.apogeeDocument.apogeeDocument.repositories;

import com.apogeeDocument.apogeeDocument.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByEmail(String email);
   /* User findByNomAndPrenom(String nom, String prenom);
    User findByNom(String nom);
    User findByNumero_employe(Integer numero_employe);
    User findByNumero(Integer numero);*/
}
