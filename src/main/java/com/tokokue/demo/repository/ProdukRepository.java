package com.tokokue.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tokokue.demo.model.Produk;

@Repository
public interface ProdukRepository extends JpaRepository<Produk, Integer> {
    // Dengan ini, kamu sudah punya fungsi Save, Delete, dan Find otomatis.
}