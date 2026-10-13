package view;

import controller.TugasController;
import model.*;

import java.util.List;
import java.util.Scanner;

public class MainView {
    private final TugasController controller = new TugasController();
    private final Scanner scan = new Scanner(System.in);

    public void olahMenu(Mahasiswa mhs) {
        boolean lanjut = true;
        System.out.println("\n==========================================================================================");
        System.out.println("  Selamat Datang, " + mhs.getNama() + " (" + mhs.getNim() + ")");
        System.out.println("==========================================================================================");

        while (lanjut) {
            tampilkanDashboardStatistik();

            System.out.println("\n=== SISTEM MANAJEMEN TUGAS KULIAH (DATABASE) ===");
            System.out.println("1. Tambah Tugas Baru (Create)");
            System.out.println("2. Tampilkan Daftar Tugas (Read)");
            System.out.println("3. Cari Tugas berdasarkan Mata Kuliah");
            System.out.println("4. Tandai Status Tugas (Selesai/Belum)");
            System.out.println("5. Ubah Data Tugas (Update)");
            System.out.println("6. Hapus Tugas (Delete)");
            System.out.println("7. Keluar Aplikasi");
            System.out.print("Pilih menu (1-7): ");

            int pil = bacaAngka();

            switch (pil) {
                case 1 -> tambahTugas();
                case 2 -> tampilkanTugas();
                case 3 -> cariTugasByMatkul();
                case 4 -> ubahStatusTugas();
                case 5 -> editTugas();
                case 6 -> hapusTugas();
                case 7 -> {
                    lanjut = false;
                    System.out.println("\nTerima kasih, program selesai.");
                }
                default -> System.out.println("[!] Pilihan menu tidak tersedia. Pilih 1-7.");
            }
        }
    }

    private void tampilkanDashboardStatistik() {
        List<EntitasTugas> all = controller.getAllTugas();
        int total = all.size();
        int selesai = controller.getJumlahSelesai();
        int belum = total - selesai;

        System.out.println("\n+-----------------------------------------------------------+");
        System.out.printf("| 📊 DASHBOARD TUGAS : Total: %-2d | Selesai: %-2d | Belum: %-2d  |\n", total, selesai, belum);
        System.out.println("+-----------------------------------------------------------+");
    }

    private int bacaAngka() {
        while (!scan.hasNextInt()) {
            System.out.println("[!] Input harus berupa angka!");
            System.out.print("Silakan masukkan angka: ");
            scan.next();
        }
        int angka = scan.nextInt();
        scan.nextLine();
        return angka;
    }

