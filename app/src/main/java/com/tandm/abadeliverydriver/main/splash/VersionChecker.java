package com.tandm.abadeliverydriver.main.splash;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class VersionChecker extends AsyncTask<String, String, String> {

    String newVersion;

    Context context;
    String curVersion;

    public VersionChecker(Context context) {
        this.context = context;
    }


    @Override
    protected String doInBackground(String... strings) {
        try {
           Document document =  Jsoup.connect("https://play.google.com/store/apps/details?id=" + context.getPackageName() + "&hl=en")
                    .timeout(30000)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get();
            if (document != null){
                Elements element = document.getElementsContainingOwnText("Current Version");
                for (Element ele : element) {
                    if (ele.siblingElements() != null) {
                        Elements sibElemets = ele.siblingElements();
                        for (Element sibElemet : sibElemets) {
                            newVersion = sibElemet.text();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("version", newVersion);
        return newVersion;
    }


//    @Override
//    protected String doInBackground(String... params) {
//
//        try {
//            newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + context.getPackageName())
//                    .timeout(30000)
//                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
//                    .referrer("http://www.google.com")
//                    .get()
//                    .select(".hAyfc .htlgb")
//                    .get(7)
//                    .ownText();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Log.e("version", newVersion);
//        return newVersion;
//    }


}
