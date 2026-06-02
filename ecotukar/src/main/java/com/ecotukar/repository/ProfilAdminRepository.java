package com.ecotukar.repository;

import com.ecotukar.model.ProfilAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProfilAdminRepository extends JpaRepository<ProfilAdmin, Integer> {
    Optional<ProfilAdmin> findByIdAkun(Integer idAkun);
}
