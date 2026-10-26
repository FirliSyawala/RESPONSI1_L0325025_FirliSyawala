//FIRLI SYAWALA L0325025

package responsi1;

public class BarangBantuan {
    private final String kodeBarang;
    private final String namaBarang;
    private final String kategori;
    private final double beratKg;

    public BarangBantuan(String kodeBarang, String namaBarang,
                         String kategori, double beratKg) {
        this.kodeBarang = kodeBarang;
        this.namaBarang = namaBarang;
        this.kategori = kategori;
        this.beratKg = beratKg;
    }

    public String getKodeBarang() {
        return kodeBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public String getKategori() {
        return kategori;
    }

    public double getBeratKg() {
        return beratKg;
    }
}