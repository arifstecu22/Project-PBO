package com.tokokue.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tokokue.demo.model.Produk;
import com.tokokue.demo.service.ProdukService;

@Controller
public class ViewController {

    @Autowired
    private ProdukService produkService;

    @GetMapping("/bakery")
    public String halamanUtama() {
        return "welcome"; 
    }

    @GetMapping("/bakery/login-admin")
    public String loginAdmin() {
        return "login-admin"; 
    }

    @GetMapping("/bakery/login-pelanggan")
    public String loginPelanggan() {
        return "login-pelanggan"; 
    }
    
    // Tampilan Dashboard Manajemen Admin
    @GetMapping("/bakery/admin")
    public String halamanAdmin(Model model) {
        model.addAttribute("daftarProduk", produkService.ambilSemuaProduk());
        return "index"; 
    }

    // Tampilan Katalog Menu Pelanggan Premium
    @GetMapping("/bakery/katalog")
    public String halamanPelanggan(Model model) {
        model.addAttribute("daftarProduk", produkService.ambilSemuaProduk());
        return "pelanggan"; 
    }

    // Eksekusi Simpan data baru / edit data lama
    @PostMapping("/bakery/simpan")
    public String simpan(@ModelAttribute Produk produk, @RequestParam(value = "idProdukStr", required = false) String idProdukStr) {
        if (idProdukStr == null || idProdukStr.trim().isEmpty()) {
            produk.setIdProduk(0); 
        } else {
            try {
                produk.setIdProduk(Integer.parseInt(idProdukStr));
            } catch (NumberFormatException e) {
                produk.setIdProduk(0);
            }
        }
        produkService.simpanProduk(produk);
        return "redirect:/bakery/admin"; 
    }

    @GetMapping("/bakery/hapus/{id}")
    public String hapus(@PathVariable int id) {
        produkService.hapusProduk(id);
        return "redirect:/bakery/admin"; 
    }
}