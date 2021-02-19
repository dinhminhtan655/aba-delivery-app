package com.tandm.abadeliverydriver.main.home.fragmenthistorybiker;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.home.fragmenthistorybiker.model.PayslipBiker;
import com.tandm.abadeliverydriver.main.utilities.Utilities;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailPayslipBikerActivity extends AppCompatActivity {

    @BindView(R.id.tvBangLuongChiTiet)
    TextView tvBangLuongChiTiet;
    @BindView(R.id.tvPayslipName)
    TextView tvPayslipName;
    @BindView(R.id.tvPayslipPosition)
    TextView tvPayslipPosition;
    @BindView(R.id.tvPayslipMaNhanVien)
    TextView tvPayslipMaNhanVien;
    @BindView(R.id.tvCacKhoanCong)
    TextView tvCacKhoanCong;
    @BindView(R.id.tvKhayLon)
    TextView tvKhayLon;
    @BindView(R.id.tvKhayLon2)
    TextView tvKhayLon2;
    @BindView(R.id.tvKhayNho)
    TextView tvKhayNho;
    @BindView(R.id.tvKhayNho2)
    TextView tvKhayNho2;
    @BindView(R.id.tvGiaoBuCH)
    TextView tvGiaoBuCH;
    @BindView(R.id.tvGiaoBuCH2)
    TextView tvGiaoBuCH2;
    @BindView(R.id.tvNgayLamViecDat)
    TextView tvNgayLamViecDat;
    @BindView(R.id.tvNgayLamViecDat2)
    TextView tvNgayLamViecDat2;
    @BindView(R.id.tvKPINgay)
    TextView tvKPINgay;
    @BindView(R.id.tvKPIThang)
    TextView tvKPIThang;
    @BindView(R.id.tvBuSanLuong)
    TextView tvBuSanLuong;
    @BindView(R.id.tvBoSung)
    TextView tvBoSung;
    @BindView(R.id.tvThue)
    TextView tvThue;
    @BindView(R.id.tvPhiKhac)
    TextView tvPhiKhac;
    @BindView(R.id.tvCoc)
    TextView tvCoc;
    @BindView(R.id.tvThucLanh)
    TextView tvThucLanh;
    @BindView(R.id.tvNote)
    TextView tvNote;
    @BindView(R.id.tvTongKhoanTru)
    TextView tvTongKhoanTru;
    @BindView(R.id.tvCocDaThu)
    TextView tvCocDaThu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_payslip_biker);
        ButterKnife.bind(this);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            PayslipBiker items = (PayslipBiker) getIntent().getSerializableExtra("items");
            tvBangLuongChiTiet.setText("Bảng lương chi tiết tháng " + Utilities.formatDate_MMyyyy(items.luong_Thang));
            tvPayslipName.setText(items.ten_Nhan_Vien);
            tvPayslipPosition.setText(items.vi_tri);
            tvPayslipMaNhanVien.setText(items.ma_Nhan_Vien);
            tvCacKhoanCong.setText(Utilities.formatNumber(String.valueOf((int)items.tong_Luong_Chuyen)));
            tvKhayLon.setText(items.khay_Lon);
            tvKhayNho.setText(items.khay_Nho);
            tvGiaoBuCH.setText(items.gIao_Bu);
            tvNgayLamViecDat.setText(items.ngay_Lam_VIec_Dat);
            tvKPINgay.setText(Utilities.formatNumber(String.valueOf((int) items.kpI_Ngay)));
            tvKPIThang.setText(Utilities.formatNumber(String.valueOf((int)items.kpI_Thang)));
            tvBuSanLuong.setText(Utilities.formatNumber(String.valueOf((int)items.bu_San_Luong)));
            tvBoSung.setText(Utilities.formatNumber(String.valueOf((int)items.bo_Sung)));
            tvThue.setText(Utilities.formatNumber(String.valueOf((int)items.thue_TNCN)));
            tvPhiKhac.setText(Utilities.formatNumber(String.valueOf((int)items.phi_Khac)));
            tvCoc.setText(Utilities.formatNumber(String.valueOf((int)items.coc)));
            tvThucLanh.setText(Utilities.formatNumber(String.valueOf((int)items.thuc_Lanh)));
            tvNote.setText(items.note);
            tvKhayLon2.setText(Utilities.formatNumber(String.valueOf((int)items.tong_Tien_Khay_Lon)));
            tvKhayNho2.setText(Utilities.formatNumber(String.valueOf((int)items.tong_Tien_Khay_Nho)));
            tvTongKhoanTru.setText(Utilities.formatNumber(String.valueOf((int)items.tong_Khoan_Tru)));
            tvGiaoBuCH2.setText(Utilities.formatNumber(String.valueOf((int)items.tong_Tien_Giao_Bu)));
            tvNgayLamViecDat2.setText(Utilities.formatNumber(String.valueOf((int)items.tong_Tien_Ngay_Lam_Viec_Dat)));
            tvCocDaThu.setText(Utilities.formatNumber(String.valueOf((int)items.tong_Tien_Coc_Da_Thu)));

        } else {
            tvPayslipName.setText("");
            tvPayslipPosition.setText("");
            tvPayslipMaNhanVien.setText("");
            tvCacKhoanCong.setText("");
            tvKhayLon.setText("");
            tvKhayNho.setText("");
            tvGiaoBuCH.setText("");
            tvNgayLamViecDat.setText("");
            tvKPINgay.setText("");
            tvKPIThang.setText("");
            tvBuSanLuong.setText("");
            tvBoSung.setText("");
            tvThue.setText("");
            tvPhiKhac.setText("");
            tvCoc.setText("");
            tvThucLanh.setText("");
            tvNote.setText("");
        }
    }
}