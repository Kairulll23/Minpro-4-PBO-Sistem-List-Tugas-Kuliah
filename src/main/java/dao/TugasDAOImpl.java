package dao;

import config.Database;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TugasDAOImpl implements TugasDAO {

    @Override
    public boolean insert(EntitasTugas tugas) {
        String sql = "INSERT INTO tugas (kode_tugas, tipe_tugas, nama_matkul, detail_tugas, dateline, is_selesai, platform_pengumpulan, jumlah_anggota) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, tugas.getKodeTugas());
            stmt.setString(2, tugas.getTipeTugas());
            stmt.setString(3, tugas.getNamaMatkul());
            stmt.setString(4, tugas.getDetailTugas());
            stmt.setString(5, tugas.getDateline());
            stmt.setBoolean(6, tugas.isSelesai());

            if (tugas instanceof TugasIndividu ti) {
                stmt.setString(7, ti.getPlatformPengumpulan());
                stmt.setNull(8, Types.INTEGER);
            } else if (tugas instanceof TugasKelompok tk) {
                stmt.setNull(7, Types.VARCHAR);
                stmt.setInt(8, tk.getJumlahAnggota());
            }

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[!] Error Insert DB: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<EntitasTugas> getAll() {
        List<EntitasTugas> list = new ArrayList<>();
        String sql = "SELECT * FROM tugas ORDER BY kode_tugas ASC";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(mapRowToEntity(rs));
            }
        } catch (SQLException e) {
            System.err.println("[!] Error Get All DB: " + e.getMessage());
        }
        return list;
    }

    @Override
    public EntitasTugas getByKode(String kodeTugas) {
        String sql = "SELECT * FROM tugas WHERE kode_tugas = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, kodeTugas);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToEntity(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("[!] Error Get By Kode DB: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<EntitasTugas> getByMatkul(String namaMatkul) {
        List<EntitasTugas> list = new ArrayList<>();
        String sql = "SELECT * FROM tugas WHERE LOWER(nama_matkul) LIKE LOWER(?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + namaMatkul + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRowToEntity(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("[!] Error Search Matkul DB: " + e.getMessage());
        }
        return list;
    }

    @Override
    public boolean update(EntitasTugas tugas) {
        String sql = "UPDATE tugas SET nama_matkul = ?, detail_tugas = ?, dateline = ?, platform_pengumpulan = ?, jumlah_anggota = ? WHERE kode_tugas = ?";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tugas.getNamaMatkul());
            stmt.setString(2, tugas.getDetailTugas());
            stmt.setString(3, tugas.getDateline());

            if (tugas instanceof TugasIndividu ti) {
                stmt.setString(4, ti.getPlatformPengumpulan());
                stmt.setNull(5, Types.INTEGER);
            } else if (tugas instanceof TugasKelompok tk) {
                stmt.setNull(4, Types.VARCHAR);
                stmt.setInt(5, tk.getJumlahAnggota());
            }

            stmt.setString(6, tugas.getKodeTugas());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[!] Error Update DB: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateStatus(String kodeTugas, boolean isSelesai) {
        String sql = "UPDATE tugas SET is_selesai = ? WHERE kode_tugas = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setBoolean(1, isSelesai);
            stmt.setString(2, kodeTugas);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[!] Error Update Status DB: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(String kodeTugas) {
        String sql = "DELETE FROM tugas WHERE kode_tugas = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, kodeTugas);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[!] Error Delete DB: " + e.getMessage());
            return false;
        }
    }

    @Override
    public String getLastKode() {
        String sql = "SELECT kode_tugas FROM tugas ORDER BY kode_tugas DESC LIMIT 1";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getString("kode_tugas");
            }
        } catch (SQLException e) {
            System.err.println("[!] Error Get Last Kode: " + e.getMessage());
        }
        return null;
    }

    private EntitasTugas mapRowToEntity(ResultSet rs) throws SQLException {
        String kode = rs.getString("kode_tugas");
        String tipe = rs.getString("tipe_tugas");
        String matkul = rs.getString("nama_matkul");
        String detail = rs.getString("detail_tugas");
        String dateline = rs.getString("dateline");
        boolean selesai = rs.getBoolean("is_selesai");

        EntitasTugas tugas;
        if ("Individu".equalsIgnoreCase(tipe)) {
            String platform = rs.getString("platform_pengumpulan");
            tugas = new TugasIndividu(kode, matkul, detail, dateline, platform);
        } else {
            int anggota = rs.getInt("jumlah_anggota");
            tugas = new TugasKelompok(kode, matkul, detail, dateline, anggota);
        }
        tugas.setSelesai(selesai);
        return tugas;
    }
}