package com.ecotukar.repository;

import com.ecotukar.model.ProfilCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProfilCustomerRepository extends JpaRepository<ProfilCustomer, Integer> {
    Optional<ProfilCustomer> findByIdAkun(Integer idAkun);
}
