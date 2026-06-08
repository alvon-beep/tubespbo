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

    @OneToOne
    @JoinColumn(name = "id_request", nullable = false)
    private RequestSampah requestSampah;

    @ManyToOne
    @JoinColumn(name = "id_tukang", nullable = false)
    private ProfilTukang tukang;

    @Column(name = "berat_aktual", nullable = false)
    private Double beratAktual = 0.0;

    @Column(name = "catatan_fisik", columnDefinition = "TEXT")
    private String catatanFisik = "";

    @Column(name = "waktu_verifikasi", insertable = false, updatable = false)
    private LocalDateTime waktuVerifikasi;

    @Column(name = "status_verifikasi", length = 20)
    private String statusVerifikasi = "UNVERIFIED";

    public PermintaanPenjemputan() {}

    public PermintaanPenjemputan(RequestSampah requestSampah, ProfilTukang tukang) {
        this.requestSampah = requestSampah;
        this.tukang = tukang;
        this.beratAktual = 0.0;
        this.catatanFisik = "";
        this.statusVerifikasi = "UNVERIFIED";
    }

    public Integer getIdVerifikasi() { return idVerifikasi; }
    public void setIdVerifikasi(Integer idVerifikasi) { this.idVerifikasi = idVerifikasi; }

    public RequestSampah getRequestSampah() { return requestSampah; }
    public void setRequestSampah(RequestSampah requestSampah) { this.requestSampah = requestSampah; }

    public ProfilTukang getTukang() { return tukang; }
    public void setTukang(ProfilTukang tukang) { this.tukang = tukang; }

    public Double getBeratAktual() { return beratAktual; }
    public void setBeratAktual(Double beratAktual) { this.beratAktual = beratAktual; }

    public String getCatatanFisik() { return catatanFisik; }
    public void setCatatanFisik(String catatanFisik) { this.catatanFisik = catatanFisik; }

    public LocalDateTime getWaktuVerifikasi() { return waktuVerifikasi; }
    public void setWaktuVerifikasi(LocalDateTime waktuVerifikasi) { this.waktuVerifikasi = waktuVerifikasi; }

    public String getStatusVerifikasi() { return statusVerifikasi; }
    public void setStatusVerifikasi(String statusVerifikasi) { this.statusVerifikasi = statusVerifikasi; }
}
