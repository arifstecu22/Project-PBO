package com.tokokue.demo.controller;

import com.tokokue.demo.model.Admin;
import com.tokokue.demo.repository.AdminRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/bakery/api/admin")
public class AdminRestController {

    private final AdminRepository adminRepository;

    public AdminRestController(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> prosesLogin(@RequestBody Map<String, String> payload) {
        Map<String, Object> response = new HashMap<>();

        // Proteksi dari data null / kosong
        if (payload == null || payload.get("username") == null || payload.get("password") == null) {
            response.put("success", false);
            response.put("message", "Nama Admin atau Password tidak boleh kosong! ❌");
            return ResponseEntity.ok(response);
        }

        String username = payload.get("username").trim();
        String password = payload.get("password");
        
        // Cari data langsung di tabel admin berdasarkan nama user
        Optional<Admin> adminOpt = adminRepository.findByNamaCaseInsensitive(username);
        
        if (adminOpt.isPresent()) {
            Admin admin = adminOpt.get();
            // Cocokkan password
            if (admin.getPassword() != null && admin.getPassword().equals(password)) {
                response.put("success", true);
                response.put("message", "Akses diterima!");
                return ResponseEntity.ok(response);
            }
        }
        
        // Jika tidak ditemukan di tabel admin atau password salah
        response.put("success", false);
        response.put("message", "Nama Admin atau Password salah! ❌");
        return ResponseEntity.ok(response);
    }
}