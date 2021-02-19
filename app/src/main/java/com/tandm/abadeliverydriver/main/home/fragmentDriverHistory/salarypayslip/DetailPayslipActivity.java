package com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.salarypayslip;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.home.fragmentDriverHistory.salarypayslip.model.ItemPayslip;
import com.tandm.abadeliverydriver.main.utilities.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailPayslipActivity extends AppCompatActivity {

    @BindView(R.id.tvBangLuongChiTiet)
    TextView tvBangLuongChiTiet;
    @BindView(R.id.tvPayslipName)
    TextView tvPayslipName;
    @BindView(R.id.tvPayslipPosition)
    TextView tvPayslipPosition;
    @BindView(R.id.tvPayslipMaNhanVien)
    TextView tvPayslipMaNhanVien;
    @BindView(R.id.tvPayslipNgayVaoLam)
    TextView tvPayslipNgayVaoLam;
    @BindView(R.id.tvPayslipMST)
    TextView tvPayslipMST;
    @BindView(R.id.tvCacKhoanCong)
    TextView tvCacKhoanCong;
    @BindView(R.id.tvPayslipTotalChuyen)
    TextView tvPayslipTotalChuyen;
    @BindView(R.id.tvLuongChuyenChinh)
    TextView tvLuongChuyenChinh;
    @BindView(R.id.tvSoNgayHocViec)
    TextView tvSoNgayHocViec;
    @BindView(R.id.tvLuongHocViec)
    TextView tvLuongHocViec;
    @BindView(R.id.tvHoTro)
    TextView tvHoTro;
    @BindView(R.id.tvLuongNgayPhep)
    TextView tvLuongNgayPhep;
    @BindView(R.id.tvTienDienThoai)
    TextView tvTienDienThoai;
    @BindView(R.id.tvPhuCapDoiTruong)
    TextView tvPhuCapDoiTruong;
    @BindView(R.id.tvSinhNhat)
    TextView tvSinhNhat;
    @BindView(R.id.tvThuongLe)
    TextView tvThuongLe;
    @BindView(R.id.tvDinhMucDauVuot)
    TextView tvDinhMucDauVuot;
    @BindView(R.id.tvThuongTienDau)
    TextView tvThuongTienDau;
    @BindView(R.id.tvLuongThuong)
    TextView tvLuongThuong;
    @BindView(R.id.tvKhac)
    TextView tvKhac;
    @BindView(R.id.tvTongKhoanTru)
    TextView tvTongKhoanTru;
    @BindView(R.id.tvTienDau)
    TextView tvTienDau;
    @BindView(R.id.tvLuongDongBHXH)
    TextView tvLuongDongBHXH;
    @BindView(R.id.tvBHBB)
    TextView tvBHBB;
    @BindView(R.id.tvDieuChinhBHXH)
    TextView tvDieuChinhBHXH;
    @BindView(R.id.tvSoNguoiPhuThuoc)
    TextView tvSoNguoiPhuThuoc;
    @BindView(R.id.tvThueTNCN)
    TextView tvThueTNCN;
    @BindView(R.id.tvTienDatCoc)
    TextView tvTienDatCoc;
    @BindView(R.id.tvTamUngBoiThuong)
    TextView tvTamUngBoiThuong;
    @BindView(R.id.tvTamUngLuong)
    TextView tvTamUngLuong;
    @BindView(R.id.tvPhiCongDoan)
    TextView tvPhiCongDoan;
    @BindView(R.id.tvPhiNganHang)
    TextView tvPhiNganHang;
    @BindView(R.id.tvTruyThuKhac)
    TextView tvTruyThuKhac;
    @BindView(R.id.tvCongTumLum)
    TextView tvCongTumLum;

    @BindView(R.id.tvLuongHoTroMacDinh)
    TextView tvLuongHoTroMacDinh;
    @BindView(R.id.tvLuongNgayPhepMacDinh)
    TextView tvLuongNgayPhepMacDinh;
    @BindView(R.id.tvCacKhoanCongMacDinh)
    TextView tvCacKhoanCongMacDinh;

    @BindView(R.id.tvKhacNeuCo)
    TextView tvKhacNeuCo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_payslip);
        ButterKnife.bind(this);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            ItemPayslip items = (ItemPayslip) getIntent().getSerializableExtra("items");
            if (items.loai_xe.equals("XE_NHO")) {
                tvHoTro.setVisibility(View.GONE);
                tvLuongHoTroMacDinh.setVisibility(View.GONE);
                tvLuongNgayPhep.setVisibility(View.GONE);
                tvLuongNgayPhepMacDinh.setVisibility(View.GONE);
                tvCacKhoanCongMacDinh.setText(getResources().getString(R.string.cackhoancongxenho));
            } else {
                tvLuongNgayPhep.setText(Utilities.formatNumber(String.valueOf((int) items.luong_Ngay_NP)));
                tvHoTro.setText(Utilities.formatNumber(String.valueOf((int) items.luong_Ho_Tro_Phu)));
            }
            tvBangLuongChiTiet.setText("Bảng lương chi tiết tháng " + Utilities.formatDate_MMyyyy(items.luong_Thang));
            tvPayslipName.setText(items.ten_Nhan_Vien);
            tvPayslipPosition.setText(items.vi_tri);
            tvPayslipMaNhanVien.setText(items.ma_Nhan_Vien);
            tvPayslipNgayVaoLam.setText(Utilities.formatDate_ddMMyyyy(items.ngay_Vao_Lam));
            tvPayslipMST.setText(items.ma_so_thue);
            tvCacKhoanCong.setText(Utilities.formatNumber(String.valueOf((int) items.tong_Thu_Nhap)));
            tvPayslipTotalChuyen.setText(String.valueOf(items.tong_Chuyen));
            tvLuongChuyenChinh.setText(Utilities.formatNumber(String.valueOf((int) items.luong_Chuyen_Chinh)));
            tvSoNgayHocViec.setText(String.valueOf(items.soNgay_Hoc_Viec));
            tvLuongHocViec.setText(Utilities.formatNumber(String.valueOf((int) items.luong_Hoc_Viec)));
            tvTienDienThoai.setText(Utilities.formatNumber(String.valueOf((int) items.tien_Dien_Thoai)));
            tvPhuCapDoiTruong.setText(Utilities.formatNumber(String.valueOf((int) items.phu_Cap_Doi_Truong)));
            tvSinhNhat.setText(Utilities.formatNumber(String.valueOf((int) items.sinh_Nhat)));
            tvThuongLe.setText(Utilities.formatNumber(String.valueOf((int) items.thuong_Hotro_Le)));
            tvDinhMucDauVuot.setText(Utilities.formatNumber(String.valueOf((int) items.thuong_Dinh_Muc_Dau + (int) items.tru_Dinh_Muc_Dau)));
            tvThuongTienDau.setText(Utilities.formatNumber(String.valueOf((int) items.thanh_Tien_Thuong_Dau)));
            tvLuongThuong.setText(Utilities.formatNumber(String.valueOf((int) items.luong_Bo_Sung_4B)));
            tvKhac.setText(Utilities.formatNumber(String.valueOf((int) items.khac)));
            tvTongKhoanTru.setText(Utilities.formatNumber(String.valueOf((int) items.tong_Khau_Tru)));
            tvTienDau.setText(Utilities.formatNumber(String.valueOf((int) items.thanh_Tien_Tru_Dau)));
            tvLuongDongBHXH.setText(Utilities.formatNumber(String.valueOf((int) items.tong_TG_BHXH)));
            tvBHBB.setText(Utilities.formatNumber(String.valueOf((int) items.trich_BHXH)));
            tvDieuChinhBHXH.setText(Utilities.formatNumber(String.valueOf((int) items.truy_Thu_BHXH)));
            tvSoNguoiPhuThuoc.setText(String.valueOf(items.so_Nguoi_Phu_Thuoc));
            tvThueTNCN.setText(Utilities.formatNumber(String.valueOf((int) items.thue_TNCN)));
            tvTienDatCoc.setText(Utilities.formatNumber(String.valueOf((int) items.thu_Coc)));
            tvTamUngBoiThuong.setText(Utilities.formatNumber(String.valueOf((int) items.tam_Ung_Boi_Thuong)));
            tvTamUngLuong.setText(Utilities.formatNumber(String.valueOf((int) items.tam_Ung_Luong)));
            tvPhiCongDoan.setText(Utilities.formatNumber(String.valueOf((int) items.le_Phi_Cong_Doan)));
            tvPhiNganHang.setText(Utilities.formatNumber(String.valueOf((int) items.phi_Ngan_Hang)));
            tvTruyThuKhac.setText(Utilities.formatNumber(String.valueOf((int) items.khoan_Tru_Khac)));
            tvCongTumLum.setText(Utilities.formatNumber(String.valueOf((int) items.thuc_Lanh)));
            tvKhacNeuCo.setText("Ghi chú(nếu có):''"+items.note+"''");
        } else {
            tvBangLuongChiTiet.setText("");
            tvPayslipName.setText("");
            tvPayslipMaNhanVien.setText("");
            tvPayslipNgayVaoLam.setText("");
            tvPayslipMST.setText("");
            tvCacKhoanCong.setText("");
            tvPayslipTotalChuyen.setText("");
            tvLuongChuyenChinh.setText("");
            tvSoNgayHocViec.setText("");
            tvLuongHocViec.setText("");
            tvHoTro.setText("");
            tvLuongNgayPhep.setText("");
            tvTienDienThoai.setText("");
            tvPhuCapDoiTruong.setText("");
            tvSinhNhat.setText("");
            tvThuongLe.setText("");
            tvDinhMucDauVuot.setText("");
            tvThuongTienDau.setText("");
            tvLuongThuong.setText("");
            tvKhac.setText("");
            tvTongKhoanTru.setText("");
            tvTienDau.setText("");
            tvLuongDongBHXH.setText("");
            tvBHBB.setText("");
            tvDieuChinhBHXH.setText("");
            tvSoNguoiPhuThuoc.setText("");
            tvThueTNCN.setText("");
            tvTienDatCoc.setText("");
            tvTamUngBoiThuong.setText("");
            tvTamUngLuong.setText("");
            tvPhiCongDoan.setText("");
            tvPhiNganHang.setText("");
            tvTruyThuKhac.setText("");
            tvCongTumLum.setText("");
            tvKhacNeuCo.setText("");
        }
    }
}
