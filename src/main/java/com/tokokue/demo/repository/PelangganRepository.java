package com.tokokue.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.tokokue.demo.model.Pelanggan;
import java.util.Optional;

@Repository
public interface PelangganRepository extends JpaRepository<Pelanggan, Integer> {
    // Fungsi ini sekarang otomatis bekerja sempurna karena kolom email sudah ada di tabel PELANGGAN
    Optional<Pelanggan> findByEmail(String email);
}