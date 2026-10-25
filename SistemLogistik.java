package responsi1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SistemLogistik {

    private static final Scanner input = new Scanner(System.in);

    private static final ArrayList<BarangBantuan> daftarBarang
            = new ArrayList<>();

    public static void main(String[] args) {

        boolean selesai = false;

        while (!selesai) {
            System.out.println("\n=== SISTEM LOGISTIK BANTUAN ===");
            System.out.println("1. Tambah Barang");
            System.out.println("2. Selesai dan Cetak Manifes");
            System.out.print("Pilih menu: ");

            String pilihan = input.nextLine().trim();

            if (pilihan.equals("1")) {
                tambahBarang();
            } else if (pilihan.equals("2")) {
                cetakManifes();
                selesai = true;
            } else {
                System.out.println(
                        "Pilihan tidak valid. Masukkan 1 atau 2."
                );
            }
        }

        input.close();
    }

    private static void tambahBarang() {

        boolean valid = false;

        while (!valid) {
            try {
                System.out.print(
                        "Kode barang (harus diawali EDX-): "
                );
                String kode = input.nextLine().trim();

                if (!kode.startsWith("EDX-")) {
                    throw new IllegalArgumentException(
                            "Kode barang harus diawali EDX-"
                    );
                }

                System.out.print("Nama barang: ");
                String nama = input.nextLine().trim();

                System.out.print("Kategori barang: ");
                String kategori = input.nextLine().trim();

                if (nama.isEmpty() || kategori.isEmpty()) {
                    throw new IllegalArgumentException(
                            "Nama dan kategori tidak boleh kosong."
                    );
                }

                System.out.print("Berat barang (kg): ");
                double berat = Double.parseDouble(
                        input.nextLine().trim()
                );

                if (berat <= 0) {
                    throw new BeratInvalidException(
                            "Berat barang harus lebih dari 0 kg."
                    );
                }

                BarangBantuan barang = new BarangBantuan(
                        kode, nama, kategori, berat
                );

                daftarBarang.add(barang);

                System.out.println(
                        "Barang berhasil ditambahkan."
                );

                valid = true;

            } catch (BeratInvalidException e) {
                System.out.println(
                        "Kesalahan berat: " + e.getMessage()
                );
                System.out.println(
                        "Silakan ulangi input barang."
                );

            } catch (NumberFormatException e) {
                System.out.println(
                        "Berat harus berupa angka, misalnya 2.5."
                );
                System.out.println(
                        "Silakan ulangi input barang."
                );

            } catch (IllegalArgumentException e) {
                System.out.println(
                        "Input tidak valid: " + e.getMessage()
                );
                System.out.println(
                        "Silakan ulangi input barang."
                );

            } finally {
                System.out.println(
                        "Proses validasi input selesai."
                );
            }
        }
    }

    private static void cetakManifes() {

        double totalBerat = 0;

        for (BarangBantuan barang : daftarBarang) {
            totalBerat += barang.getBeratKg();
        }

        String armada;

        if (totalBerat <= 50) {
            armada = "Mobil Pickup";
        } else {
            armada = "Truk Ekspedisi";
        }

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("manifest_pengiriman.txt"))) {

            writer.write(
                    "========== MANIFES PENGIRIMAN =========="
            );
            writer.newLine();

            writer.write(
                    "Kode Barang | Nama Barang | Kategori | Berat (kg)"
            );
            writer.newLine();

            writer.write(
                    "--------------------------------------------------"
            );
            writer.newLine();

            for (BarangBantuan barang : daftarBarang) {
                writer.write(
                        barang.getKodeBarang() + " | "
                        + barang.getNamaBarang() + " | "
                        + barang.getKategori() + " | "
                        + barang.getBeratKg() + " kg"
                );
                writer.newLine();
            }

            writer.newLine();
            writer.write(
                    "Total barang: " + daftarBarang.size()
            );
            writer.newLine();

            writer.write(
                    "Total berat muatan: " + totalBerat + " kg"
            );
            writer.newLine();

            writer.write("Label armada: " + armada);
            writer.newLine();

            System.out.println(
                    "Data manifes berhasil diekspor ke "
                    + "manifest_pengiriman.txt"
            );

            System.out.println(
                    "Total barang: " + daftarBarang.size()
            );

            System.out.println(
                    "Total berat muatan: " + totalBerat + " kg"
            );

            System.out.println("Label armada: " + armada);

        } catch (IOException e) {
            System.out.println(
                    "Gagal menyimpan manifes: " + e.getMessage()
            );
        }
    }
}