package com.example.user.laporanpengaduanadmin.mDatas;

public class HistoryInfo {
    private String nama;
    private String kategori;
    private String deskripsi;
    private String lokasi;
    private String tanggal;
    private String tanggalawal;
    private String url;
    private String gmail;
    private String status;
    private String tanggalselesai;
    private String minggu1;
    private String minggu2;
    private String minggu3;
    private String minggu4;
    private String checkminggu1;
    private String checkminggu2;
    private String checkminggu3;
    private String checkminggu4;
    private String lamaPengerjaan;

    public HistoryInfo() {}

    public HistoryInfo(String nama, String kategori, String deskripsi, String lokasi, String tanggal, String url, String gmail, String status, String tanggalselesai) {
        this.nama = nama;
        this.kategori = kategori;
        this.deskripsi = deskripsi;
        this.lokasi = lokasi;
        this.tanggal = tanggal;
        this.url = url;
        this.gmail = gmail;
        this.status = status;
        this.tanggalselesai = tanggalselesai;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public void setTanggalAwal(String tanggalawal) {
        this.tanggalawal = tanggalawal;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTanggalSelesai(String tanggalselesai) {
        this.tanggalselesai = tanggalselesai;
    }

    public void setMinggu1(String minggu1) {
        this.minggu1= minggu1;
    }

    public void setMinggu2(String minggu2) {
        this.minggu2 = minggu2;
    }

    public void setMinggu3(String minggu3) {
        this.minggu3 = minggu3;
    }

    public void setMinggu4(String minggu4) {
        this.minggu4 = minggu4;
    }

    public void setCheckMinggu1(String checkminggu1) {
        this.checkminggu1= checkminggu1;
    }

    public void setCheckMinggu2(String checkminggu2) {
        this.checkminggu2 = checkminggu2;
    }

    public void setCheckMinggu3(String checkminggu3) {
        this.checkminggu3 = checkminggu3;
    }

    public void setCheckMinggu4(String checkminggu4) {
        this.checkminggu4 = checkminggu4;
    }

    public void setLamaPengerjaan(String lamaPengerjaan) {
        this.lamaPengerjaan = lamaPengerjaan;
    }

    public String getNama() {
        return nama;
    }

    public String getLamaPengerjaan() {
        return lamaPengerjaan;
    }

    public String getTanggalSelesai() {
        return tanggalselesai;
    }

    public String getTanggalAwal() {
        return tanggalawal;
    }

    public String getMinggu1() {
        return minggu1;
    }

    public String getMinggu2() {
        return minggu2;
    }

    public String getMinggu3() {
        return minggu3;
    }

    public String getMinggu4() {
        return minggu4;
    }

    public String getCheckMinggu1() {
        return checkminggu1;
    }

    public String getCheckMinggu2() {
        return checkminggu2;
    }

    public String getCheckMinggu3() {
        return checkminggu3;
    }

    public String getCheckMinggu4() {
        return checkminggu4;
    }


    public String getKategori() {
        return kategori;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getLokasi() {
        return lokasi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getUrl() {
        return url;
    }

    public String getGmail() {
        return gmail;
    }

    public String getStatus() {
        return status;
    }
}
