package com.tokokue.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tokokue.demo.model.Pembayaran;

@Repository
public interface PembayaranRepository extends JpaRepository<Pembayaran, Integer> {
    // Sama seperti ProdukRepository, interface ini otomatis menyediakan 
    // fungsi .save() untuk menyimpan transaksi pembayaran ke database.
}