package com.tokokue.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDate; // Library tanggal yang lebih modern

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pesanan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPesanan;
    
    private LocalDate tanggalPesan; // Pengganti @Temporal Date
    
    private int totalHarga;
    private String status;

    // Method sesuai dokumen analisis (Laporan Hlm 3 & 6)
    public void buatPesanan() {
        this.tanggalPesan = LocalDate.now(); // Mengambil tanggal hari ini
        this.status = "PENDING";
    }

    public void updateStatus(String statusBaru) {
        this.status = statusBaru;
    }
}