-- Hapus data lama (jika ada) untuk memastikan state bersih saat aplikasi running
DELETE FROM admin;
DELETE FROM user;
DELETE FROM produk;

-- 1. Insert Produk
INSERT INTO produk (id, nama_produk, harga, stok, gambar) VALUES (1, 'nastar', 50000, 15, '/images/nastar.png');
INSERT INTO produk (id, nama_produk, harga, stok, gambar) VALUES (2, 'putri salju', 39000, 20, '/images/putri_salju.jpg');
INSERT INTO produk (id, nama_produk, harga, stok, gambar) VALUES (3, 'kue kacang', 40000, 12, '/images/kue_kacang.jpg');  

-- 2. Insert User (Biarkan database mengisi ID secara otomatis jika memungkinkan, 
-- namun karena strategi JOINED membutuhkan ID yang sinkron di script SQL awal, kita pastikan nilainya eksplisit)
INSERT INTO user (id, nama, email, password, no_hp) VALUES (1, 'Arif', 'arif@bakery.com', 'admin123', '0812345678');
INSERT INTO user (id, nama, email, password, no_hp) VALUES (2, 'Rafly', 'rafly@bakery.com', 'admin123', '0812345679');
INSERT INTO user (id, nama, email, password, no_hp) VALUES (3, 'Bintang', 'bintang@bakery.com', 'admin123', '0812345680');

-- 3. Hubungkan ID ke tabel Admin
INSERT INTO admin (id) VALUES (1);
INSERT INTO admin (id) VALUES (2);
INSERT INTO admin (id) VALUES (3);

-- 4. TRICK UTAMA: Reset sequence/auto-increment database agar tidak tabrakan dengan ID manual di atas
-- Jika kamu pakai Database H2 (Default Spring Boot):
ALTER TABLE user ALTER COLUMN id RESTART WITH 4;

-- ATAU Jika kamu pakai Database MySQL, aktifkan baris di bawah ini dan matikan baris H2 di atas:
-- ALTER TABLE user AUTO_INCREMENT = 4;