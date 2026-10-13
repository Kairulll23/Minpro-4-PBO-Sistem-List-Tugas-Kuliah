# Minpro-4-PBO-SistemListTugasKuliah

## Judul : Sistem List Tugas Kuliah Berbasis Database (Mini Project 4)

---

## 1. Deskripsi Singkat Program  
Pada program ini, sistem manajemen list tugas kuliah dikembangkan lebih lanjut dari versi sebelumnya dengan menghubungkannya ke Database MySQL menggunakan JDBC (PreparedStatement) dan pola desain DAO (Data Access Object). Program berbasis CLI ini membantu mahasiswa mengelola, mencatat, memantau status penyelesaian, memperbarui, serta menghapus tugas perkuliahan secara persisten. Di dalam sistem ini, data awal (dummy data) wajib diisi langsung melalui file SQL eksternal agar saat menu Read dijalankan, data langsung tampil dari database tanpa harus diinput ulang dari awal. Arsitektur MVC (Model-View-Controller), prinsip OOP (Abstraction, Inheritance, Polymorphism, Interface), keyword final, Auto-ID Generator, serta validasi input ketat tetap dipertahankan untuk menjaga keandalan aplikasi.

---  

## 2. Penjelasan Class, Interface & Atribut  
Struktur project dibagi ke dalam arsitektur MVC dan DAO berbasis package:

Package config:
* Database
  * URL, USER, PASSWORD, method getConnection()

Package dao:
* TugasDAO (interface)
  * Method contract CRUD & database operations
* TugasDAOImpl (class implementation)
  * Implementasi PreparedStatement JDBC

Package model:
* PenyimpananData (interface)
  * Method contract: getKategoriFormat(), cetakBaris()
* EntitasTugas (superclass / abstract class)
  * kodeTugas (final)
  * namaMatkul
  * detailTugas
  * dateline
  * isSelesai
* TugasIndividu (subclass 1)
  * platformPengumpulan
* TugasKelompok (subclass 2)
  * jumlahAnggota
* Mahasiswa
  * nim (final)
  * nama (final)

Package controller:
* TugasController
  * tugasDAO (TugasDAO)
  * PREFIX (static final String)

Package view:
* MainView
  * controller (final TugasController)
  * scan (final Scanner)

Package Main:
* main (Entry point)

---

## 3. Penjelasan Tiap Class, Interface & Konfigurasi

* Database (config)
  * Mengelola koneksi driver JDBC ke database MySQL secara terpusat.
* TugasDAO & TugasDAOImpl (dao)
  * Memisahkan logika eksekusi query SQL (INSERT, SELECT, UPDATE, DELETE) dari controller menggunakan PreparedStatement untuk mencegah SQL Injection.
* PenyimpananData (Interface)
  * Berkontrak standar format data keluaran agar setiap entitas tugas mencetak baris data secara konsisten.
* EntitasTugas (Abstract Class)
  * Superclass utama pengimplementasi PenyimpananData yang menampung atribut umum tugas kuliah serta abstract method getTipeTugas().
* TugasIndividu & TugasKelompok (Subclass)
  * Kelas turunan yang mengimplementasikan method overriding untuk mencetak format tabel dan atribut spesifik masing-masing kategori.
* Mahasiswa
  * Menyimpan identitas pengguna dengan atribut final (immutable).
* TugasController
  * Menghubungkan View dengan DAO, menangani pembuatan ID otomatis berbasis database, serta logika pemrosesan data.
* MainView
  * Mengelola antarmuka CLI, dashboard statistik real-time dari database, serta validasi input yang ketat.
* main
  * Entry point aplikasi untuk menginisialisasi objek mahasiswa dan menjalankan menu utama.

---  

## 4. Penjelasan Penerapan Database, DAO, & Pilar OOP  

* JDBC & Prepared Statement DAO
  * Seluruh operasi manipulasi data dilakukan secara persisten ke database menggunakan PreparedStatement untuk keamanan dan performa query.
* Database Dummy Data (database.sql)
  * Menyediakan data awal minimal 1-2 baris yang dimasukkan langsung melalui file SQL agar langsung termuat saat aplikasi dijalankan.
