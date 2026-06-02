package com.ecotukar.model;

import jakarta.persistence.*;

@Entity
@Table(name = "profil_customer")
public class ProfilCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_customer")
    private Integer idCustomer;

    @Column(name = "id_akun", nullable = false)
    private Integer idAkun;

    @Column(name = "nama_lengkap", nullable = false, length = 150)
    private String namaLengkap;

    @Column(name = "no_telepon", nullable = false, length = 15)
    private String noTelepon;

    @Column(name = "alamat_lengkap", nullable = false, columnDefinition = "TEXT")
    private String alamatLengkap = "";

    @Column(name = "saldo_poin")
    private Integer saldoPoin = 0;

    @Column(name = "koordinat_maps", length = 100)
    private String koordinatMaps;

    public ProfilCustomer() {}

    public ProfilCustomer(Integer idAkun, String namaLengkap, String noTelepon, String alamatLengkap) {
        this.idAkun = idAkun;
        this.namaLengkap = namaLengkap;
        this.noTelepon = noTelepon;
        this.alamatLengkap = alamatLengkap;
        this.saldoPoin = 0;
    }

    public Integer getIdCustomer() { return idCustomer; }
    public void setIdCustomer(Integer idCustomer) { this.idCustomer = idCustomer; }

    public Integer getIdAkun() { return idAkun; }
    public void setIdAkun(Integer idAkun) { this.idAkun = idAkun; }

    public String getNamaLengkap() { return namaLengkap; }
    public void setNamaLengkap(String namaLengkap) { this.namaLengkap = namaLengkap; }

    public String getNoTelepon() { return noTelepon; }
    public void setNoTelepon(String noTelepon) { this.noTelepon = noTelepon; }

    public String getAlamatLengkap() { return alamatLengkap; }
    public void setAlamatLengkap(String alamatLengkap) { this.alamatLengkap = alamatLengkap; }

    public Integer getSaldoPoin() { return saldoPoin; }
    public void setSaldoPoin(Integer saldoPoin) { this.saldoPoin = saldoPoin; }

    public String getKoordinatMaps() { return koordinatMaps; }
    public void setKoordinatMaps(String koordinatMaps) { this.koordinatMaps = koordinatMaps; }
}
