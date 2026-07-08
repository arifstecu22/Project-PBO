package com.tokokue.demo.model;

import jakarta.persistence.*;

@Entity
public class Pembayaran {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPembayaran;
    private String metode;
    private int jumlahBayar;
    private String status;

    public Pembayaran() {}

    public void prosesPembayaran() {
        this.status = "SUCCESS";
    }

    // --- GETTER & SETTER MANUAL ---
    public int getIdPembayaran() { return idPembayaran; }
    public void setIdPembayaran(int idPembayaran) { this.idPembayaran = idPembayaran; }

    public String getMetode() { return metode; }
    public void setMetode(String metode) { this.metode = metode; }

    public int getJumlahBayar() { return jumlahBayar; }
    public void setJumlahBayar(int jumlahBayar) { this.jumlahBayar = jumlahBayar; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}