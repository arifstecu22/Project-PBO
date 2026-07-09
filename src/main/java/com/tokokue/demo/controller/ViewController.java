package com.tokokue.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.tokokue.demo.model.Pembayaran;
import com.tokokue.demo.model.Pesanan;
import com.tokokue.demo.model.Pelanggan;
import com.tokokue.demo.model.Produk;
import com.tokokue.demo.service.ProdukService;
import com.tokokue.demo.repository.PembayaranRepository;
import com.tokokue.demo.repository.PesananRepository;
import com.tokokue.demo.repository.PelangganRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

@PostMapping("/bakery/api/pembayaran")
    @ResponseBody 
    @org.springframework.transaction.annotation.Transactional // Ditambahkan agar potong stok aman jika terjadi eror
    public ResponseEntity<?> simpanPembayaran(@RequestBody Map<String, Object> data) {
        try {
            int totalHarga = (int) data.get("totalHarga");
            String metode = (String) data.get("metode");
            java.util.List<Map<String, Object>> itemBelanja = (java.util.List<Map<String, Object>>) data.get("itemBelanja");

            // 1. Validasi stok semua produk terlebih dahulu sebelum memotong
            if (itemBelanja != null) {
                for (Map<String, Object> item : itemBelanja) {
                    String namaKue = (String) item.get("nama");
                    int jumlahBeli = (int) item.get("jumlah");

                    // Cari produk berdasarkan nama produk di DB
                    Produk produk = produkService.ambilSemuaProduk().stream()
                            .filter(p -> p.getNamaProduk().equalsIgnoreCase(namaKue))
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("Produk " + namaKue + " tidak ditemukan!"));

                    // Cek stok cukup atau tidak
                    if (produk.getStok() < jumlahBeli) {
                        return ResponseEntity.badRequest().body("Transaksi gagal! Stok kue '" + namaKue + "' tidak mencukupi (Sisa stok: " + produk.getStok() + ").");
                    }

                    // Potong stok dan simpan perubahan ke database produk
                    produk.setStok(produk.getStok() - jumlahBeli);
                    produkService.simpanProduk(produk);
                }
            }

            // 2. Simpan Data Pesanan
            Pesanan pesanan = new Pesanan();
            pesanan.buatPesanan();
            pesanan.setTotalHarga(totalHarga);
            Pesanan pesananSaved = pesananRepository.save(pesanan);

            // 3. Simpan Data Pembayaran
            Pembayaran pembayaran = new Pembayaran();
            pembayaran.setMetode(metode);
            pembayaran.setJumlahBayar(totalHarga);
            pembayaran.prosesPembayaran(); 
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

    @PostMapping("/bakery/api/register")
    public String daftarPelanggan(@RequestParam String nama, 
                                  @RequestParam String email, 
                                  @RequestParam String password) {
        try {
            Pelanggan pelanggan = new Pelanggan();
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
                         @RequestParam("stok") int stok,
                         @RequestParam("fileGambar") MultipartFile fileGambar) {
        try {
            Produk produk = new Produk();
            boolean isEdit = false;

            if (idProdukStr != null && !idProdukStr.trim().isEmpty()) {
                produk.setIdProduk(Integer.parseInt(idProdukStr.trim()));
                isEdit = true;
            } 
            produk.setNamaProduk(namaProduk);
            produk.setHarga(harga);
            produk.setStok(stok);
            
            if (fileGambar != null && !fileGambar.isEmpty()) {
                String userDir = System.getProperty("user.dir");
                Path pathFolder = Paths.get(userDir, "src", "main", "resources", "static", "images");
                
                if (!Files.exists(pathFolder)) {
                    Files.createDirectories(pathFolder);
                }
                
                String ekstensi = ".jpg";
                String namaAsli = fileGambar.getOriginalFilename();
                if (namaAsli != null && namaAsli.contains(".")) {
                    ekstensi = namaAsli.substring(namaAsli.lastIndexOf("."));
                }
                String namaFileBersih = namaProduk.toLowerCase().replaceAll("[^a-zA-Z0-9]", "_") + ekstensi;
                
                // DISINI SUDAH DISATUKAN TANPA SPASI
                Path pathFileLengkap = pathFolder.resolve(namaFileBersih);
                Files.copy(fileGambar.getInputStream(), pathFileLengkap, StandardCopyOption.REPLACE_EXISTING);
                
                produk.setGambar("/images/" + namaFileBersih);
            } else {
                if (isEdit) {
                    Produk produkLama = produkService.ambilSemuaProduk().stream()
                            .filter(p -> p.getIdProduk() == produk.getIdProduk())
                            .findFirst().orElse(null);
                    if (produkLama != null && produkLama.getGambar() != null) {
                        produk.setGambar(produkLama.getGambar());
                    } else {
                        produk.setGambar("/images/default-kue.jpg");
                    }
                } else {
                    produk.setGambar("/images/default-kue.jpg");
                }
            }
            
            produkService.simpanProduk(produk);
            System.out.println("LOG: Berhasil menyimpan produk " + namaProduk);
            
        } catch (IOException e) {
            System.out.println("EROR IO: Gagal menyalin file fisik gambar -> " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Gagal menulis berkas gambar: " + e.getMessage(), e);
        } catch (Exception e) {
            System.out.println("EROR DATABASE: " + e.getMessage());
            e.printStackTrace();
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