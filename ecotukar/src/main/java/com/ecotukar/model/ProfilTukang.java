package com.ecotukar.model;

import jakarta.persistence.*;

@Entity
@Table(name = "profil_tukang")
public class ProfilTukang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tukang")
    private Integer idTukang;

    @Column(name = "id_akun", nullable = false)
    private Integer idAkun;

    @Column(name = "nama_lengkap", nullable = false, length = 150)
    private String namaLengkap;

    @Column(name = "no_telepon", nullable = false, length = 15)
    private String noTelepon;

    @Column(name = "plat_kendaraan", length = 20)
    private String platKendaraan;

    @Column(name = "status_ketersediaan")
    private Boolean statusKetersediaan = true;

    public ProfilTukang() {}

    public ProfilTukang(Integer idAkun, String namaLengkap, String noTelepon) {
        this.idAkun = idAkun;
        this.namaLengkap = namaLengkap;
        this.noTelepon = noTelepon;
        this.statusKetersediaan = true;
    }

    public Integer getIdTukang() { return idTukang; }
    public void setIdTukang(Integer idTukang) { this.idTukang = idTukang; }

    public Integer getIdAkun() { return idAkun; }
    public void setIdAkun(Integer idAkun) { this.idAkun = idAkun; }

    public String getNamaLengkap() { return namaLengkap; }
    public void setNamaLengkap(String namaLengkap) { this.namaLengkap = namaLengkap; }

    public String getNoTelepon() { return noTelepon; }
    public void setNoTelepon(String noTelepon) { this.noTelepon = noTelepon; }

    public String getPlatKendaraan() { return platKendaraan; }
    public void setPlatKendaraan(String platKendaraan) { this.platKendaraan = platKendaraan; }

    public Boolean getStatusKetersediaan() { return statusKetersediaan; }
    public void setStatusKetersediaan(Boolean statusKetersediaan) { this.statusKetersediaan = statusKetersediaan; }
}
