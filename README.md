# Ecotukar

Ecotukar adalah sebuah platform untuk *waste management* dengan fitur poin. Dengan banyaknya sampah di Indonesia, ecotukar adalah solusi kami untuk mengurangi sampah, meningkatkan partisipasi masyarakat dalam pengelolaan sampah, dan memberikan penghargaan kepada mereka yang peduli lingkungan.

Proyek ini dibuat sebagai Tugas Besar untuk Mata Kuliah Pemrograman Berorientasi Objek, dengan tujuan untuk menunjukkan ciri-ciri dari OOP, termasuk Abstraksi, Enkapsulasi, Inheritance, Polimorfisme

## Cara run

### Prasyarat

Pastikan aplikasi berikut sudah terinstall:

1. **Java JDK 21** — [download](https://www.oracle.com/java/technologies/downloads/#java21)
2. **Maven** — [download](https://maven.apache.org/download.cgi)
3. **XAMPP** (untuk MySQL) — [download](https://www.apachefriends.org)

Selain itu, daftarkan diri di [openrouteservice.org](https://openrouteservice.org/) untuk mendapatkan API Key gratis. Ini berfungsi untuk routing

### Instalasi

1. Buka XAMPP dan klik **Start** pada MySQL.
2. Buka **phpMyAdmin** (`http://localhost/phpmyadmin`) dan buat database baru bernama `ecotukar`.
3. Buka file `ecotukar/src/main/resources/application.properties.example`, copy, rename menjadi `application.properties`, lalu isi API Key ORS kamu:
   ```
   ors.api.key=MASUKKAN_API_KEY_KAMU_DISINI
   ```
4. Masuk ke folder `ecotukar/` dan jalankan:
   ```
   mvn spring-boot:run
   ```
   Hibernate akan otomatis membuat tabel dan mengisi data awal (tidak perlu import SQL manual).
5. Buka browser dan akses `http://localhost:8080`

## Akun Default

| Username | Password | Role |
|---|---|---|
| `admin` | `password` | Admin |
| `budi` | `password` | Kurir |
| `customer1` | `password` | Customer |

---
