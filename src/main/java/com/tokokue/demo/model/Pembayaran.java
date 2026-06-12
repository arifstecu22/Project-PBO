package com.tokokue.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pembayaran {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPembayaran;
    
    private String metode;
    private int jumlahBayar;
    private String status;

    // Method dari laporan (Hlm 4)
    public void prosesPembayaran() {
        this.status = "SUCCESS";
    }
}