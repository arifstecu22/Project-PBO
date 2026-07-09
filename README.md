# Toko Kue Jihan

Aplikasi manajemen **toko kue online** berbasis web yang dibangun dengan **Spring Boot**, **Thymeleaf**, dan **H2 Database**. Aplikasi ini menyediakan dashboard untuk Admin dalam mengelola produk, serta katalog belanja untuk Pelanggan lengkap dengan proses pemesanan dan pembayaran.

Proyek ini dibuat untuk memenuhi tugas mata kuliah **Pemrograman Berorientasi Objek (PBO)**.

> **Kelompok:** *()*
> **Anggota:**
> - *(Bintang Wafii Al-Farisi - 41524010181)*
> - *(Arif Suryo Utomo - 41524010150)*
> - *(Rafly Prasetya Romahdoni - 41524010122)*

---

## Tech Stack

| Komponen      | Teknologi                          |
| ------------- | ----------------------------------- |
| Backend       | Spring Boot 4.0 / Java 25           |
| Template View | Thymeleaf                           |
| Database      | H2 Database (file-based)            |
| ORM           | Spring Data JPA / Hibernate         |
| Build Tool    | Maven                               |
| Utilitas      | Lombok                              |

---

## Fitur Utama

- **Login terpisah** untuk Admin dan Pelanggan
- **Registrasi akun** Pelanggan baru
- **CRUD Produk** (tambah, lihat, hapus) lengkap dengan upload gambar kue
- **Katalog produk** untuk Pelanggan dengan info stok & harga
- **Proses pemesanan & pembayaran** yang otomatis memotong stok produk
- **H2 Console** untuk inspeksi database secara langsung

---

## Struktur Proyek

```
src/main/java/com/tokokue/demo/
├── DemoApplication.java       # Entry point aplikasi
├── controller/
│   ├── ViewController.java        # Routing halaman (welcome, login, katalog, dsb)
│   ├── ProdukController.java      # REST API produk
│   ├── AdminRestController.java   # REST API login admin
│   └── WebConfig.java             # Konfigurasi resource gambar statis
├── model/
│   ├── User.java, Admin.java, Pelanggan.java   # Entity akun (inheritance JOINED)
│   ├── Produk.java, Kategori.java              # Entity produk
│   └── Pesanan.java, Pembayaran.java           # Entity transaksi
├── repository/     # Interface Spring Data JPA (Admin, Pelanggan, Produk, Pesanan, Pembayaran)
└── service/
    └── ProdukService.java         # Business logic produk

src/main/resources/
├── templates/       # Halaman Thymeleaf (welcome, login-admin, login-pelanggan, index, pelanggan)
├── static/images/    # Gambar produk
├── application.properties
└── import.sql        # Data awal (seed) produk & admin
```

---

## Cara Menjalankan

### 1. Prasyarat

- JDK 25+
- Maven 3.8+ (atau gunakan `mvnw` yang sudah disertakan)

### 2. Clone / Ekstrak Proyek

```bash
cd pbo
```

### 3. Jalankan Aplikasi

**Menggunakan Maven Wrapper (disarankan):**

```bash
./mvnw spring-boot:run
```

**Windows:**

```bash
mvnw.cmd spring-boot:run
```

Aplikasi akan berjalan di **http://localhost:8080**

### 4. Build JAR (opsional)

```bash
./mvnw clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

---

## Halaman & Endpoint

| Route                        | Deskripsi                          | Method |
| ----------------------------- | ----------------------------------- | ------ |
| `/bakery`                     | Halaman selamat datang              | GET    |
| `/bakery/login-admin`         | Halaman login Admin                 | GET    |
| `/bakery/login-pelanggan`     | Halaman login & daftar Pelanggan    | GET    |
| `/bakery/admin`                | Dashboard Admin (kelola produk)     | GET    |
| `/bakery/katalog`             | Katalog produk untuk Pelanggan      | GET    |
| `/bakery/simpan`              | Simpan/edit produk (dengan gambar)  | POST   |
| `/bakery/hapus/{id}`          | Hapus produk                        | GET    |
| `/bakery/api/register`        | Registrasi akun Pelanggan baru      | POST   |
| `/bakery/api/login`           | Proses login Pelanggan              | POST   |
| `/bakery/api/admin/login`     | Proses login Admin (REST)           | POST   |
| `/bakery/api/pembayaran`      | Proses pesanan & pembayaran         | POST   |
| `/api/produk`                 | REST API daftar & tambah produk     | GET/POST |

---

## Akun Contoh (Seed Data)

Data berikut sudah otomatis ditambahkan lewat `import.sql` saat aplikasi pertama kali dijalankan:

**Admin:**

| Nama    | Email                | Password  |
| ------- | --------------------- | --------- |
| Arif    | arif@bakery.com        | admin123  |
| Rafly   | rafly@bakery.com       | admin123  |
| Bintang | bintang@bakery.com     | admin123  |

**Produk Awal:** Nastar, Putri Salju, Kue Kacang

---

## Database (H2 Console)

Untuk melihat isi database secara langsung:

1. Jalankan aplikasi
2. Buka **http://localhost:8080/h2-console**
3. Gunakan JDBC URL: `jdbc:h2:file:./data/tokokuedb`
4. Username: `sa`, Password: *(kosong)*

---

## Catatan

- Data disimpan secara persisten di folder `data/` (file H2), bukan in-memory, sehingga data tidak hilang saat aplikasi di-restart.
- Gambar produk yang diupload melalui dashboard Admin akan disimpan di `src/main/resources/static/images/`.