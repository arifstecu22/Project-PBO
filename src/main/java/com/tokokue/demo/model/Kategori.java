package com.tokokue.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Ini otomatis buatin Getter, Setter, dan toString
@AllArgsConstructor // Ini otomatis buatin Constructor untuk semua field
@NoArgsConstructor // Ini buatin constructor kosong (penting buat JPA/Database)
public class Kategori {
    private int idKategori;
    private String namaKategori;
}