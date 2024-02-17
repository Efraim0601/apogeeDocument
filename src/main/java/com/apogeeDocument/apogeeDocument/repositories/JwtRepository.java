package com.apogeeDocument.apogeeDocument.repositories;

import com.apogeeDocument.apogeeDocument.entites.Jwt;
import org.springframework.data.repository.CrudRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface JwtRepository extends CrudRepository<Jwt, Integer > {
    Optional<Jwt> findByValueAndDisabledAndExpired(String value, boolean disable, boolean expire);
}
