package com.tokokue.demo.model;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id; // Ubah dari 'int' ke 'Integer' agar bisa bernilai null jika data baru

    protected String nama;
    protected String email;
    protected String password;
    protected String noHp;

    // Konstruktor Kosong (Wajib untuk JPA/Hibernate)
    public User() {}

    // Konstruktor Manual
    public User(Integer id, String nama, String email, String password, String noHp) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.noHp = noHp;
    }

    // --- GETTER & SETTER MANUAL ---
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