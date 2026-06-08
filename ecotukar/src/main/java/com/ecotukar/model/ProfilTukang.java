package com.ecotukar.model;

import jakarta.persistence.*;

@Entity
@Table(name = "profil_tukang")
public class ProfilTukang extends ProfilPengguna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tukang")
    private Integer idTukang;



    @Column(name = "plat_kendaraan", length = 20)
    private String platKendaraan;

    @Column(name = "status_ketersediaan")
    private Boolean statusKetersediaan = true;

    public ProfilTukang() {}

    public ProfilTukang(Integer idAkun, String namaLengkap, String noTelepon) {
        super(idAkun, namaLengkap, noTelepon);
        this.statusKetersediaan = true;
    }

    public Integer getIdTukang() { return idTukang; }
    public void setIdTukang(Integer idTukang) { this.idTukang = idTukang; }



    public String getPlatKendaraan() { return platKendaraan; }
    public void setPlatKendaraan(String platKendaraan) { this.platKendaraan = platKendaraan; }

    public Boolean getStatusKetersediaan() { return statusKetersediaan; }
    public void setStatusKetersediaan(Boolean statusKetersediaan) { this.statusKetersediaan = statusKetersediaan; }
}
