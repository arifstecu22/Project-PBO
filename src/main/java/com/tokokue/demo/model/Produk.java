package com.tokokue.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "PRODUK")
public class Produk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProduk; // Menyesuaikan p.idProduk di HTML
    private String namaProduk;
    private int harga;
    private int stok;

    // Konstruktor Kosong
    public Produk() {}

    // Konstruktor Lengkap
    public Produk(int idProduk, String namaProduk, int harga, int stok) {
        this.idProduk = idProduk;
        this.namaProduk = namaProduk;
        this.harga = harga;
        this.stok = stok;
    }

    // Tambahkan atribut ini di dalam kelas Produk.java
private String gambar;

// Tambahkan juga Getter dan Setter-nya (atau jika pakai Lombok, tidak perlu)
public String getGambar() {
    return gambar;
}

public void setGambar(String gambar) {
    this.gambar = gambar;
}

    // --- GETTER & SETTER MANUAL ---
    public int getIdProduk() { return idProduk; }
    public void setIdProduk(int idProduk) { this.idProduk = idProduk; }

    public String getNamaProduk() { return namaProduk; }
    public void setNamaProduk(String namaProduk) { this.namaProduk = namaProduk; }

    public int getHarga() { return harga; }
    public void setHarga(int harga) { this.harga = harga; }

    public int getStok() { return stok; }
    public void setStok(int stok) { this.stok = stok; }
}