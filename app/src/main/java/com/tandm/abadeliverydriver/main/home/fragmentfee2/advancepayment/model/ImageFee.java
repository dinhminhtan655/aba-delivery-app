package com.tandm.abadeliverydriver.main.home.fragmentfee2.advancepayment.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import com.tandm.abadeliverydriver.main.utilities.ExifUtil;
import com.tandm.abadeliverydriver.main.utilities.PathUtil;

import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;

public class ImageFee implements Serializable {
    private String path;
    private Uri uri;

    public ImageFee(String path, Uri uri) {
        this.path = path;
        this.uri = uri;
    }

    public ImageFee() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public Bitmap xulyAnh2(Context context, String path, Uri imgUri) {
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
}
