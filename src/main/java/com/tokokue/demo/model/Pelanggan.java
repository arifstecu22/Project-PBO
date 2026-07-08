package com.tokokue.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "PELANGGAN")
public class Pelanggan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nama;
    private String email;
    private String password;
    private String noHp;
    private String alamat;

    // Konstruktor Kosong
    public Pelanggan() {}

    // Konstruktor Lengkap
    public Pelanggan(int id, String nama, String email, String password, String noHp, String alamat) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.noHp = noHp;
        this.alamat = alamat;
    }

    // --- GETTER & SETTER MANUAL (PASTI AMAN) ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNoHp() { return noHp; }
    public void setNoHp(String noHp) { this.noHp = noHp; }

    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
}