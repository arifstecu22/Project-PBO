package com.tokokue.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ADMIN")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String nama;
    private String email;
    private String password;
    @Column(name = "no_hp")
    private String noHp;

    // Konstruktor Kosong
    public Admin() {}

    // Konstruktor Manual
    public Admin(String nama, String email, String password, String noHp) {
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.noHp = noHp;
    }

    // --- GETTER & SETTER ---
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNoHp() { return noHp; }
    public void setNoHp(String noHp) { this.noHp = noHp; }
}