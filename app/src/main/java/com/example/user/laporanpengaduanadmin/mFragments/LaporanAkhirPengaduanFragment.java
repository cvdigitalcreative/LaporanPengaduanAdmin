package com.example.user.laporanpengaduanadmin.mFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.laporanpengaduanadmin.R;
import com.example.user.laporanpengaduanadmin.mAdapters.KategoriDetailListAdapter;
import com.example.user.laporanpengaduanadmin.mAdapters.LaporanAkhirAdapter;
import com.example.user.laporanpengaduanadmin.mDatas.HistoryInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class LaporanAkhirPengaduanFragment extends Fragment {
    private ProgressDialog progressDialog;
    private ArrayList<HistoryInfo> infoHistory;
    private DatabaseReference databaseRef;
    private LaporanAkhirAdapter adapter;
    private ListView lvLaporanAkhir;
    private Button simpanExcel;

    public LaporanAkhirPengaduanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_laporan_akhir_pengaduan, container, false);

        lvLaporanAkhir = view.findViewById(R.id.lv_LaporanAkhir);
        simpanExcel = view.findViewById(R.id.writeExcel);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Silahkan tunggu proses memuat...");
        progressDialog.show();

        infoHistory = new ArrayList<>();
        databaseRef = FirebaseDatabase.getInstance().getReference("user/");

        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                infoHistory.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    HistoryInfo info = snapshot.getValue(HistoryInfo.class);
                    infoHistory.add(info);
                }

                adapter = new LaporanAkhirAdapter(getContext(),infoHistory);
                lvLaporanAkhir.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

        simpanExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Silahkan tunggu proses menyimpan...");
                progressDialog.show();

                databaseRef = FirebaseDatabase.getInstance().getReference("user/");

                databaseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        progressDialog.dismiss();
                        infoHistory.clear();
                        boolean Success = false;

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            HistoryInfo info = snapshot.getValue(HistoryInfo.class);
                            if(!snapshot.hasChild("tanggalselesai")){
                                info.setTanggalSelesai("-");
                            }

                            if(snapshot.hasChild("minggu1")){
                                info.setMinggu1(snapshot.child("minggu1").getValue().toString());
                                info.setMinggu2(snapshot.child("minggu2").getValue().toString());
                                info.setMinggu3(snapshot.child("minggu3").getValue().toString());
                                info.setMinggu4(snapshot.child("minggu4").getValue().toString());
                            }
                            else{
                                info.setMinggu1("-");
                                info.setMinggu2("-");
                                info.setMinggu3("-");
                                info.setMinggu4("-");
                            }
                            infoHistory.add(info);
                        }

                        Success = saveExcelFile(getContext(), "pengaduan.xls", infoHistory);

                        if(Success == true){
                            Toast.makeText(getActivity().getApplicationContext(), "Excel berhasil disimpan", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getActivity().getApplicationContext(), "Excel gagal disimpan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        progressDialog.dismiss();
                    }
                });
            }
        });
    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean saveExcelFile(Context context, String fileName, ArrayList<HistoryInfo> info) {
        int baris = 0;
        // check if available and not read only
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.w("FileUtils", "Storage not available or read only");
            return false;
        }

        boolean success = false;

        //New Workbook
        Workbook wb = new HSSFWorkbook();

        Cell c = null;

//        //Cell style for header row
//        CellStyle cs = wb.createCellStyle();
//        cs.setFillForegroundColor(HSSFColor.LIME.index);
//        cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        //New Sheet
        Sheet sheet1 = null;
        sheet1 = wb.createSheet("Laporan Pengaduan");

        // Generate column headings

        Row row = sheet1.createRow(0);

        c = row.createCell(0);
        c.setCellValue("Nama");
//        c.setCellStyle(cs);

        c = row.createCell(1);
        c.setCellValue("Kategori");
//        c.setCellStyle(cs);

        c = row.createCell(2);
        c.setCellValue("Deskripsi");
//        c.setCellStyle(cs);

        c = row.createCell(3);
        c.setCellValue("Lokasi");
//        c.setCellStyle(cs);

        c = row.createCell(4);
        c.setCellValue("Email");
        //       c.setCellStyle(cs);

        c = row.createCell(5);
        c.setCellValue("Tanggal Pengaduan");
//        c.setCellStyle(cs);

        c = row.createCell(6);
        c.setCellValue("Tanggal Selesai");
        //       c.setCellStyle(cs);

        c = row.createCell(7);
        c.setCellValue("Minggu 1");

        c = row.createCell(8);
        c.setCellValue("Minggu 2");

        c = row.createCell(9);
        c.setCellValue("Minggu 3");

        c = row.createCell(10);
        c.setCellValue("Minggu 4");

        c = row.createCell(11);
        c.setCellValue("Keterangan");

        sheet1.setColumnWidth(0, (8 * 500));
        sheet1.setColumnWidth(1, (8 * 500));
        sheet1.setColumnWidth(2, (15 * 500));
        sheet1.setColumnWidth(3, (15 * 500));
        sheet1.setColumnWidth(4, (15 * 500));
        sheet1.setColumnWidth(5, (15 * 500));
        sheet1.setColumnWidth(6, (15 * 500));
        sheet1.setColumnWidth(7, (10 * 500));
        sheet1.setColumnWidth(8, (10 * 500));
        sheet1.setColumnWidth(9, (10 * 500));
        sheet1.setColumnWidth(10, (10 * 500));
        sheet1.setColumnWidth(11, (15 * 500));

        for(int i=0; i<info.size(); i++) {
            row = sheet1.createRow(i + 1);

            c = row.createCell(0);
            c.setCellValue(info.get(i).getNama());

            c = row.createCell(1);
            c.setCellValue(info.get(i).getKategori());

            c = row.createCell(2);
            c.setCellValue(info.get(i).getDeskripsi());

            c = row.createCell(3);
            c.setCellValue(info.get(i).getLokasi());

            c = row.createCell(4);
            c.setCellValue(info.get(i).getGmail());

            c = row.createCell(5);
            c.setCellValue(info.get(i).getTanggal());

            c = row.createCell(6);
            c.setCellValue(info.get(i).getTanggalSelesai());

            c = row.createCell(7);
            c.setCellValue(info.get(i).getMinggu1());

            c = row.createCell(8);
            c.setCellValue(info.get(i).getMinggu2());

            c = row.createCell(9);
            c.setCellValue(info.get(i).getMinggu3());

            c = row.createCell(10);
            c.setCellValue(info.get(i).getMinggu4());

            c = row.createCell(11);
            c.setCellValue(info.get(i).getStatus());
        }

        // Create a path where we will place our List of objects on external storage
        File file = new File(context.getExternalFilesDir(null), fileName);
        FileOutputStream os = null;

        try {
            os = new FileOutputStream(file);
            wb.write(os);
            Log.w("FileUtils", "Writing file" + file);
            success = true;
        } catch (IOException e) {
            Log.w("FileUtils", "Error writing " + file, e);
        } catch (Exception e) {
            Log.w("FileUtils", "Failed to save file", e);
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception ex) {
            }
        }

        return success;
    }
}
