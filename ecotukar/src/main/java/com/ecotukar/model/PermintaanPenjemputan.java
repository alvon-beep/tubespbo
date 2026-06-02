package com.ecotukar.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "permintaan_penjemputan")
public class PermintaanPenjemputan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_verifikasi")
    private Integer idVerifikasi;

    @Column(name = "id_request", nullable = false)
    private Integer idRequest;

    @Column(name = "id_tukang", nullable = false)
    private Integer idTukang; // id_akun of courier (tukang)

    @Column(name = "berat_aktual", nullable = false)
    private Double beratAktual = 0.0;

    @Column(name = "catatan_fisik", columnDefinition = "TEXT")
    private String catatanFisik = "";

    @Column(name = "waktu_verifikasi", insertable = false, updatable = false)
    private LocalDateTime waktuVerifikasi;

    public PermintaanPenjemputan() {}

    public PermintaanPenjemputan(Integer idRequest, Integer idTukang) {
        this.idRequest = idRequest;
        this.idTukang = idTukang;
        this.beratAktual = 0.0;
        this.catatanFisik = "";
    }

    public Integer getIdVerifikasi() { return idVerifikasi; }
    public void setIdVerifikasi(Integer idVerifikasi) { this.idVerifikasi = idVerifikasi; }

    public Integer getIdRequest() { return idRequest; }
    public void setIdRequest(Integer idRequest) { this.idRequest = idRequest; }

    public Integer getIdTukang() { return idTukang; }
    public void setIdTukang(Integer idTukang) { this.idTukang = idTukang; }

    public Double getBeratAktual() { return beratAktual; }
    public void setBeratAktual(Double beratAktual) { this.beratAktual = beratAktual; }

    public String getCatatanFisik() { return catatanFisik; }
    public void setCatatanFisik(String catatanFisik) { this.catatanFisik = catatanFisik; }

    public LocalDateTime getWaktuVerifikasi() { return waktuVerifikasi; }
    public void setWaktuVerifikasi(LocalDateTime waktuVerifikasi) { this.waktuVerifikasi = waktuVerifikasi; }
}
