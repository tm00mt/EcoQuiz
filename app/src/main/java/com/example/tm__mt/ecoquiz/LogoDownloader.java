package com.example.tm__mt.ecoquiz;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class LogoDownloader extends AsyncTask<String, Void, Bitmap> {
    private static final String DEBUG_TAG = "LogoDownloader";

    private int logoNum = 0;
    private String logoPath = "";
    private boolean logoSaved = false;
    private Context context = null;

    public LogoDownloader(int logoNum, Context context) {
        Log.d(DEBUG_TAG, "LogoDownloader constructor");
        this.logoNum = logoNum;
        this.context = context;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        Log.d(DEBUG_TAG, "doInBackground; Downloading image nr " + this.logoNum + ", url: " + urls[0]);

        Bitmap mIcon11 = null;
        InputStream in;

        try {
            in = new java.net.URL(urls[0]).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
            in.close();
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        return mIcon11;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        Log.d(DEBUG_TAG, "onPostExecute; image nr " + this.logoNum + ": " + result);

        if (result != null)
            saveBitmapInternal(result);
    }

    private void saveBitmapInternal(Bitmap bitmap){
        Log.d(DEBUG_TAG, "saveBitmapInternal" + this.logoNum);

        ContextWrapper cw = new ContextWrapper(this.context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File path = new File(directory,"logo" + this.logoNum + ".png");

        FileOutputStream fos;
        try {
            fos = new FileOutputStream(path);
            // Use the compress method on the BitMap object to write image to the OutputStream
            //"Hint to the compressor, 0-100. 0 meaning compress for small size, 100 meaning compress for max quality. Some formats, like PNG which is lossless, will ignore the quality setting.."
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.logoPath = path.getAbsolutePath();
        this.logoSaved = true;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public boolean isLogoSaved() {
        Log.d(DEBUG_TAG, "isLogoSaved, logo num: " + this.logoNum + " " + this.logoSaved);
        Log.d(DEBUG_TAG, "logoPath : " + this.logoPath);
        return this.logoSaved;
    }
}
