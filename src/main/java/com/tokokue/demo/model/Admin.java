package com.tokokue.demo.model;

public class Admin extends User {
    public Admin(int id, String nama, String email, String password, String noHp) {
        super(id, nama, email, password, noHp);
    }

    public void kelolaProduk() {
        System.out.println("Admin " + nama + " sedang mengelola stok bakery.");
    }
}