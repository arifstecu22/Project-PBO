package com.tokokue.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.LocalDate;

@Entity
public class Pesanan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPesanan;
    private LocalDate tanggalPesan;
    private int totalHarga;
    private String status;

    public Pesanan() {}

    public void buatPesanan() {
        this.tanggalPesan = LocalDate.now();
        this.status = "PENDING";
    }

    public void updateStatus(String statusBaru) {
        this.status = statusBaru;
    }

    // --- GETTER & SETTER MANUAL ---
    public int getIdPesanan() { return idPesanan; }
    public void setIdPesanan(int idPesanan) { this.idPesanan = idPesanan; }

    public LocalDate getTanggalPesan() { return tanggalPesan; }
    public void setTanggalPesan(LocalDate tanggalPesan) { this.tanggalPesan = tanggalPesan; }

    public int getTotalHarga() { return totalHarga; }
    public void setTotalHarga(int totalHarga) { this.totalHarga = totalHarga; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}