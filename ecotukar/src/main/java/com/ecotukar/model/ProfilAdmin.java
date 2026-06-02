package com.ecotukar.model;

import jakarta.persistence.*;

@Entity
@Table(name = "profil_admin")
public class ProfilAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_admin")
    private Integer idAdmin;

    @Column(name = "id_akun", nullable = false)
    private Integer idAkun;

    @Column(name = "nama_lengkap", nullable = false, length = 150)
    private String namaLengkap;

    @Column(name = "no_telepon", nullable = false, length = 15)
    private String noTelepon;

    @Column(name = "regional_bank_sampah", length = 100)
    private String regionalBankSampah;

    public ProfilAdmin() {}

    public ProfilAdmin(Integer idAkun, String namaLengkap, String noTelepon) {
        this.idAkun = idAkun;
        this.namaLengkap = namaLengkap;
        this.noTelepon = noTelepon;
    }

    public Integer getIdAdmin() { return idAdmin; }
    public void setIdAdmin(Integer idAdmin) { this.idAdmin = idAdmin; }

    public Integer getIdAkun() { return idAkun; }
    public void setIdAkun(Integer idAkun) { this.idAkun = idAkun; }

    public String getNamaLengkap() { return namaLengkap; }
    public void setNamaLengkap(String namaLengkap) { this.namaLengkap = namaLengkap; }

    public String getNoTelepon() { return noTelepon; }
    public void setNoTelepon(String noTelepon) { this.noTelepon = noTelepon; }

    public String getRegionalBankSampah() { return regionalBankSampah; }
    public void setRegionalBankSampah(String regionalBankSampah) { this.regionalBankSampah = regionalBankSampah; }
}
