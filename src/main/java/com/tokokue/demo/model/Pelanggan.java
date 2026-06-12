package com.tokokue.demo.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity // Tambahkan ini agar muncul di H2 Console
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Pelanggan extends User {
    private String alamat;

    public Pelanggan(int id, String nama, String email, String password, String noHp, String alamat) {
        super(id, nama, email, password, noHp);
        this.alamat = alamat;
    }
}