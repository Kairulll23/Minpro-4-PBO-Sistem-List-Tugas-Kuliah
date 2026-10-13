package model;

public abstract class EntitasTugas implements PenyimpananData {
    private final String kodeTugas;
    private String namaMatkul;
    private String detailTugas;
    private String dateline;
    private boolean isSelesai;

    public EntitasTugas(String kodeTugas, String namaMatkul, String detailTugas, String dateline) {
        this.kodeTugas = kodeTugas;
        this.namaMatkul = namaMatkul;
        this.detailTugas = detailTugas;
        this.dateline = dateline;
        this.isSelesai = false;
    }

    public String getKodeTugas() { return kodeTugas; }
    public String getNamaMatkul() { return namaMatkul; }
    public void setNamaMatkul(String namaMatkul) { this.namaMatkul = namaMatkul; }

    public String getDetailTugas() { return detailTugas; }
    public void setDetailTugas(String detailTugas) { this.detailTugas = detailTugas; }

    public String getDateline() { return dateline; }
    public void setDateline(String dateline) { this.dateline = dateline; }

    public boolean isSelesai() { return isSelesai; }
    public void setSelesai(boolean selesai) { isSelesai = selesai; }

    public String getStatusTeks() {
        return isSelesai ? "SELESAI [v]" : "BELUM [x]";
    }

    public abstract String getTipeTugas();

    @Override
    public String getKategoriFormat() {
        return "[" + getTipeTugas().toUpperCase() + "]";
    }
}