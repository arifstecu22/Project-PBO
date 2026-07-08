package com.tokokue.demo.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity // Tambahkan @Entity agar sinkron dengan tabel User
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Admin extends User {
    
    public Admin(int id, String nama, String email, String password, String noHp) {
        super(id, nama, email, password, noHp);
    }

    public void kelolaProduk() {
        System.out.println("Admin " + nama + " sedang mengelola stok bakery.");
    }
}