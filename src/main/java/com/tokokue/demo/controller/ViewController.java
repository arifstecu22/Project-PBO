package com.tokokue.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.tokokue.demo.model.Pembayaran;
import com.tokokue.demo.model.Pesanan;
import com.tokokue.demo.model.Pelanggan;
import com.tokokue.demo.model.Produk;
import com.tokokue.demo.service.ProdukService;
import com.tokokue.demo.repository.PembayaranRepository;
import com.tokokue.demo.repository.PesananRepository;
import com.tokokue.demo.repository.PelangganRepository;

import java.util.Map;
import java.util.Optional;

@Controller
public class ViewController {

    @Autowired
    private ProdukService produkService;

    @Autowired
    private PembayaranRepository pembayaranRepository;

    @Autowired
    private PesananRepository pesananRepository;

    @Autowired
    private PelangganRepository pelangganRepository;

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

    // 1. HUBUNGKAN TRANSAKSI PEMBELIAN & PEMBAYARAN KE DATABASE
    @PostMapping("/bakery/api/pembayaran")
    @ResponseBody 
    public ResponseEntity<?> simpanPembayaran(@RequestBody Map<String, Object> data) {
        try {
            int totalHarga = (int) data.get("totalHarga");
            String metode = (String) data.get("metode");

            // Buat & simpan pesanan terlebih dahulu ke database
            Pesanan pesanan = new Pesanan();
            pesanan.buatPesanan();
            pesanan.setTotalHarga(totalHarga);
            Pesanan pesananSaved = pesananRepository.save(pesanan);

            // Buat & simpan detail pembayaran ke database
            Pembayaran pembayaran = new Pembayaran();
            pembayaran.setMetode(metode);
            pembayaran.setJumlahBayar(totalHarga);
            pembayaran.prosesPembayaran(); // Otomatis status jadi SUCCESS
            Pembayaran pembayaranSaved = pembayaranRepository.save(pembayaran);

            return ResponseEntity.ok(Map.of(
                "success", true,
                "idPesanan", pesananSaved.getIdPesanan(),
                "idPembayaran", pembayaranSaved.getIdPembayaran()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Gagal memproses transaksi: " + e.getMessage());
        }
    }

    // 2. PROSES DAFTAR AKUN PELANGGAN BARU (Diperbaiki: ID dibiarkan auto-generate oleh JPA/Database)
    @PostMapping("/bakery/api/register")
    public String daftarPelanggan(@RequestParam String nama, 
                                  @RequestParam String email, 
                                  @RequestParam String password) {
        try {
            Pelanggan pelanggan = new Pelanggan();
            // JANGAN di-set id(0) manual agar strateginya menggunakan Auto-Increment bawaan database
            pelanggan.setNama(nama);
            pelanggan.setEmail(email.trim());       
            pelanggan.setPassword(password.trim()); 
            pelanggan.setNoHp("Belum diisi");
            pelanggan.setAlamat("Belum diisi");
            
            pelangganRepository.save(pelanggan);
            return "redirect:/bakery/login-pelanggan?registerSuccess=true";
        } catch (Exception e) {
            System.out.println("Eror saat registrasi: " + e.getMessage());
            e.printStackTrace(); 
            return "redirect:/bakery/login-pelanggan?error=true";
        }
    }

    // 3. PROSES LOGIN PELANGGAN 
    @PostMapping("/bakery/api/login")
    public String prosesLoginPelanggan(@RequestParam String email, 
                                       @RequestParam String password, 
                                       Model model) {
        try {
            Optional<Pelanggan> pelangganOpt = pelangganRepository.findByEmail(email.trim());
            
            if (pelangganOpt.isPresent()) {
                Pelanggan dbPelanggan = pelangganOpt.get();
                if (dbPelanggan.getPassword().equals(password.trim())) {
                    return "redirect:/bakery/katalog"; 
                }
            }
            
            model.addAttribute("loginError", true);
            return "login-pelanggan";
            
        } catch (Exception e) {
            System.out.println("Eror saat login: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("loginError", true);
            return "login-pelanggan";
        }
    }

    @GetMapping("/bakery/admin")
    public String halamanAdmin(Model model) {
        model.addAttribute("daftarProduk", produkService.ambilSemuaProduk());
        return "index"; 
    }

    @GetMapping("/bakery/katalog")
    public String halamanPelanggan(Model model) {
        model.addAttribute("daftarProduk", produkService.ambilSemuaProduk());
        return "pelanggan"; 
    }

@PostMapping("/bakery/simpan")
    public String simpan(@RequestParam(value = "idProdukStr", required = false) String idProdukStr,
                         @RequestParam("namaProduk") String namaProduk,
                         @RequestParam("harga") int harga,
                         @RequestParam("stok") int stok) {
        try {
            Produk produk = new Produk();
            if (idProdukStr != null && !idProdukStr.trim().isEmpty()) {
                produk.setIdProduk(Integer.parseInt(idProdukStr.trim()));
            } 
            produk.setNamaProduk(namaProduk);
            produk.setHarga(harga);
            produk.setStok(stok);
            
            produkService.simpanProduk(produk);
            System.out.println("LOG: Berhasil simpan " + namaProduk);
            
        } catch (Exception e) {
            // SEMENTARA: Lempar error-nya keluar agar browser menampilkan pesan kesalahannya secara detail!
            throw new RuntimeException("DATABASE MENOLAK DATA! Alasan: " + e.getMessage(), e);
        }
        return "redirect:/bakery/admin"; 
    }

    @GetMapping("/bakery/hapus/{id}")
    public String hapus(@PathVariable int id) {
        produkService.hapusProduk(id);
        return "redirect:/bakery/admin"; 
    }
}