package com.ecotukar.repository;

import com.ecotukar.model.ProfilTukang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProfilTukangRepository extends JpaRepository<ProfilTukang, Integer> {
    Optional<ProfilTukang> findByIdAkun(Integer idAkun);
}
