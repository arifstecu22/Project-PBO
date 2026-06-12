package com.tokokue.demo.service;

import com.tokokue.demo.model.Produk;
import com.tokokue.demo.repository.ProdukRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProdukService {
    @Autowired
    private ProdukRepository produkRepository;

    public List<Produk> ambilSemuaProduk() {
        return produkRepository.findAll();
    }

    public void simpanProduk(Produk produk) {
        produkRepository.save(produk);
    }

    // Tambahkan ini untuk Delete
    public void hapusProduk(int id) {
        produkRepository.deleteById(id);
    }
}