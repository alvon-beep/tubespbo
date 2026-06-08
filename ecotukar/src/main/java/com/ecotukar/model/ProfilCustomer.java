package com.ecotukar.model;

import jakarta.persistence.*;

@Entity
@Table(name = "profil_customer")
public class ProfilCustomer extends ProfilPengguna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_customer")
    private Integer idCustomer;



    @Column(name = "alamat_lengkap", nullable = false, columnDefinition = "TEXT")
    private String alamatLengkap = "";

    @Column(name = "saldo_poin")
    private Integer saldoPoin = 0;

    @Column(name = "koordinat_maps", length = 100)
    private String koordinatMaps;

    public ProfilCustomer() {}

    public ProfilCustomer(Integer idAkun, String namaLengkap, String noTelepon, String alamatLengkap) {
        super(idAkun, namaLengkap, noTelepon);
        this.alamatLengkap = alamatLengkap;
        this.saldoPoin = 0;
    }

    public Integer getIdCustomer() { return idCustomer; }
    public void setIdCustomer(Integer idCustomer) { this.idCustomer = idCustomer; }



    public String getAlamatLengkap() { return alamatLengkap; }
    public void setAlamatLengkap(String alamatLengkap) { this.alamatLengkap = alamatLengkap; }

    public Integer getSaldoPoin() { return saldoPoin; }
    public void setSaldoPoin(Integer saldoPoin) { this.saldoPoin = saldoPoin; }

    public String getKoordinatMaps() { return koordinatMaps; }
    public void setKoordinatMaps(String koordinatMaps) { this.koordinatMaps = koordinatMaps; }
}
