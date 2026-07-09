Toko Kue Jihan

Aplikasi manajemen toko kue online berbasis web yang dibangun dengan Spring Boot, Thymeleaf, dan H2 Database. Aplikasi ini menyediakan dashboard untuk Admin dalam mengelola produk, serta katalog belanja untuk Pelanggan lengkap dengan proses pemesanan dan pembayaran.

Proyek ini dibuat untuk memenuhi tugas mata kuliah Pemrograman Berorientasi Objek (PBO).


Kelompok: (isi nama kelompok)
Anggota:


(Nama Mahasiswa 1 - NIM)
(Nama Mahasiswa 2 - NIM)
(Nama Mahasiswa 3 - NIM)





Tech Stack

KomponenTeknologiBackendSpring Boot 4.0 / Java 25Template ViewThymeleafDatabaseH2 Database (file-based)ORMSpring Data JPA / HibernateBuild ToolMavenUtilitasLombok


Fitur Utama


Login terpisah untuk Admin dan Pelanggan
Registrasi akun Pelanggan baru
CRUD Produk (tambah, lihat, hapus) lengkap dengan upload gambar kue
Katalog produk untuk Pelanggan dengan info stok & harga
Proses pemesanan & pembayaran yang otomatis memotong stok produk
H2 Console untuk inspeksi database secara langsung



Struktur Proyek

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


Cara Menjalankan

1. Prasyarat


JDK 25+
Maven 3.8+ (atau gunakan mvnw yang sudah disertakan)


2. Clone / Ekstrak Proyek

bashcd pbo

3. Jalankan Aplikasi

Menggunakan Maven Wrapper (disarankan):

bash./mvnw spring-boot:run

Windows:

bashmvnw.cmd spring-boot:run

Aplikasi akan berjalan di http://localhost:8080

4. Build JAR (opsional)

bash./mvnw clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar


Halaman & Endpoint

RouteDeskripsiMethod/bakeryHalaman selamat datangGET/bakery/login-adminHalaman login AdminGET/bakery/login-pelangganHalaman login & daftar PelangganGET/bakery/adminDashboard Admin (kelola produk)GET/bakery/katalogKatalog produk untuk PelangganGET/bakery/simpanSimpan/edit produk (dengan gambar)POST/bakery/hapus/{id}Hapus produkGET/bakery/api/registerRegistrasi akun Pelanggan baruPOST/bakery/api/loginProses login PelangganPOST/bakery/api/admin/loginProses login Admin (REST)POST/bakery/api/pembayaranProses pesanan & pembayaranPOST/api/produkREST API daftar & tambah produkGET/POST


Akun Contoh (Seed Data)

Data berikut sudah otomatis ditambahkan lewat import.sql saat aplikasi pertama kali dijalankan:

Admin:

NamaEmailPasswordArifarif@bakery.comadmin123Raflyrafly@bakery.comadmin123Bintangbintang@bakery.comadmin123

Produk Awal: Nastar, Putri Salju, Kue Kacang


Database (H2 Console)

Untuk melihat isi database secara langsung:


Jalankan aplikasi
Buka http://localhost:8080/h2-console
Gunakan JDBC URL: jdbc:h2:file:./data/tokokuedb
Username: sa, Password: (kosong)



Catatan


Data disimpan secara persisten di folder data/ (file H2), bukan in-memory, sehingga data tidak hilang saat aplikasi di-restart.
Gambar produk yang diupload melalui dashboard Admin akan disimpan di src/main/resources/static/images/.