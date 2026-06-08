package com.ecotukar.repository;

import com.ecotukar.model.RequestSampah;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RequestSampahRepository extends JpaRepository<RequestSampah, Integer> {
    List<RequestSampah> findByIdCustomerOrderByIdRequestDesc(Integer idCustomer);
    List<RequestSampah> findByStatusOrderByIdRequestDesc(String status);
}
