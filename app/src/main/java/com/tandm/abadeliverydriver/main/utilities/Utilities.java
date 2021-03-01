package com.tandm.abadeliverydriver.main.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.tandm.abadeliverydriver.R;
import com.tandm.abadeliverydriver.main.api.MyRetrofit;
import com.tandm.abadeliverydriver.main.home.fragmenthome.StoreATM;
import com.tandm.abadeliverydriver.main.home.fragmenthomedriver.model.CountShipment;
import com.tandm.abadeliverydriver.main.preference.ConnectStringPrefer;
import com.tandm.abadeliverydriver.main.preference.LoginPrefer;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Utilities extends WifiHelper {

//                public static final String IMAGE_BASE_URL = "http://118.69.37.34:567/Attachments3/";  //link public
//    public static final String IMAGE_BASE_URL = "http://api-delivery.aba.com.vn:567/Attachments3/";  //link public tên miền
//            public static final String IMAGE_BASE_URL = "http://192.168.1.49/aba2/Attachments3/"; // link home
//        public static final String IMAGE_BASE_URL = "http://192.168.0.117/aba2/Attachments3/"; // link home2
//    public static final String IMAGE_BASE_URL = "http://192.168.6.105/aba2/Attachments3/"; // link cong ty
//        public static final String IMAGE_BASE_URL = "http://192.168.2.14/aba2/Attachments3/"; // link Q12
//            public static final String IMAGE_BASE_URL = "http://10.10.12.236/aba2/Attachments3/"; // link Kho

    public static final String EDIORDERID = "EDIORDERID";
    public static final String ORDERDATE = "ORDERDATE";
    public static final String TIMESLOTID = "TIMESLOTID";
    public static final String TRUCKNUMBER = "TRUCKNUMBER";
    public static final String CUSTPONUMBER = "CUSTPONUMBER";
    public static final String CUSTOMERREFERENCE = "CUSTOMERREFERENCE";
    public static final String TOTALQUANTITY = "TOTALQUANTITY";
    public static final String TOTALWEIGHTS = "TOTALWEIGHTS";
    public static final String VEHICLETYPE = "VEHICLETYPE";
    public static final String DOCKDOORID = "DOCKDOORID";
    public static final String ORDERTYPE = "ORDERTYPE";


    public static Dialog createNewDialog(Context context, int layoutID) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(layoutID);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.getWindow().setAttributes(lp);

        return dialog;
    }

    public static final String getURLImage(Context context) {
        String IMAGE_BASE_URL = String.format("%s://%s/Attachments3/", ConnectStringPrefer.getSSLOrNoSSL(context),!ConnectStringPrefer.getDomain(context).equals("") ? ConnectStringPrefer.getDomain(context) : "api-delivery.aba.com.vn:44567");
        return IMAGE_BASE_URL;
    }

    //pass@word1
    public static final String PASSWORD1 = "AJdonErYCMGxE+Wbd/ehRYY8WP9kwmhbcvmmr88V4KzOhzMSeGEdpTHsPCs7WqA+eQ==";
    //123456
    public static final String PASSWORD2 = "ACmeoWY1fAPvE8Qoa8/R+YeNQThQw1+HSRAYjpiFeQHWL9FS1crBbdyVZBLbBcs7NQ==";

    public static StoreATM currentItem = null;


    //    public static long intervalMillis = 2L * 60L * 1_000L;
    public static long intervalMillis = 60_000L * 360L;
    public static long triggerAtMillis = System.currentTimeMillis() + intervalMillis;

    public static ProgressDialog getProgressDialog(Context context, String message) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage(message);
        return dialog;
    }

    public static void showBackIcon(ActionBar actionBar) {
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    public static void hideKeyboard(Activity context) {
        View view = context.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void dismissDialog(ProgressDialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public static String formatDate_ddMMyyyy(String sDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        try {
            Date date = format.parse(sDate);
            SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
            return newFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sDate;
    }

    public static String formatDate_MMddyyyy(String sDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        try {
            Date date = format.parse(sDate);
            SimpleDateFormat newFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
            return newFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sDate;
    }

    public static String formatDate_yyyyMMdd(String sDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        try {
            Date date = format.parse(sDate);
            SimpleDateFormat newFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
            return newFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sDate;
    }

    public static String formatDate_yyyyMMdd2(String sDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        try {
            Date date = format.parse(sDate);
            SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            return newFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sDate;
    }

    public static String formatDate_yyyyMMdd3(String sDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
        try {
            Date date = format.parse(sDate);
            SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            return newFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sDate;
    }

    public static String formatDate_yyyyMMdd4(String sDate) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        try {
            Date date = format.parse(sDate);
            SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            return newFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sDate;
    }

    public static String formatDate_MMyyyy(String sDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        try {
            Date date = format.parse(sDate);
            SimpleDateFormat newFormat = new SimpleDateFormat("MM/yyyy", Locale.US);
            return newFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sDate;
    }

    public static String formatDate_ddMMyyHHmm(String sDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        try {
            Date date = format.parse(sDate);
            SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yy HH:mm", Locale.US);
            return newFormat.format(date).equalsIgnoreCase("01/01/00 00:00") ? "" : newFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sDate;
    }

    public static String formatDateTime_yyyyMMddHHmmssFromMili(long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        return format.format(calendar.getTime());
    }

    public static String formatDateTime_ddMMMyyyyFromMili(long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        return format.format(calendar.getTime());
    }

    public static String formatDateTime_ddMMyyyyFromMili(long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        return format.format(calendar.getTime());
    }


    public static String formatDateTime_yyMMddFromMili(long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        SimpleDateFormat format = new SimpleDateFormat("yyMMdd", Locale.US);
        return format.format(calendar.getTime());
    }

    public static String formatDateTime_yyyyMMddFromMili(long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return format.format(calendar.getTime());
    }

    public static String formatDateTime_MMMyyyyFromMili(long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        SimpleDateFormat format = new SimpleDateFormat("MMM-yyyy", Locale.US);
        return format.format(calendar.getTime());
    }

    public static long formatDateTime_ToMilisecond(String datetime) throws ParseException {
        String myDate = datetime;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        Date date = sdf.parse(myDate);
        return date.getTime();
    }

    public static long formatDateTime_ToMilisecond2(String datetime) throws ParseException {
        String myDate = datetime;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        Date date = sdf.parse(myDate);
        return date.getTime();
    }

    public static String formatDateTimeddMMyyyyHHmmss_ToMilisecond(String datetime) throws ParseException {
        String myDate = datetime;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date date = sdf.parse(myDate);
        String output = sdf1.format(date);
        return output;
    }


    public static Bitmap resizeBitmap(String photoPath, int targetW, int targetH) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        }

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true; //Deprecated API 21

        return BitmapFactory.decodeFile(photoPath, bmOptions);
    }


    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path1 = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "IMG" + Calendar.getInstance().getTime(), null);
        return Uri.parse(path1);
    }

//    public static Uri getImageUri2(Context inContext, Bitmap inImage) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path1 = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
//        return Uri.parse(path1);
//    }


    public static Bitmap getThumbnail(Uri uri, Context context) throws FileNotFoundException, IOException {
        InputStream input = context.getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither = true;//optional
        onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();

        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1)) {
            return null;
        }

        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;

        double ratio = (originalSize > 2000) ? (originalSize / 2000) : 2;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither = true; //optional
        bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//
        input = context.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }


    private static int getPowerOfTwoForSampleRatio(double ratio) {
        int k = Integer.highestOneBit((int) Math.floor(ratio));
        if (k == 0) return 1;
        else return k;
    }


    public static void openSettings(Context context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }


    public static Bitmap addDateText(Bitmap bitmap) {
        Bitmap mutable = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutable);
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setTextSize(60);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(Utilities.formatDateTime_yyyyMMddHHmmssFromMili(System.currentTimeMillis()), 400, 100, paint);
        return mutable;
    }

    public static void xulyAnh(Context context, String path, ImageView imageView, Uri imgUri) {
        Bitmap bitmap = null;
        Bitmap orientedBitmap;
        try {
            path = PathUtil.getPath(context, imgUri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imgUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        orientedBitmap = ExifUtil.rotateBitmap(path, bitmap);

        imageView.setImageBitmap(orientedBitmap);
    }

    public static Bitmap xulyAnh2(Context context, String path, Uri imgUri) {
        Bitmap bitmap = null;
        Bitmap orientedBitmap;
        try {
            path = PathUtil.getPath(context, imgUri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imgUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        orientedBitmap = ExifUtil.rotateBitmap(path, bitmap);

        return orientedBitmap;
    }


    public static void hideItemBikerDrawer(NavigationView navi) {
        Menu navMenu = navi.getMenu();
        navMenu.findItem(R.id.fee_drawer).setVisible(false);
        navMenu.findItem(R.id.trip_drawer).setVisible(false);
        navMenu.findItem(R.id.home_driver_drawer).setVisible(false);
        navMenu.findItem(R.id.history_driver_drawer).setVisible(false);
        navMenu.findItem(R.id.vouchers_drawer).setVisible(false);
        navMenu.findItem(R.id.dieu_dong_driver_drawer).setVisible(false);
    }

    public static void hideItemDriverDrawer(NavigationView navi, Context context) {
        Menu navMenu = navi.getMenu();

        if (!LoginPrefer.getObject(context).isBiker && LoginPrefer.getObject(context).Position.equals("KH")) {
            navMenu.setGroupVisible(R.id.groupDriver, false);
        } else {
            navMenu.setGroupVisible(R.id.groupKH, false);
            if (LoginPrefer.getObject(context).isBiker && LoginPrefer.getObject(context).Position.equals("NVGN")) {
                navMenu.findItem(R.id.fee_drawer).setVisible(false);
                navMenu.findItem(R.id.history_drawer).setVisible(false);
                navMenu.findItem(R.id.history_driver_drawer).setVisible(false);
                navMenu.findItem(R.id.home_drawer).setVisible(false);
//            navMenu.findItem(R.id.home_driver_drawer).setVisible(false);
                navMenu.findItem(R.id.vouchers_drawer).setVisible(false);
                navMenu.findItem(R.id.dieu_dong_driver_drawer).setVisible(false);
//            navMenu.findItem(R.id.trip_drawer).setVisible(false);
                navMenu.findItem(R.id.home_op_drawer).setVisible(false);
                navMenu.findItem(R.id.late_licenses_op_drawer).setVisible(false);
            } else if (!LoginPrefer.getObject(context).isBiker && LoginPrefer.getObject(context).Position.equals("NVGN")) {
//            navMenu.findItem(R.id.fee_drawer).setVisible(false);
                navMenu.findItem(R.id.history_biker_drawer).setVisible(false);
                navMenu.findItem(R.id.history_drawer).setVisible(false);
                navMenu.findItem(R.id.home_drawer).setVisible(false);
                navMenu.findItem(R.id.giao_bu_drawer).setVisible(false);
                navMenu.findItem(R.id.home_op_drawer).setVisible(false);
                navMenu.findItem(R.id.late_licenses_op_drawer).setVisible(false);
            } else if (!LoginPrefer.getObject(context).isBiker && LoginPrefer.getObject(context).Position.equals("OP")
                    || !LoginPrefer.getObject(context).isBiker && LoginPrefer.getObject(context).Position.equals("OP2")
                    || !LoginPrefer.getObject(context).isBiker && LoginPrefer.getObject(context).Position.equals("KT")
                    || !LoginPrefer.getObject(context).isBiker && LoginPrefer.getObject(context).Position.equals("KT2")) {
                if (LoginPrefer.getObject(context).Position.equals("OP2") || LoginPrefer.getObject(context).Position.equals("KT") || LoginPrefer.getObject(context).Position.equals("KT2")) {
                    navMenu.findItem(R.id.late_licenses_op_drawer).setVisible(false);
                }
                navMenu.findItem(R.id.fee_drawer).setVisible(false);
                navMenu.findItem(R.id.history_drawer).setVisible(false);
                navMenu.findItem(R.id.history_driver_drawer).setVisible(false);
                navMenu.findItem(R.id.home_drawer).setVisible(false);
                navMenu.findItem(R.id.home_driver_drawer).setVisible(false);
                navMenu.findItem(R.id.vouchers_drawer).setVisible(false);
                navMenu.findItem(R.id.dieu_dong_driver_drawer).setVisible(false);
                navMenu.findItem(R.id.trip_drawer).setVisible(false);
                navMenu.findItem(R.id.history_biker_drawer).setVisible(false);
                navMenu.findItem(R.id.history_drawer).setVisible(false);
                navMenu.findItem(R.id.giao_bu_drawer).setVisible(false);
            }
        }


    }

    public static void initializeCountDrawer(View view, Context context, TextView tv, String strToken, String strDriverID, String strDate) {
        tv.setGravity(Gravity.CENTER_VERTICAL);
        tv.setTypeface(null, Typeface.BOLD);

        tv.setTextColor(context.getResources().getColor(R.color.white));
        MyRetrofit.initRequest(context).countShipment(strToken, strDriverID).enqueue(new Callback<CountShipment>() {
            @Override
            public void onResponse(Call<CountShipment> call, Response<CountShipment> response) {
                if (response.isSuccessful() && response != null) {

                    tv.setText(response.body().count);
                } else {
                    try {
                        Snackbar.make(view, "Lỗi hệ thống", Snackbar.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Log.e("viewErr", e.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<CountShipment> call, Throwable t) {
                try {
                    Snackbar.make(view, "Không có kết nối mạng", Snackbar.LENGTH_LONG).show();
                } catch (Exception e) {
                    Log.e("viewErr", e.toString());
                }

            }
        });
    }


    public static Bitmap textToImage(String text, int width, int height) throws WriterException, NullPointerException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.DATA_MATRIX.QR_CODE,
                    width, height, null);
        } catch (IllegalArgumentException Illegalargumentexception) {
            return null;
        }

        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        int colorWhite = 0xFFFFFFFF;
        int colorBlack = 0xFF000000;

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;
            for (int x = 0; x < bitMatrixWidth; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ? colorBlack : colorWhite;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, width, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    public static String formatNumber(String number) {
        String formattedString = "";
        try {
            String originalString = number;

            Long longval;
            if (originalString.contains(",")) {
                originalString = originalString.replaceAll(",", "");
            }
            longval = Long.parseLong(originalString);

            DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
            formatter.applyPattern("#,###,###,###,###,###,###,###,###,###");
            formattedString = formatter.format(longval);

            //setting text after format to EditText
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
        return formattedString;
    }


    public static void thongBaoDialog(Context context, String content) {
        AlertDialog.Builder ba = new AlertDialog.Builder(context);
        ba.setTitle("Thông báo").setMessage(content);

        Dialog dialog = ba.create();
        dialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 1500);
    }


    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }

        return phrase.toString();
    }


    public static Bitmap mergeBitmaps(Bitmap logo, Bitmap qrcode) {

        Bitmap combined = Bitmap.createBitmap(qrcode.getWidth(), qrcode.getHeight(), qrcode.getConfig());
        Canvas canvas = new Canvas(combined);
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        canvas.drawBitmap(qrcode, new Matrix(), null);

        Bitmap resizeLogo = Bitmap.createScaledBitmap(logo, canvasWidth / 5, canvasHeight / 5, true);
        int centreX = (canvasWidth - resizeLogo.getWidth()) / 2;
        int centreY = (canvasHeight - resizeLogo.getHeight()) / 2;
        canvas.drawBitmap(resizeLogo, centreX, centreY, null);
        return combined;
    }


    public static Activity unwrap(Context context) {
        while (!(context instanceof Activity) && context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }

        return (Activity) context;
    }
}
