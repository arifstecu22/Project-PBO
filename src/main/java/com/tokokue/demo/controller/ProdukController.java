package com.tokokue.demo.controller;

import com.tokokue.demo.model.Produk;
import com.tokokue.demo.service.ProdukService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produk") // URL Endpoint sesuai Soal No. 1
public class ProdukController {

    @Autowired
    private ProdukService produkService;

    // Endpoint GET untuk mengambil semua produk (Read)
    @GetMapping
    public List<Produk> getAll() {
        return produkService.ambilSemuaProduk();
    }

    // Endpoint POST untuk menambah produk baru (Create)
    @PostMapping
    public void add(@RequestBody Produk produk) {
        produkService.simpanProduk(produk);
    }
}