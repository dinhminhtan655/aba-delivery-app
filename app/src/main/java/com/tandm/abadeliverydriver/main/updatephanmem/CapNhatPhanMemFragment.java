package com.tandm.abadeliverydriver.main.updatephanmem;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.tandm.abadeliverydriver.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CapNhatPhanMemFragment extends Fragment {
    public static final String TAG = "CapNhatPhanMemFragment";
    private View view;
    private Unbinder unbinder;

    public static final int REQUEST_CODE = 124;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.ll_view)
    LinearLayout llView;
    String urlAPK;
    private File fileApk;

    public CapNhatPhanMemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cap_nhat_phan_mem, container, false);
        unbinder = ButterKnife.bind(this,view);

//        urlAPK = Utilities.BASE_URL+"/Attachments3/AbaB&D.apk";
//        fileApk = new File(Environment.getExternalStorageDirectory(), "AbaB&D.apk");
        return view;
    }


    @OnClick(R.id.btnCapNhat)
    public void taiPbm() {
//        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
//        } else {
//            downloadApkTask();
//        }
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=com.tandm.abadeliverydriver"));
        startActivity(intent);
    }

//    private void downloadApkTask() {
//        if (fileApk.exists()) {
//            fileApk.delete();
//        }
//        new DownloadFileAsync().execute(urlAPK);
//    }

//    private class DownloadFileAsync extends AsyncTask<String, Integer, String> {
//        @Override
//        protected void onPreExecute() {
//            llView.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected String doInBackground(String... urls) {
//            int count;
//            String result = "";
//            if (WifiHelper.isConnected(getContext())) {
//                try {
//                    URL url = new URL(urls[0]);
//                    URLConnection connect = url.openConnection();
//                    connect.connect();
//
//                    int lengthOfFile = connect.getContentLength();
//
//                    InputStream input = new BufferedInputStream(url.openStream());
//                    OutputStream output = new FileOutputStream(fileApk);
//
//                    byte data[] = new byte[lengthOfFile];
//
//                    long total = 0;
//
//                    while ((count = input.read(data)) != -1) {
//                        total += count;
//                        publishProgress((int) ((total * 100) / lengthOfFile));
//                        output.write(data, 0, count);
//                    }
//
//                    output.flush();
//                    output.close();
//                    input.close();
//                } catch (IOException e) {
//                    Log.e("DownloadApk", "", e);
////                    FirebaseCrash.report(e);
//                }
//            } else {
//                result = getString(R.string.no_internet);
//            }
//
//            return result;
//
//        }
//
//        protected void onProgressUpdate(Integer... progress) {
//            progressBar.setProgress(progress[0]);
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            try {
//                llView.setVisibility(View.GONE);
//                if (result.equalsIgnoreCase("")) {
//                    if (fileApk.exists())
//                        installApk(fileApk);
//                } else {
//                    Snackbar.make(llView, result, Snackbar.LENGTH_LONG).show();
//                }
//            } catch (Exception ex) {
////                FirebaseCrash.report(ex);
//            }
//        }
//    }
//
//
//    private void installApk(File file) {
//        Uri uriFileApk;
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            uriFileApk = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".fileprovider", file);
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//        } else {
//            uriFileApk = Uri.fromFile(fileApk);
//        }
//        intent.setDataAndType(uriFileApk, "application/vnd.android.package-archive");
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//    }
//
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == REQUEST_CODE)
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                downloadApkTask();
//            }
//    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



}
