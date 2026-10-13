package controller;

import dao.TugasDAO;
import dao.TugasDAOImpl;
import model.EntitasTugas;

import java.util.List;

public class TugasController {
    private final TugasDAO tugasDAO = new TugasDAOImpl();
    private static final String PREFIX = "TGS-";

    public String generateAutoKode() {
        String lastKode = tugasDAO.getLastKode();
        if (lastKode == null || !lastKode.startsWith(PREFIX)) {
            return "TGS-001";
        }
        int lastNum = Integer.parseInt(lastKode.replace(PREFIX, ""));
        return String.format("%s%03d", PREFIX, lastNum + 1);
    }

    public boolean tambahTugas(EntitasTugas t) {
        return tugasDAO.insert(t);
    }

    public List<EntitasTugas> getAllTugas() {
        return tugasDAO.getAll();
    }

    public EntitasTugas cariTugas(String kodeTugas) {
        return tugasDAO.getByKode(kodeTugas);
    }

    public List<EntitasTugas> cariTugas(String namaMatkul, boolean isMatkul) {
        return tugasDAO.getByMatkul(namaMatkul);
    }

    public boolean ubahTugas(EntitasTugas t) {
        return tugasDAO.update(t);
    }

    public boolean ubahStatus(String kodeTugas, boolean isSelesai) {
        return tugasDAO.updateStatus(kodeTugas, isSelesai);
    }

    public boolean hapusTugas(String kodeTugas) {
        return tugasDAO.delete(kodeTugas);
    }

    public int getJumlahSelesai() {
        int count = 0;
        for (EntitasTugas t : getAllTugas()) {
            if (t.isSelesai()) count++;
        }
        return count;
    }
}