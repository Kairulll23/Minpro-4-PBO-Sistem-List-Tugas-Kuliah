package dao;

import model.EntitasTugas;
import java.util.List;

public interface TugasDAO {
    boolean insert(EntitasTugas tugas);
    List<EntitasTugas> getAll();
    EntitasTugas getByKode(String kodeTugas);
    List<EntitasTugas> getByMatkul(String namaMatkul);
    boolean update(EntitasTugas tugas);
    boolean updateStatus(String kodeTugas, boolean isSelesai);
    boolean delete(String kodeTugas);
    String getLastKode();
}