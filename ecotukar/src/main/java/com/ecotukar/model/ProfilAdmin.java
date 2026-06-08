package com.ecotukar.model;

import jakarta.persistence.*;

@Entity
@Table(name = "profil_admin")
public class ProfilAdmin extends ProfilPengguna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_admin")
    private Integer idAdmin;



    @Column(name = "regional_bank_sampah", length = 100)
    private String regionalBankSampah;

    public ProfilAdmin() {}

    public ProfilAdmin(Integer idAkun, String namaLengkap, String noTelepon) {
        super(idAkun, namaLengkap, noTelepon);
    }

    public Integer getIdAdmin() { return idAdmin; }
    public void setIdAdmin(Integer idAdmin) { this.idAdmin = idAdmin; }



    public String getRegionalBankSampah() { return regionalBankSampah; }
    public void setRegionalBankSampah(String regionalBankSampah) { this.regionalBankSampah = regionalBankSampah; }
}