* Abstraction & Polymorphism
  * Penggunaan Abstract Class dan Interface memastikan struktur pewarisan objek yang rapi, didukung Method Overriding pada subclass serta Method Overloading pada pencarian mata kuliah di Controller.
* Encapsulation & Keyword final
  * Atribut model dienkapsulasi dengan private access modifier serta penerapan final pada identitas mutlak dan konstanta sistem.

---

## 5. Penjelasan Alur Program dan Dokumentasi Output  
Saat aplikasi dijalankan, sistem mengambil data dari database secara langsung dan menampilkan dashboard statistik tugas di atas menu utama.

Menu utama menyediakan 7 pilihan interaktif:

1. Tambah Data Tugas Baru (Create):
   Sistem membuat Kode Tugas secara otomatis dari database (Auto ID seperti TGS-003). Pengguna memilih jenis tugas dan memasukkan detail data yang langsung disimpan ke database.
   <br>
   <p align="center">
     <img width="585" height="545" alt="image" src="https://github.com/user-attachments/assets/63a3723f-94e0-4475-96af-f62bad78da9d" />
   </p>

2. Tampilkan Daftar Tugas (Read):
   Menampilkan tabel daftar seluruh tugas dari database beserta atribut spesifik dan status pengerjaannya.
   <br>
   <p align="center">
     <img width="1171" height="397" alt="image" src="https://github.com/user-attachments/assets/8156c6a0-cb74-4dac-9c66-6a1cae3509f1" />
   </p>

3. Cari Tugas berdasarkan Mata Kuliah:
   Memfilter dan menampilkan daftar tugas sesuai mata kuliah yang dicari dari database.
   <br>
   <p align="center">
     <img width="1092" height="397" alt="image" src="https://github.com/user-attachments/assets/60ef9733-c892-4345-b3af-97efe036a3fd" />
   </p>

4. Tandai Status Tugas (Task Tracking):
   Memperbarui status tugas (BELUM [x] menjadi SELESAI [v]) secara langsung di database dan memperbarui angka pada Dashboard Statistik.
   <br>
   <p align="center">
     <img width="1180" height="542" alt="image" src="https://github.com/user-attachments/assets/e5a909e5-12b4-44a2-8d31-b1e69c86ed94" />
   </p>

5. Ubah Data Tugas (Update):
   Menampilkan tabel tugas terlebih dahulu sebelum memperbarui data entitas ke database berdasarkan Kode Tugas.
   <br>
   <p align="center">
     <img width="1178" height="630" alt="image" src="https://github.com/user-attachments/assets/3643f749-e72d-4bcb-bdfe-3f881c5482e6" />
   </p>

6. Hapus Data Tugas (Delete):
   Menampilkan tabel tugas, lalu menghapus data dari database berdasarkan Kode Tugas yang dipilih.
   <br>
   <p align="center">
     <img width="1190" height="523" alt="image" src="https://github.com/user-attachments/assets/c7f4d966-bf70-4198-a0d7-1a831a3bfa2b" />
   </p>

7. Keluar Application:
   Menutup koneksi dan perulangan program.
   <br>
   <p align="center">
     <img width="727" height="472" alt="image" src="https://github.com/user-attachments/assets/8216c782-a169-4637-828f-985068504f4e" />
   </p>

---

## Dokumentasi Database (MySQL / MariaDB)

Berikut adalah tangkapan layar (screenshot) struktur tabel serta dummy data awal yang telah diisi langsung melalui file database.sql dan tersimpan di dalam database:

### 1. Struktur Tabel tugas di Database
(Unggah screenshot struktur tabel tugas dari phpMyAdmin / MySQL Workbench ke folder screenshots/)
<br>
<p align="center">
  <img width="1012" height="387" alt="image" src="https://github.com/user-attachments/assets/a28d4d72-bb94-4522-a737-95274ee5de73" />
</p>

### 2. Isi Dummy Data Awal pada Tabel tugas (SQL)
(Unggah screenshot data awal minimal 1-2 data yang berhasil masuk via script .sql)
<br>
<p align="center">
  <img width="1336" height="518" alt="image" src="https://github.com/user-attachments/assets/9f3775af-02f5-4d5e-ab63-48e8f6ac0f40" />
</p>
