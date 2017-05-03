package com.signu.signu;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.util.Log;

import java.io.File;

/**
 * Created by Marcos on 03/05/2017.
 */

public class PdfManager {

    private static final String LOG_TAG = "PDF MANAGER LOG";

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public File getExternalFile(String filename){
        return new File(Environment.getExternalStorageDirectory().getAbsolutePath(), filename);
    }

    public void trying(){
    }

    /**
     * Devuelve una instancia de File que reresenta el directorio correspondiente dentro de Downloads
     * @return
     */
    public File getDownladsStorageDir() {
        // Get the directory for the user's public pictures directory.
        return Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS);
    }
}
