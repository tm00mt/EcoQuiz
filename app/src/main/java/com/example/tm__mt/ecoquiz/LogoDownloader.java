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

/**
 * Created by tm__mt
 *
 * Downloads an image of given url.
 * Saves it locally as logoX.9.png, where X is from 1 to 6.
 *
 * Downloaded image should have 1px transparent border to indicate which parts of image can be
 * expanded (9patch functionality).
 */
public class LogoDownloader extends AsyncTask<String, Void, Bitmap> {
    private static final String DEBUG_TAG = "LogoDownloader";

    private int logoNum       = 0;
    private String logoPath   = "";
    private boolean logoSaved = false;
    private Context context   = null;

    public LogoDownloader(int logoNum, Context context) {
        this.logoNum = logoNum;
        this.context = context;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        Log.d(DEBUG_TAG, "Downloading image nr " + this.logoNum + ", url: " + urls[0]);

        Bitmap mIcon11 = null;
        InputStream in;

        try {
            in = new java.net.URL(urls[0]).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
            in.close();
        } catch (Exception e) {
            //Log.e("Error", e.getMessage());
            //e.printStackTrace();
            this.cancel(true);
        }

        return mIcon11;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        Log.d(DEBUG_TAG, "onPostExecute; image nr " + this.logoNum);

        if (result != null)
            saveBitmapInternal(result);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        Log.d(DEBUG_TAG, "onCancelled; image nr " + this.logoNum);
    }

    private void saveBitmapInternal(Bitmap bitmap){
        ContextWrapper cw = new ContextWrapper(this.context);

        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

        // Create imageDir
        File path = new File(directory, "logo" + this.logoNum + ".9.png");
        Log.d(DEBUG_TAG, "Saving " + this.logoNum + ". downloaded logo to '" + path.getPath() + "' directory");

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

    public boolean isLogoSaved(boolean dispalyStatus) {
        if (dispalyStatus)
            Log.d(DEBUG_TAG, "Logo number: " + this.logoNum + ", path: " + this.logoPath + ", saved: " + this.logoSaved);

        return this.logoSaved;
    }
}
