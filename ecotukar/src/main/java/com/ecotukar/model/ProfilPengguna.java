package com.ecotukar.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class ProfilPengguna {

    @Column(name = "id_akun", nullable = false)
    private Integer idAkun;

    @Column(name = "nama_lengkap", nullable = false, length = 150)
    private String namaLengkap;

    @Column(name = "no_telepon", nullable = false, length = 15)
    private String noTelepon;

    public ProfilPengguna() {}

    public ProfilPengguna(Integer idAkun, String namaLengkap, String noTelepon) {
        this.idAkun = idAkun;
        this.namaLengkap = namaLengkap;
        this.noTelepon = noTelepon;
    }

    public Integer getIdAkun() {
        return idAkun;
    }

    public void setIdAkun(Integer idAkun) {
        this.idAkun = idAkun;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getNoTelepon() {
        return noTelepon;
    }

    public void setNoTelepon(String noTelepon) {
        this.noTelepon = noTelepon;
    }
}