    private String bacaTeks(String label) {
        String input;
        do {
            System.out.print(label);
            input = scan.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("[!] Inputan tidak boleh kosong.");
            } else if (input.matches("^\\d+$")) {
                System.out.println("[!] Inputan tidak boleh hanya berupa angka!");
                input = "";
            }
        } while (input.isEmpty());
        return input;
    }

    private String bacaTeksBebas(String label) {
        String input;
        do {
            System.out.print(label);
            input = scan.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("[!] Inputan tidak boleh kosong.");
            }
        } while (input.isEmpty());
        return input;
    }

    private void tambahTugas() {
        System.out.println("\n--- TAMBAH TUGAS BARU ---");
        String autoKode = controller.generateAutoKode();
        System.out.println(">> Kode Tugas Otomatis : " + autoKode);

        System.out.println("Pilih Jenis Tugas:");
        System.out.println("1. Tugas Individu");
        System.out.println("2. Tugas Kelompok");
        System.out.print("Pilih (1/2): ");
        int jenis = bacaAngka();

        String matkul = bacaTeks("Nama Mata Kuliah: ");
        String detail = bacaTeksBebas("Catatan/Detail Tugas: ");
        String dateline = bacaTeksBebas("Deadline (contoh: 2026-11-01 23:59): ");

        boolean sukses = false;
        if (jenis == 1) {
            String platform = bacaTeks("Platform Pengumpulan: ");
            sukses = controller.tambahTugas(new TugasIndividu(autoKode, matkul, detail, dateline, platform));
        } else if (jenis == 2) {
            System.out.print("Jumlah Anggota Kelompok: ");
            int jml = bacaAngka();
            sukses = controller.tambahTugas(new TugasKelompok(autoKode, matkul, detail, dateline, jml));
        } else {
            System.out.println("[!] Jenis tugas tidak valid.");
            return;
        }

        if (sukses) {
            System.out.println("[+] Tugas berhasil disimpan ke Database!");
        } else {
            System.out.println("[!] Gagal menyimpan tugas ke database.");
        }
    }

    public void tampilkanTugas() {
        List<EntitasTugas> list = controller.getAllTugas();
        if (list.isEmpty()) {
            System.out.println("\n[!] Belum ada data tugas di dalam Database.");
            return;
        }

        cetakTabelHeader();
        for (EntitasTugas t : list) {
            System.out.println(t.cetakBaris());
        }
        cetakTabelFooter();
    }

    private void ubahStatusTugas() {
        System.out.println("\n--- TANDAI STATUS TUGAS ---");
        tampilkanTugas();
        if (controller.getAllTugas().isEmpty()) return;

        System.out.print("\nMasukkan Kode Tugas yang ingin diubah statusnya: ");
        String kode = scan.nextLine().trim();
        EntitasTugas t = controller.cariTugas(kode);

        if (t == null) {
            System.out.println("[!] Kode tugas tidak ditemukan.");
            return;
        }

        System.out.println("Status saat ini: " + t.getStatusTeks());
        System.out.print("Ubah ke Selesai? (y/n): ");
        String pil = scan.nextLine().trim();

        boolean statusBaru = pil.equalsIgnoreCase("y");
        if (controller.ubahStatus(kode, statusBaru)) {
            System.out.println("[+] Status di database berhasil diperbarui!");
        } else {
            System.out.println("[!] Gagal memperbarui status.");
        }
    }

    private void cariTugasByMatkul() {
        System.out.println("\n--- CARI TUGAS BERDASARKAN MATKUL ---");
        String matkul = bacaTeks("Masukkan Nama Mata Kuliah: ");
        List<EntitasTugas> hasil = controller.cariTugas(matkul, true);

        if (hasil.isEmpty()) {
            System.out.println("[!] Tidak ditemukan tugas untuk mata kuliah: " + matkul);
            return;
        }

        cetakTabelHeader();
        for (EntitasTugas t : hasil) {
            System.out.println(t.cetakBaris());
        }
        cetakTabelFooter();
    }

    private void editTugas() {
        System.out.println("\n--- UBAH DATA TUGAS ---");
        tampilkanTugas();
        if (controller.getAllTugas().isEmpty()) return;

        System.out.print("\nMasukkan Kode Tugas yang ingin diubah: ");
        String kode = scan.nextLine().trim();
        EntitasTugas t = controller.cariTugas(kode);

        if (t == null) {
            System.out.println("[!] Kode tugas tidak ditemukan.");
            return;
        }

        System.out.println("\n-> Mengubah Data Kode: " + t.getKodeTugas() + " (" + t.getTipeTugas() + ")");
        t.setNamaMatkul(bacaTeks("Nama Mata Kuliah Baru: "));
        t.setDetailTugas(bacaTeksBebas("Catatan Tugas Baru: "));
        t.setDateline(bacaTeksBebas("Deadline Baru: "));

        if (t instanceof TugasIndividu ti) {
            ti.setPlatformPengumpulan(bacaTeks("Platform Pengumpulan Baru: "));
        } else if (t instanceof TugasKelompok tk) {
            System.out.print("Jumlah Anggota Kelompok Baru: ");
            tk.setJumlahAnggota(bacaAngka());
        }

        if (controller.ubahTugas(t)) {
            System.out.println("[+] Data tugas berhasil diperbarui di Database!");
        } else {
            System.out.println("[!] Gagal memperbarui data.");
        }
    }

    private void hapusTugas() {
        System.out.println("\n--- HAPUS TUGAS ---");
        tampilkanTugas();
        if (controller.getAllTugas().isEmpty()) return;

        System.out.print("\nMasukkan Kode Tugas yang ingin dihapus: ");
        String kode = scan.nextLine().trim();

        if (controller.hapusTugas(kode)) {
            System.out.println("[+] Tugas " + kode + " berhasil dihapus dari Database.");
        } else {
            System.out.println("[!] Gagal menghapus atau Kode tidak ditemukan.");
        }
    }

    private void cetakTabelHeader() {
        System.out.println("\n==================================================================================================================");
        System.out.printf("| %-8s | %-10s | %-18s | %-26s | %-16s | %-11s |\n", "KODE", "TIPE", "MATA KULIAH", "CATATAN TUGAS", "DEADLINE", "STATUS");
        System.out.println("==================================================================================================================");
    }

    private void cetakTabelFooter() {
        System.out.println("==================================================================================================================");
    }
}