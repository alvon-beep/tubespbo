package com.ecotukar.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "request_sampah")
public class RequestSampah {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_request")
    private Integer idRequest;

    @Column(name = "id_customer", nullable = false)
    private Integer idCustomer; // id_akun of customer

    @Column(name = "jenis_sampah", nullable = false, length = 50)
    private String jenisSampah;

    @Column(name = "berat_estimasi", nullable = false)
    private Double beratEstimasi;

    @Column(name = "alamat_penjemputan", nullable = false, columnDefinition = "TEXT")
    private String alamatPenjemputan;

    @Column(length = 20)
    private String status = "PENDING"; // PENDING, ASSIGNED, ON ROUTE, COMPLETED, CANCELLED

    @Column(name = "tanggal_dibuat", insertable = false, updatable = false)
    private LocalDateTime tanggalDibuat;

    public RequestSampah() {}

    public RequestSampah(Integer idCustomer, String jenisSampah, Double beratEstimasi, String alamatPenjemputan) {
        this.idCustomer = idCustomer;
        this.jenisSampah = jenisSampah;
        this.beratEstimasi = beratEstimasi;
        this.alamatPenjemputan = alamatPenjemputan;
        this.status = "PENDING";
    }

    public Integer getIdRequest() { return idRequest; }
    public void setIdRequest(Integer idRequest) { this.idRequest = idRequest; }

    public Integer getIdCustomer() { return idCustomer; }
    public void setIdCustomer(Integer idCustomer) { this.idCustomer = idCustomer; }

    public String getJenisSampah() { return jenisSampah; }
    public void setJenisSampah(String jenisSampah) { this.jenisSampah = jenisSampah; }

    public Double getBeratEstimasi() { return beratEstimasi; }
    public void setBeratEstimasi(Double beratEstimasi) { this.beratEstimasi = beratEstimasi; }

    public String getAlamatPenjemputan() { return alamatPenjemputan; }
    public void setAlamatPenjemputan(String alamatPenjemputan) { this.alamatPenjemputan = alamatPenjemputan; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getTanggalDibuat() { return tanggalDibuat; }
    public void setTanggalDibuat(LocalDateTime tanggalDibuat) { this.tanggalDibuat = tanggalDibuat; }
}
