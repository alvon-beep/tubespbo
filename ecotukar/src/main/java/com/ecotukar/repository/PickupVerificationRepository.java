package com.ecotukar.repository;

import com.ecotukar.model.PickupVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PickupVerificationRepository extends JpaRepository<PickupVerification, String> {
}
