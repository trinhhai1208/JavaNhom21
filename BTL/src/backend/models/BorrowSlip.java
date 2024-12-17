package backend.models;

import java.time.LocalDate;

public class BorrowSlip {
    private String maPhieuMuon;
    private LocalDate ngayMuon;
    private LocalDate ngayTra;
    private String maTaiKhoan;
    private String maSach;
    //6 trạng thái bao gồm Pending(Chờ duyệt), Approved (Được duyệt), Dissaproved(Không được duyệt),onTime(trả sách đúng hạn),Lost(Độc giả làm mất sách),Expired(Hết hạn),Solved(Đã giải quyết)
    private String trangThai;

    public BorrowSlip(String maPhieuMuon, LocalDate ngayMuon, String maTaiKhoan, String maSach, String trangThai) {
        this.maPhieuMuon = maPhieuMuon;
        this.ngayMuon = ngayMuon;
        this.ngayTra = ngayMuon.plusDays(14);
        this.maTaiKhoan = maTaiKhoan;
        this.maSach = maSach;
        this.trangThai = trangThai;
    }

    public String getMaPhieuMuon() {
        return maPhieuMuon;
    }

    public void setMaPhieuMuon(String maPhieuMuon) {
        this.maPhieuMuon = maPhieuMuon;
    }

    public LocalDate getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(LocalDate ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public LocalDate getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(LocalDate ngayTra) {
        this.ngayTra = ngayTra;
    }

    public String getMaTaiKhoan() {
        return maTaiKhoan;
    }

    public void setMaTaiKhoan(String maTaiKhoan) {
        this.maTaiKhoan = maTaiKhoan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    @Override
    public String toString() {
        return String.format(" %-25s | %-25s | %-25s | %-25s | %-25s | %-25s |",
                maPhieuMuon, ngayMuon, ngayTra, maTaiKhoan, maSach,trangThai);
    }
}
