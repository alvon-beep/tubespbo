package com.ecotukar.repository;

import com.ecotukar.model.PermintaanPenjemputan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface PermintaanPenjemputanRepository extends JpaRepository<PermintaanPenjemputan, Integer> {
    Optional<PermintaanPenjemputan> findByIdRequest(Integer idRequest);
    List<PermintaanPenjemputan> findByIdTukang(Integer idTukang);
}
