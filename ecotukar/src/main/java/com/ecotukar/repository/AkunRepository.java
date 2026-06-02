package com.ecotukar.repository;

import com.ecotukar.model.Akun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AkunRepository extends JpaRepository<Akun, Integer> {
    Optional<Akun> findByEmail(String email);
}
